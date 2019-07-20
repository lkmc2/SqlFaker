package com.lin.creator;

import java.util.*;

/**
 * MySQL 数据库 Faker 表创建器
 *
 * @author lkmc2
 * @since 1.0.2
 */
public class MysqlFakerCreator extends BaseFakerCreator {

    /**
     * 静态单例
     **/
    private static final class FakerCreatorHolder {
        private static final BaseFakerCreator INSTANCE = new MysqlFakerCreator();
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
        // 获取所有的表信息（表名、表注释）
        return String.format(
                "select table_name as tableName, table_comment as tableComment " +
                "from information_schema.tables " +
                "where table_schema='%s'", this.dbName);
    }

    @Override
    protected String getQueryFieldsInfoSql(String tableName) {
        // 获取表中信息（字段名、字段类型、字段注释）
        return String.format(
                "select column_name as fieldName,column_comment as comments,data_type as dataType " +
                "from information_schema.columns " +
                "where table_name='%s' and table_schema = '%s' " +
                "order by ordinal_position", tableName, this.dbName);
    }

    @Override
    protected String getDefaultUsername() {
        return "root";
    }

    @Override
    protected String getDefaultPassword() {
        return "123456";
    }

    @Override
    protected String getDefaultDriverClassName() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    protected String getDefaultUrlPrefix() {
        return "jdbc:mysql://localhost:3306/";
    }

    @Override
    protected void setDbStringTypeSet(Collection<String> dbStringTypeSet) {
        dbStringTypeSet.addAll(Arrays.asList(
                "CHAR", "VARCHAR", "TINYBLOB", "TINYTEXT", "BLOB",
                "TEXT", "MEDIUMBLOB", "MEDIUMTEXT", "LONGBLOB", "LONGTEXT"
        ));
    }

    @Override
    protected void setDatabaseInferMap(Map<String, String> databaseInferMap) {
        // 数值类型
        databaseInferMap.put("TINYINT", "INT");
        databaseInferMap.put("SMALLINT", "INT");
        databaseInferMap.put("MEDIUMINT", "INT");
        databaseInferMap.put("INT", "INT");
        databaseInferMap.put("INTEGER", "INT");
        databaseInferMap.put("BIGINT", "INT");
        databaseInferMap.put("FLOAT", "FLOAT");
        databaseInferMap.put("DOUBLE", "FLOAT");
        databaseInferMap.put("DECIMAL", "FLOAT");

        // 日期和时间类型
        databaseInferMap.put("DATE", "TIME");
        databaseInferMap.put("TIME", "TIME");
        databaseInferMap.put("YEAR", "TIME");
        databaseInferMap.put("DATETIME", "TIME");
        databaseInferMap.put("TIMESTAMP", "TIME");

        // 字符串类型
        databaseInferMap.put("CHAR", "TEXT");
        databaseInferMap.put("VARCHAR", "TEXT");
        databaseInferMap.put("TINYBLOB", "TEXT");
        databaseInferMap.put("TINYTEXT", "TEXT");
        databaseInferMap.put("BLOB", "TEXT");
        databaseInferMap.put("TEXT", "TEXT");
        databaseInferMap.put("MEDIUMBLOB", "TEXT");
        databaseInferMap.put("MEDIUMTEXT", "TEXT");
        databaseInferMap.put("LONGBLOB", "TEXT");
        databaseInferMap.put("LONGTEXT", "TEXT");
        databaseInferMap.put("VARBINARY", "TEXT");
    }

}
