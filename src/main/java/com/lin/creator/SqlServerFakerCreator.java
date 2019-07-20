package com.lin.creator;

import java.util.*;

/**
 * Sql Server数据库 Faker 表创建器
 *
 * @author lkmc2
 * @since 1.0.3
 */
public class SqlServerFakerCreator extends BaseFakerCreator {

    /**
     * 静态单例
     **/
    private static final class FakerCreatorHolder {
        private static final BaseFakerCreator INSTANCE = new SqlServerFakerCreator();
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
                "select distinct d.name as tableName, convert(nvarchar(50),isnull(f.value,'')) as tableComment " +
                "from syscolumns a " +
                "inner join sysobjects d on a.id = d.id  and d.xtype = 'U' and  d.name <> 'dtproperties' " +
                "left join sys.extended_properties f on d.id = f.major_id and f.minor_id = 0 " +
                "where d.name is not null and d.name <> 'sysdiagrams'";
    }

    @Override
    protected String getQueryFieldsInfoSql(String tableName) {
        // 获取表中信息（字段名、字段类型、字段注释）
        return String.format(
                "select a.name as fieldName, b.name as dataType, convert(nvarchar(50),isnull(g.[value],'')) as comments " +
                "from syscolumns a " +
                "left join systypes b on a.xusertype = b.xusertype " +
                "inner join sysobjects d on a.id = d.id  and d.xtype = 'U' and  d.name<>'dtproperties' " +
                "left join sys.extended_properties g on a.id = g.major_id and a.colid = g.minor_id  " +
                "where d.name='%s' " +
                "order by a.id, a.colorder", tableName);
    }

    @Override
    protected String getDefaultUsername() {
        return "sa";
    }

    @Override
    protected String getDefaultPassword() {
        return "123456";
    }

    @Override
    protected String getDefaultDriverClassName() {
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }

    @Override
    protected String getDefaultUrlPrefix() {
        return "jdbc:sqlserver://localhost:1433;DatabaseName=";
    }

    @Override
    protected void setDbStringTypeSet(Collection<String> dbStringTypeSet) {
        dbStringTypeSet.addAll(Arrays.asList(
                "CHAR", "VARCHAR", "NVARCHAR", "TINYBLOB", "TINYTEXT", "BLOB",
                "TEXT", "MEDIUMBLOB", "MEDIUMTEXT", "LONGBLOB", "LONGTEXT",
                "VARBINARY", "BINARY", "UDT", "UNIQUEIDENTIFIER", "XML",
                "SQLVARIANT", "GEOMETRY", "IMAGE", "GEOGRAPHY"
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
        databaseInferMap.put("NCHAR", "FLOAT");
        databaseInferMap.put("NTEXT", "FLOAT");
        databaseInferMap.put("NUMERIC", "FLOAT");
        databaseInferMap.put("REAL", "FLOAT");

        // 日期和时间类型
        databaseInferMap.put("DATE", "TIME");
        databaseInferMap.put("TIME", "TIME");
        databaseInferMap.put("YEAR", "TIME");
        databaseInferMap.put("DATETIME", "TIME");
        databaseInferMap.put("DATETIME2", "TIME");
        databaseInferMap.put("DATETIMEOFFSET", "TIME");
        databaseInferMap.put("TIMESTAMP", "TIME");
        databaseInferMap.put("SMALLDATETIME", "TIME");

        // 字符串类型
        databaseInferMap.put("CHAR", "TEXT");
        databaseInferMap.put("VARCHAR", "TEXT");
        databaseInferMap.put("NVARCHAR", "TEXT");
        databaseInferMap.put("TINYBLOB", "TEXT");
        databaseInferMap.put("TINYTEXT", "TEXT");
        databaseInferMap.put("BLOB", "TEXT");
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
    }

}
