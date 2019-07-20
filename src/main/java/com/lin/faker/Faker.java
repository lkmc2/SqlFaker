package com.lin.faker;

import com.lin.asserts.Asserts;
import com.lin.datatype.DataType;
import com.lin.helper.DatabaseHelper;
import com.lin.logger.Logger;
import com.lin.logger.LoggerFactory;
import com.lin.mapping.DataTypeMapping;
import com.lin.random.RandomData;
import com.lin.utils.FlyweightUtils;
import com.lin.utils.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 数据伪造器
 * @author lkmc2
 * @since 1.0.0
 */
public final class Faker {

    /** 数据库表名 **/
    private String tableName;

    /** 插入数据条数 **/
    private int count;

    /** 总的插入行数 **/
    private int totalCount = 0;

    /** 是否插入数据到数据库 **/
    private boolean isInsertDataToDb;

    /** 存储属性的同步Map **/
    private final Map<String, Object> PARAM_MAP;

    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Faker.class);

    /** sql 语句保存文件 **/
    private File sqlFile;

    // 初始化参数存储Map和数据类型映射Map/
    {
        PARAM_MAP = Collections.synchronizedMap(new LinkedHashMap<String, Object>(10));
    }

    private Faker() {
        sqlFile = new File("my_sql.sql");
        if (!sqlFile.exists()) {
            try {
                boolean success = sqlFile.createNewFile();
                if (success) {
                    System.out.println("创建文件 【my_sql.sql】 成功");
                    System.out.println(sqlFile.getAbsolutePath());
                } else {
                    System.out.println("创建文件 【my_sql.sql】 失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(sqlFile.getAbsolutePath());
        }
    }

    // 静态单例
    private static final class FakerHolder {
        private static final Faker INSTANCE = new Faker();
    }

    /**
     * 获取Facker类实例，并设置数据库表名
     * @param tableName 数据库表名
     * @return 数据伪造器
     */
    public static Faker tableName(String tableName) {
        return FakerHolder.INSTANCE.setTableName(tableName);
    }

    /**
     * 设置数据库表名
     * @param tableName 数据库表名
     * @return 数据伪造器
     */
    private Faker setTableName(String tableName) {
        this.tableName = tableName;

        // 重置变量值
        resetVariable();

        return this;
    }

    /**
     * 重置变量值
     */
    private void resetVariable() {
        this.PARAM_MAP.clear();
        this.count = 0;
        this.totalCount = 0;
        this.isInsertDataToDb = false;
    }

    /**
     * 添加参数
     * @param paramName 参数名
     * @param paramType 参数类型
     * @return 数据伪造器
     */
    public Faker param(String paramName, Object paramType) {
        PARAM_MAP.put(paramName, paramType);
        return this;
    }

    /**
     * 设置数据库插入条数
     * @param count 插入条数
     * @return 数据伪造器
     */
    public Faker insertCount(int count) {
        this.count = count;
        return this;
    }

    /**
     * 不指定SQL生成和插入操作
     */
    public void ignored() {
    }

    /**
     * 只是显示生成的SQL，不插入数据到数据库
     * 该方法用于检测生成的数据是否正确
     */
    public void onlyShowSql() {
        // 设置不插入数据到数据库
        this.isInsertDataToDb = false;

        // 执行主要逻辑
        executeMainLogic();
    }

    /**
     * 执行插入数据到数据库，并显示执行的SQL语句
     */
    public void execute() {
        // 设置插入数据到数据库
        this.isInsertDataToDb = true;

        // 执行主要逻辑
        executeMainLogic();
    }

    /**
     * 执行主要逻辑
     */
    private void executeMainLogic() {
        // 1.检查参数是否传入成功
        checkParams();

        // 2.执行生成SQL语句的逻辑
        generateSql();

        // 3.执行SQL语句，插入数据到数据库
        executeSql();
    }

    /**
     * 检查参数是否传入成功
     */
    private void checkParams() {
        Asserts.isTrue(StringUtils.isNotEmpty(tableName), "数据库表名不能为空");

        // 属性数必须大于等于1
        Asserts.isTrue(PARAM_MAP.size() >= 1, "必须设置一条以上属性值，需要使用param(paramName,paramType)方法设置");

        // 插入条数必须大于等于1
        Asserts.isTrue(count >= 1, "插入条数应大于等于1条，需要使用insertCount(int)方法设置插入条数");
    }

    /**
     * 执行生成SQL语句的逻辑
     */
    private void generateSql() {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(sqlFile);

            for (int i = 0; i < count; i++) {
                // 生成参数名和参数值列表
                String[] paramNameAndValue = generateParamNameAndValue();

                // 参数名，如：name,age,sex,birthday
                String paramName = paramNameAndValue[0];
                // 参数值，如：'jack','13','0','1999-9-9 12:12:12'
                String paramValue = paramNameAndValue[1];

                // 拼接成insert语句
                String sql = String.format("insert into %s(%s) values(%s)", tableName, paramName, paramValue);

                // 根据 url 判断是否 sql server 数据库，进行特别处理
                String url = DatabaseHelper.getDataSource().getUrl();
                if (url.contains("sqlserver")) {
                    sql = String.format("insert into [dbo].[%s](%s) values(%s)", tableName, paramName, paramValue);
                }

                LOGGER.info(sql);

                // 如果需要插入到数据库，将生成的sql语句添加到LIST中
                if (this.isInsertDataToDb) {
                    writer.println(sql);
                    writer.flush();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 执行SQL语句，插入数据到数据库
     */
    private void executeSql() {
        // 不插入数据到数据库
        if (!this.isInsertDataToDb) {
            LOGGER.print(String.format("成功生成[ %s ]条数据", this.count));
            return;
        }

        try {
            // 开始事务
            DatabaseHelper.beginTransaction();

            // 批量执行sql语句，获取总的插入条数
            this.totalCount = DatabaseHelper.executeSqlFile(sqlFile);

            LOGGER.print(String.format("成功插入[ %s ]条数据",this.totalCount));
        } catch (Exception e) {
            e.printStackTrace();

            // 事务回滚
            DatabaseHelper.rollbackTransaction();
        }

        if (sqlFile.exists()) {
            boolean success = sqlFile.delete();

            if (success) {
                System.out.println("删除文件成功");
            } else {
                System.out.println("删除文件失败");
            }
        }
    }

    /**
     * 生成参数名和参数值列表数组
     * 数组返回值示范：
     * 数组【0】：name,age,sex,birthday
     * 数组【1】：'jack','13','0','1999-9-9 12:12:12'
     * @return 参数名和参数值列表数组
     */
    private String[] generateParamNameAndValue() {
        StringBuilder paramNameSb = new StringBuilder();
        StringBuilder paramValueSb = new StringBuilder();

        // 使用Map生成参数
        for (Map.Entry<String, Object> entry : PARAM_MAP.entrySet()) {
            String paramName = entry.getKey();
            Object paramType = entry.getValue();

            // 添加参数名
            paramNameSb.append(paramName).append(",");

            // 创建参数值（添加参数值）
            createParamValue(paramType, paramValueSb);
        }

        // 所有参数名，如：name,age,sex,birthday
        String paramNames = StringUtils.deleteLastComma(paramNameSb);

        // 所有参数值，如：'jack','13','0','1999-9-9 12:12:12'
        String paramValues = StringUtils.deleteLastComma(paramValueSb);

        return new String[]{ paramNames, paramValues };
    }

    /**
     * 创建参数值
     * @param paramType 参数类型
     * @param paramValueSB 参数值字符串
     */
    @SuppressWarnings("unchecked")
    private void createParamValue(Object paramType, StringBuilder paramValueSB) {
        Asserts.isTrue(paramType instanceof DataType
                        || paramType instanceof RandomData
                        || RandomData.class.isAssignableFrom((Class<?>) paramType),
                "传入的paramType必须是DataType枚举值或实现RandomData接口的类");

        Object target;

        if (paramType instanceof RandomData) {
            // 实现了RandomData接口
            target = paramType;
        } else {
            // 传入的是DataType枚举或Class类型
            if (paramType instanceof DataType) {
                // 是数据类型枚举
                DataType dataType = (DataType) paramType;

                // 从数据类型Map中获取对应的随机生成器类型
                paramType = DataTypeMapping.getMapping(dataType);
            }

            // 获取目标类的单例对象（实例化目标类）
            target = FlyweightUtils.getInstance((Class<?>) paramType);
        }

        // 生成随机数据（核心语句）
        Object randomData = ((RandomData<?>) target).next();

        // 将随机数据转换成字符串
        String data = (randomData instanceof String) ? (String) randomData : String.valueOf(randomData);

        // 在字符串两边加上单引号，然后加上逗号
        paramValueSB.append(StringUtils.addSingleQuotesAround(data)).append(",");
    }

}
