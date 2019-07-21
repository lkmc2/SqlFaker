package com.lin.creator;

import com.lin.entity.sqlite.SqliteFieldInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Sqlite 数据库 Faker 表创建器
 *
 * @author lkmc2
 * @since 1.0.4
 */
public class SqliteFakerCreator extends BaseFakerCreator<SqliteFieldInfo> {

    /**
     * 静态单例
     **/
    private static final class FakerCreatorHolder {
        private static final BaseFakerCreator INSTANCE = new SqliteFakerCreator();
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
        return
                "SELECT name as tableName " +
                "FROM sqlite_master " +
                "WHERE type = 'table'";
    }

    @Override
    protected String getQueryFieldsInfoSql(String tableName) {
        // 获取表中信息（字段名、字段类型），该数据库没有字段注释
        return String.format("PRAGMA table_info(%s)", tableName);
    }

    @Override
    protected String getDefaultUsername() {
        return "";
    }

    @Override
    protected String getDefaultPassword() {
        return "";
    }

    @Override
    protected String getDefaultDriverClassName() {
        return "org.sqlite.JDBC";
    }

    @Override
    protected String getDefaultUrlPrefix() {
        return "jdbc:sqlite:";
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
