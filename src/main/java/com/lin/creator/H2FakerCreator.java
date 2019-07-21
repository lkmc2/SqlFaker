package com.lin.creator;

import com.lin.entity.h2.H2FieldInfo;
import com.lin.entity.h2.H2TableInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * H2 数据库 Faker 表创建器
 *
 * @author lkmc2
 * @since 1.0.4
 */
public class H2FakerCreator extends BaseFakerCreator<H2TableInfo, H2FieldInfo> {

    /**
     * 静态单例
     **/
    private static final class FakerCreatorHolder {
        private static final BaseFakerCreator INSTANCE = new H2FakerCreator();
    }

    /**
     * 创建数据库连接配置工具实例，并设置数据库连接url
     *
     * @param url 数据库连接url
     * @return Faker表创建器
     */
    public static BaseFakerCreator url(String url) {
        return FakerCreatorHolder.INSTANCE.setUrl(url);
    }

    /**
     * 创建数据库连接配置工具实例，并设置连接的数据库名
     *
     * @param dbName 数据库名
     * @return Faker表创建器
     */
    public static BaseFakerCreator dbName(String dbName) {
        return FakerCreatorHolder.INSTANCE.setDbName(dbName);
    }

    @Override
    protected String getQueryTablesInfoSql() {
        // 获取所有的表名（该数据没有表注释）
        return "show tables";
    }

    @Override
    protected String getQueryFieldsInfoSql(String tableName) {
        // 获取表中信息（字段名、字段类型），该数据库没有字段注释
        return String.format("show columns from %s", tableName);
    }

    @Override
    protected String getDefaultUsername() {
        return "sa";
    }

    @Override
    protected String getDefaultPassword() {
        return "";
    }

    @Override
    protected String getDefaultDriverClassName() {
        return "org.h2.Driver";
    }

    @Override
    protected String getDefaultUrlPrefix() {
        return "jdbc:h2:";
    }

    @Override
    protected void setDbStringTypeSet(Collection<String> dbStringTypeSet) {
        dbStringTypeSet.addAll(Arrays.asList(
                "CHAR", "NCHAR", "CHARACTER", "VARCHAR", "VARCHAR_IGNORECASE",
                "NVARCHAR", "NATIVE CHARACTER", "VARYING CHARACTER", "TINYBLOB",
                "TINYTEXT", "BINARY", "BLOB", "CLOB", "TEXT", "MEDIUMBLOB",
                "MEDIUMTEXT", "LONGBLOB", "LONGTEXT", "VARBINARY", "UUID",
                "ARRAY", "OTHER", "GEOMETRY"
        ));
    }

    @Override
    protected void setDatabaseInferMap(Map<String, String> databaseInferMap) {
        // 数值类型
        databaseInferMap.put("TINYINT", "INT");
        databaseInferMap.put("SMALLINT", "INT");
        databaseInferMap.put("MEDIUMINT", "INT");
        databaseInferMap.put("INT", "INT");
        databaseInferMap.put("INT2", "INT");
        databaseInferMap.put("INT8", "INT");
        databaseInferMap.put("INTEGER", "INT");
        databaseInferMap.put("BIGINT", "INT");
        databaseInferMap.put("UNSIGNED BIG INT", "INT");
        databaseInferMap.put("INTERVAL", "INT");
        databaseInferMap.put("FLOAT", "FLOAT");
        databaseInferMap.put("REAL", "FLOAT");
        databaseInferMap.put("DOUBLE", "FLOAT");
        databaseInferMap.put("DOUBLE PRECISION", "FLOAT");
        databaseInferMap.put("NUMERIC", "FLOAT");
        databaseInferMap.put("DECIMAL", "FLOAT");
        databaseInferMap.put("BOOLEAN", "FLOAT");
        databaseInferMap.put("ENUM", "FLOAT");
        databaseInferMap.put("IDENTITY", "FLOAT");

        // 日期和时间类型
        databaseInferMap.put("DATE", "TIME");
        databaseInferMap.put("TIME", "TIME");
        databaseInferMap.put("YEAR", "TIME");
        databaseInferMap.put("DATETIME", "TIME");
        databaseInferMap.put("TIMESTAMP", "TIME");
        databaseInferMap.put("TIMESTAMP WITH TIME ZONE", "TIME");

        // 字符串类型
        databaseInferMap.put("CHAR", "TEXT");
        databaseInferMap.put("NCHAR", "TEXT");
        databaseInferMap.put("CHARACTER", "TEXT");
        databaseInferMap.put("VARCHAR", "TEXT");
        databaseInferMap.put("VARCHAR_IGNORECASE", "TEXT");
        databaseInferMap.put("NVARCHAR", "TEXT");
        databaseInferMap.put("NATIVE CHARACTER", "TEXT");
        databaseInferMap.put("VARYING CHARACTER", "TEXT");
        databaseInferMap.put("TINYBLOB", "TEXT");
        databaseInferMap.put("TINYTEXT", "TEXT");
        databaseInferMap.put("BINARY", "TEXT");
        databaseInferMap.put("BLOB", "TEXT");
        databaseInferMap.put("CLOB", "TEXT");
        databaseInferMap.put("TEXT", "TEXT");
        databaseInferMap.put("MEDIUMBLOB", "TEXT");
        databaseInferMap.put("MEDIUMTEXT", "TEXT");
        databaseInferMap.put("LONGBLOB", "TEXT");
        databaseInferMap.put("LONGTEXT", "TEXT");
        databaseInferMap.put("VARBINARY", "TEXT");
        databaseInferMap.put("UUID", "TEXT");
        databaseInferMap.put("ARRAY", "TEXT");
        databaseInferMap.put("OTHER", "TEXT");
        databaseInferMap.put("GEOMETRY", "TEXT");
    }

}
