package com.lin.creator;

import com.lin.entity.FieldInfo;
import com.lin.entity.common.PathInfo;
import com.lin.entity.common.TableInfo;
import com.lin.helper.DatabaseHelper;
import com.lin.utils.DBTools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * FakerCreator 公共逻辑
 * @since 1.0.3
 * @author lkmc2
 */
public abstract class BaseFakerCreator<T extends FieldInfo> {

    /** 数据库中的字符串类型集合 **/
    private static Set<String> DB_STRING_TYPE_SET = new HashSet<String>();

    /** DataType类型名列表 **/
    private static final List<String> DATA_TYPE_LIST = Arrays.asList(
            "USERNAME", "PHONE", "TIME", "ADDRESS", "AGE", "SEX", "EMAIL"
    );

    /** 数据库类型推断字典 **/
    private static Map<String, String> DATABASE_INFER_MAP = new LinkedHashMap<String, String>();

    /** 数据库名 **/
    protected String dbName;

    /** 数据库链接 **/
    private String url;

    /** 用户名 **/
    private String username;

    /** 密码 **/
    private String password;

    /** 数据库的驱动名称 **/
    private String driverClassName;

    {
        // 设置数据库中的字符串类型集合
        setDbStringTypeSet(DB_STRING_TYPE_SET);

        // 设置数据库类型推断字典
        setDatabaseInferMap(DATABASE_INFER_MAP);
    }

    /** 记录泛型信息的变量 **/
    private Class<T> clazz = null;

    {
        Type type = getClass().getGenericSuperclass();
        if( type instanceof ParameterizedType){
            ParameterizedType pType = (ParameterizedType)type;
            Type clazz = pType.getActualTypeArguments()[0];
            if( clazz instanceof Class ){
                // noinspection unchecked
                this.clazz = (Class<T>) clazz;
            }
        }
    }

    /**
     * 设置数据库连接url
     * @param url 数据库连接url
     * @return Faker表创建器
     */
    protected BaseFakerCreator setUrl(String url) {
        this.url = url;
        this.dbName = url.substring(url.lastIndexOf("/") + 1);
        return this;
    }

    /**
     * 设置连接的数据库名
     * @param dbName 数据库名
     * @return Faker表创建器
     * 默认会加上前缀：jdbc:mysql://localhost:3306/
     */
    protected BaseFakerCreator setDbName(String dbName) {
        if (this.url == null) {
            this.url = getDefaultUrlPrefix() + dbName;
        }
        this.dbName = dbName;
        return this;
    }

    /**
     * 设置连接的数据库用户名
     * @param username 数据库用户名
     * @return Faker表创建器
     * 默认值为：root
     */
    public BaseFakerCreator username(String username) {
        this.username = username;
        return this;
    }

    /**
     * 设置连接的数据库密码
     * @param password 数据库密码
     * @return Faker表创建器
     * 默认值为：123456
     */
    public BaseFakerCreator password(String password) {
        this.password = password;
        return this;
    }

    /**
     * 设置连接的数据库的驱动名称
     * @param driverClassName 数据库的驱动名称
     * @return Faker表创建器
     * 默认值为：com.microsoft.sqlserver.jdbc.SQLServerDriver
     */
    public BaseFakerCreator driverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    /**
     * 创建Faker表结构
     */
    public void build() {
        this.username = (this.username != null) ? this.username : getDefaultUsername();
        this.password = (this.password != null) ? this.password : getDefaultPassword();
        this.driverClassName = (this.driverClassName != null) ? this.driverClassName : getDefaultDriverClassName();

        try {
            // 执行主要逻辑
            executeMainLogic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行主要逻辑
     */
    private void executeMainLogic() throws Exception {
        // 获取生成文件的路径信息
        PathInfo pathInfo = getPathInfo(getRuntimeClassName());

        // 对Url链接进行检查
        validUrl();

        // 连接数据库
        DBTools.url(this.url)
                .username(this.username)
                .password(this.password)
                .driverClassName(this.driverClassName)
                .connect();

        // 获取查询表信息的 sql 语句
        String queryTablesInfoSql = getQueryTablesInfoSql();

        // 获取所有的表信息（表名、表注释）列表
        List<TableInfo> tableInfoList = DatabaseHelper.queryEntityList(TableInfo.class, queryTablesInfoSql);

        // 生成Java文件的内容
        StringBuilder fileContent = new StringBuilder();
        fileContent.append(String.format("package %s;", pathInfo.getPackageName())).append("\n\n");
        fileContent.append("import com.lin.datatype.DataType;").append("\n");
        fileContent.append("import com.lin.faker.Faker;").append("\n");
        fileContent.append("import com.lin.utils.DBTools;").append("\n");
        fileContent.append("import com.lin.value.Times;").append("\n");
        fileContent.append("import com.lin.value.Values;").append("\n\n");
        fileContent.append("/**").append("\n");
        fileContent.append("* Faker生成的表结构").append("\n");
        fileContent.append("*/").append("\n");
        fileContent.append("public class CreateFakerTable {").append("\n\n");
        fileContent.append("\t").append("public static void main(String[] args) {").append("\n");
        fileContent.append("\t\t").append("// 创建数据库连接\n");
        fileContent.append("\t\t").append(String.format("DBTools.url(\"%s\")", this.url)).append("\n");
        fileContent.append("\t\t\t\t").append(String.format(".username(\"%s\")", this.username)).append("\n");
        fileContent.append("\t\t\t\t").append(String.format(".password(\"%s\")", this.password)).append("\n");
        fileContent.append("\t\t\t\t").append(String.format(".driverClassName(\"%s\")", this.driverClassName)).append("\n");
        fileContent.append("\t\t\t\t").append(".connect();").append("\n\n");

        // 遍历所有表信息
        for (TableInfo tableInfo : tableInfoList) {
            // 数据库表名
            String tableName = tableInfo.getTableName();

            // 获取该表所有的字段信息的 sql 语句
            String queryFieldsInfoSql = getQueryFieldsInfoSql(tableName);

            // 获取该表所有的字段信息列表
            List<T> fieldInfoList = DatabaseHelper.queryEntityList(clazz, queryFieldsInfoSql);

            // 创建一个Faker表
            String fakerTable = createFakerTable(tableName, fieldInfoList);
            System.out.println(fakerTable);

            // 如果表注释非空，则添加注释
            String tableComment = tableInfo.getTableComment();
            if (tableComment != null && !"".equals(tableComment)) {
                fileContent.append("\t\t").append(String.format("// %s", tableComment)).append("\n");
            }

            // 添加Faker表信息
            fileContent.append(fakerTable).append("\n");
        }

        fileContent.append("\t").append("}").append("\n\n");
        fileContent.append("}").append("\n");


        // 设置文件生成位置（文件前缀路径不为空时，加上路径前缀）
        String fileName = "CreateFakerTable.java";
        if (pathInfo.getCurrentFilePath() != null) {
            fileName = pathInfo.getCurrentFilePath() + fileName;
        }

        System.out.println(String.format("共生成 [ %s ] 个表结构", tableInfoList.size()));
        System.out.println("生成Faker表结构位置，位于当前项目：" + fileName);

        // 将代码写入CreateFakerTable.java文件中
        writeContentToFile(fileContent, fileName);
    }

    /**
     * 设置获取查询表信息的 sql 语句
     * @return 获取查询表信息的 sql 语句
     */
    protected abstract String getQueryTablesInfoSql();

    /**
     * 设置获取该表所有的字段信息的 sql 语句
     * @return 获取该表所有的字段信息的 sql 语句
     * @param tableName 表名
     */
    protected abstract String getQueryFieldsInfoSql(String tableName);

    /**
     * 验证Url信息
     */
    private void validUrl() {
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("数据库url不能为空");
        }
    }

    /**
     * 将内容写入文件
     * @param fileContent 内容
     * @param fileName 文件名
     */
    private static void writeContentToFile(StringBuilder fileContent, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(fileContent.toString());
        writer.flush();
        writer.close();
    }

    /**
     * 获取生成文件的路径信息
     * @param className 类名
     * @return 生成文件的路径信息
     */
    private static PathInfo getPathInfo(String className) throws ClassNotFoundException, IOException {
        // 类
        Class<?> clazz = Class.forName(className);
        // 包名
        String packageName = clazz.getPackage().getName();

        // 创建文件所在目录
        File file = new File("");
        // 文件路径
        String currentFilePath = file.getCanonicalPath();

        // 路径中不包含包名时，添加\src\main\java路径
        String packageNamePath = packageName.replace(".", "\\");
        if (!currentFilePath.contains(packageNamePath)) {
            currentFilePath += "\\src\\main\\java\\" + packageNamePath + "\\";
        }

        // 判断该路径是否已创建，未创建时将路径置为null
        File pathFile = new File(currentFilePath);
        if (!pathFile.exists()) {
            currentFilePath = null;
        }

        // 路径信息
        return new PathInfo(packageName, currentFilePath);
    }

    /**
     * 获取当前正在运行的类的名字
     * @return 当前正在运行的类名
     */
    private static String getRuntimeClassName() {
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }

    /**
     * 创建一个Faker表
     * @param tableName 表名
     * @param fieldInfoList 字段信息列表
     * @return Faker表结构表示
     */
    private static <T extends FieldInfo> String createFakerTable(String tableName, List<T> fieldInfoList) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\t\tFaker.tableName(\"%s\")\n", tableName));

        // 遍历生成参数信息
        for (T fieldInfo : fieldInfoList) {
            sb.append(String.format("\t\t\t\t.param(\"%s\", %s)%s\n",
                    fieldInfo.getFieldName(), fieldToInferType(fieldInfo), addCommon(fieldInfo)));
        }

        sb.append("\t\t\t\t.insertCount(5)\n");
        sb.append("\t\t\t\t.onlyShowSql();\n");
        return sb.toString();
    }

    /**
     * 添加字段的注释
     * @param fieldInfo 字段信息
     * @return 字段注释
     */
    private static String addCommon(FieldInfo fieldInfo) {
        String comment = fieldInfo.getComments();

        // 注释非空时，返回字段的注释
        return (comment != null && !"".equals(comment)) ? String.format(" // %s", comment) : "";
    }

    /**
     * 根据字段信息推断出对应的DateType类型
     * @param fieldInfo 字段信息
     * @return DateType类型对应的字符串
     */
    private static String fieldToInferType(FieldInfo fieldInfo) {
        // 字段名
        String fieldName = fieldInfo.getFieldName().toUpperCase();
        // 删除括号后的字段类型
        String fieldType = removeBrackets(fieldInfo.getDataType().toUpperCase());

        // 1.根据是否主键设置（字段名中有ID，并且字段类型是字符串类型）
        if (fieldName.contains("ID") && isStringType(fieldType)) {
            return "DataType.ID";
        }

        // 2.根据字段名设置
        // 检查字段名中是否有符合DataType名的字段
        if (checkFieldNameValidToInfer(fieldName, fieldType)) {
            // 获取合适的DataType类型
            return getRightDataType(fieldName);
        }

        // 3.根据数据库类型设置
        return dbTypeToDataType(fieldType);
    }

    /**
     * 将数据库类型转换成合适的DataType类型
     * @param fieldType 字段类型
     * @return 临时DataType类型
     */
    private static String dbTypeToDataType(String fieldType) {
        // 将数据类类型转换成临时类型
        String tempType = DATABASE_INFER_MAP.get(fieldType);

        if (tempType == null) {
            throw new RuntimeException(String.format("无法转换 【%s】 类型的数据", fieldType));
        }

        switch (tempType) {
            case "INT":
                return "Values.ofIntRange(1, 18)";
            case "FLOAT":
                return "Values.ofDoubleRange(1.0, 18.0)";
            case "TIME":
                return "Values.ofTimeRange(Times.of(2019, 1, 1), Times.of(2019, 5, 1))";
            case "TEXT":
                return "Values.of(\"example1\", \"example2\", \"example3\")";
            default:
                throw new RuntimeException("生成的DataType类型错误");
        }
    }

    /**
     * 获取合适的DataType类型
     * @param fieldName 字段名
     * @return DataType类型的字符串
     */
    private static String getRightDataType(String fieldName) {
        // 遍历DataType类型列表
        for (String dataTypeName : DATA_TYPE_LIST) {
            // 字段名中包含该DataType类型名
            if (fieldName.contains(dataTypeName)) {
                return String.format("DataType.%s", dataTypeName);
            }
        }
        throw new RuntimeException("未找到合适的DataType映射类型");
    }

    /**
     * 检查字段名中是否有符合DataType名的字段
     * @param fieldName 字段名
     * @param fieldType 字段类型
     * @return 是否有符合DataType名的字段
     */
    private static boolean checkFieldNameValidToInfer(String fieldName, String fieldType) {
        // DataType类型名列表包含该字段名，并且是字符串类型
        return DATA_TYPE_LIST.contains(fieldName) && isStringType(fieldType);
    }

    /**
     * 判断字段类型是否属于数据库中的字符串类型
     * @param fieldType 字段类型
     * @return 是否属于数据库中的字符串类型
     */
    private static boolean isStringType(String fieldType) {
        // 数据库中的字符串类型列表包含该类型
        return DB_STRING_TYPE_SET.contains(fieldType.toUpperCase());
    }

    /**
     * 移除字段类型中的括号
     * @param fieldType 字段类型
     * @return 删除括号后的字段类型
     */
    private static String removeBrackets(String fieldType) {
        if (fieldType.contains("(")) {
            int index = fieldType.indexOf("(");
            return fieldType.substring(0, index);
        }
        return fieldType;
    }

    /**
     * 获取默认用户名
     * @return 默认用户名
     */
    protected abstract String getDefaultUsername();

    /**
     * 获取默认密码
     * @return 默认密码
     */
    protected abstract String getDefaultPassword();

    /**
     * 获取默认驱动名称
     * @return 默认驱动名称
     */
    protected abstract String getDefaultDriverClassName();

    /**
     * 获取默认 url 前缀
     * @return 默认 url 前缀
     */
    protected abstract String getDefaultUrlPrefix();

    /**
     * 设置数据库中的字符串类型集合
     * @param dbStringTypeSet 数据库中的字符串类型集合
     */
    protected abstract void setDbStringTypeSet(Collection<String> dbStringTypeSet);

    /**
     * 设置数据库类型推断字典
     * @param databaseInferMap 数据库类型推断字典
     */
    protected abstract void setDatabaseInferMap(Map<String, String> databaseInferMap);

}
