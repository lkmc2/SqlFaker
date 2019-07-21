package com.lin.creator;

import com.lin.entity.common.CommonFieldInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Oracle 数据库 Faker 表创建器
 *
 * @author lkmc2
 * @since 1.0.4
 */
public class OracleFakerCreator extends BaseFakerCreator<CommonFieldInfo> {

    /**
     * 静态单例
     **/
    private static final class FakerCreatorHolder {
        private static final BaseFakerCreator INSTANCE = new OracleFakerCreator();
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
        return
                "select table_name as tableName, comments as tableComment " +
                "from user_tab_comments";
    }

    @Override
    protected String getQueryFieldsInfoSql(String tableName) {
        // 获取表中信息（字段名、字段类型、字段注释）
        return String.format(
                "select a.column_name as fieldName, a.data_type as dataType, b.comments as comments " +
                "from user_tab_columns a " +
                "left join user_col_comments b on b.column_name = a.column_name " +
                "where a.table_name = '%s' " +
                "and b.table_name = '%s' " +
                "order by a.column_id", tableName.toUpperCase(), tableName.toUpperCase());
    }

    @Override
    protected String getDefaultUsername() {
        return "test1";
    }

    @Override
    protected String getDefaultPassword() {
        return "123456";
    }

    @Override
    protected String getDefaultDriverClassName() {
        return "oracle.jdbc.driver.OracleDriver";
    }

    @Override
    protected String getDefaultUrlPrefix() {
        return "jdbc:oracle:thin:@localhost:1521/";
    }

    @Override
    protected void setDbStringTypeSet(Collection<String> dbStringTypeSet) {
        dbStringTypeSet.addAll(Arrays.asList(
                "CHAR", "NCHAR", "VARCHAR", "VARCHAR2", "NVARCHAR", "NVARCHAR2", "TINYBLOB",
                "TINYTEXT", "BLOB", "CLOB", "NCLOB", "BFILE", "TEXT", "MEDIUMBLOB",
                "MEDIUMTEXT", "LONGBLOB", "LONGTEXT", "VARBINARY", "BINARY", "UDT",
                "UNIQUEIDENTIFIER", "XML", "SQLVARIANT", "GEOMETRY", "IMAGE", "GEOGRAPHY",
                "ROWID", "NROWID", "RAW", "LONG", "LONG RAW"
        ));
    }

    @Override
    protected void setDatabaseInferMap(Map<String, String> databaseInferMap) {
        // 数值类型
        databaseInferMap.put("BIGINT", "INT");
        databaseInferMap.put("TINYINT", "INT");
        databaseInferMap.put("SMALLINT", "INT");
        databaseInferMap.put("MEDIUMINT", "INT");
        databaseInferMap.put("INT", "INT");
        databaseInferMap.put("INTEGER", "INT");
        databaseInferMap.put("SMALLMONEY", "INT");
        databaseInferMap.put("FLOAT", "FLOAT");
        databaseInferMap.put("DOUBLE", "FLOAT");
        databaseInferMap.put("DECIMAL", "FLOAT");
        databaseInferMap.put("BIT", "FLOAT");
        databaseInferMap.put("SSNOVERSION", "FLOAT");
        databaseInferMap.put("MONEY", "FLOAT");
        databaseInferMap.put("NTEXT", "FLOAT");
        databaseInferMap.put("NUMERIC", "FLOAT");
        databaseInferMap.put("NUMBER", "FLOAT");
        databaseInferMap.put("REAL", "FLOAT");
        databaseInferMap.put("BINARY_FLOAT", "FLOAT");
        databaseInferMap.put("BINARY_DOUBLE", "FLOAT");

        // 日期和时间类型
        databaseInferMap.put("DATE", "TIME");
        databaseInferMap.put("TIME", "TIME");
        databaseInferMap.put("YEAR", "TIME");
        databaseInferMap.put("DAY", "TIME");
        databaseInferMap.put("HOUR", "TIME");
        databaseInferMap.put("MINUTE", "TIME");
        databaseInferMap.put("SECOND", "TIME");
        databaseInferMap.put("TIMEZONE_HOUR", "TIME");
        databaseInferMap.put("TIMEZONE_MINUTE", "TIME");
        databaseInferMap.put("TIMEZONE_REGION", "TIME");
        databaseInferMap.put("TIMEZONE_ABBR ", "TIME");
        databaseInferMap.put("DATETIME", "TIME");
        databaseInferMap.put("DATETIME2", "TIME");
        databaseInferMap.put("DATETIMEOFFSET", "TIME");
        databaseInferMap.put("TIMESTAMP", "TIME");
        databaseInferMap.put("INTERVAL YEAR", "TIME");
        databaseInferMap.put("INTERVAL DAY", "TIME");
        databaseInferMap.put("SMALLDATETIME", "TIME");

        // 字符串类型
        databaseInferMap.put("CHAR", "TEXT");
        databaseInferMap.put("NCHAR", "TEXT");
        databaseInferMap.put("VARCHAR", "TEXT");
        databaseInferMap.put("VARCHAR2", "TEXT");
        databaseInferMap.put("NVARCHAR", "TEXT");
        databaseInferMap.put("NVARCHAR2", "TEXT");
        databaseInferMap.put("TINYBLOB", "TEXT");
        databaseInferMap.put("TINYTEXT", "TEXT");
        databaseInferMap.put("BLOB", "TEXT");
        databaseInferMap.put("CLOB", "TEXT");
        databaseInferMap.put("NCLOB", "TEXT");
        databaseInferMap.put("BFILE", "TEXT");
        databaseInferMap.put("TEXT", "TEXT");
        databaseInferMap.put("MEDIUMBLOB", "TEXT");
        databaseInferMap.put("MEDIUMTEXT", "TEXT");
        databaseInferMap.put("LONGBLOB", "TEXT");
        databaseInferMap.put("LONGTEXT", "TEXT");
        databaseInferMap.put("VARBINARY", "TEXT");
        databaseInferMap.put("BINARY", "TEXT");
        databaseInferMap.put("UDT", "TEXT");
        databaseInferMap.put("UNIQUEIDENTIFIER", "TEXT");
        databaseInferMap.put("XML", "TEXT");
        databaseInferMap.put("SQLVARIANT", "TEXT");
        databaseInferMap.put("GEOMETRY", "TEXT");
        databaseInferMap.put("IMAGE", "TEXT");
        databaseInferMap.put("GEOGRAPHY", "TEXT");
        databaseInferMap.put("ROWID", "TEXT");
        databaseInferMap.put("NROWID", "TEXT");
        databaseInferMap.put("RAW", "TEXT");
        databaseInferMap.put("LONG", "TEXT");
        databaseInferMap.put("LONG RAW", "TEXT");
    }

}
