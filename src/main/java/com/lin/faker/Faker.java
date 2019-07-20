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


    // 初始化参数存储Map和数据类型映射Map/
    {
        PARAM_MAP = Collections.synchronizedMap(new LinkedHashMap<String, Object>(10));
    }

    private Faker() {
    }

    /** 静态单例 **/
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

        // 2.执行生成并执行SQL语句的逻辑
        generateAndExecuteSql();
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
     * 执行生成并执行SQL语句的逻辑
     */
    private void generateAndExecuteSql() {
        // 提交事务时生成的 sql 数量
        int commitSize = 1000;

        try {
            // 如果需要插入到数据库
            if (this.isInsertDataToDb) {
                // 开始事务
                DatabaseHelper.beginTransaction();
            }

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

                // 如果需要插入到数据库，执行生成的 sql 语句
                if (this.isInsertDataToDb) {
                    // 执行sql，获取受影响行数
                    int effectCount = DatabaseHelper.executeUpdate(sql);

                    // 统计总的插入条数
                    this.totalCount += effectCount;

                    if (this.totalCount % commitSize == 0) {
                        // 每执行 1000 条 sql 就提交事务
                        DatabaseHelper.commitTransaction();
                    }
                }
            }

            // 如果需要插入到数据库
            if (this.isInsertDataToDb) {
                // 当还有未提交的 sql 时，提交事务
                if (this.totalCount % commitSize != 0) {
                    // 提交事务
                    DatabaseHelper.commitTransaction();
                }
            }

            if (this.isInsertDataToDb) {
                // 插入数据到数据库
                LOGGER.print(String.format("成功插入[ %s ]条数据",this.totalCount));
            } else {
                // 不插入数据到数据库
                LOGGER.print(String.format("成功生成[ %s ]条数据", this.count));
            }
        } catch (Exception e) {
            e.printStackTrace();

            // 如果需要插入到数据库
            if (this.isInsertDataToDb) {
                // 事务回滚
                DatabaseHelper.rollbackTransaction();
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
     * @param paramValueSb 参数值字符串
     */
    @SuppressWarnings("unchecked")
    private void createParamValue(Object paramType, StringBuilder paramValueSb) {
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
        paramValueSb.append(StringUtils.addSingleQuotesAround(data)).append(",");
    }

}
