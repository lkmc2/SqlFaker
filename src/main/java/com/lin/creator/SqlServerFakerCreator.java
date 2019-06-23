package com.lin.creator;

import java.util.*;

/**
 * Sql Server数据库 Faker 表创建器
 * @since 1.0.3
 * @author lkmc2
 */
public class SqlServerFakerCreator extends BaseFakerCreator {

    /** 静态单例 **/
    private static final class FakerCreatorHolder {
        private static final BaseFakerCreator INSTANCE = new SqlServerFakerCreator();
    }

    /**
     * 创建数据库连接配置工具实例，并设置数据库连接url
     * @param url 数据库连接url
     * @return Faker表创建器
     */
    public static BaseFakerCreator url(String url) {
        return FakerCreatorHolder.INSTANCE.setUrl(url);
    }

    /**
     * 创建数据库连接配置工具实例，并设置连接的数据库名
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
                "left join sys.extended_properties f on d.id=f.major_id and f.minor_id = 0 " +
                "where d.name is not null and d.name <> 'sysdiagrams'";
    }

    @Override
    protected String getQueryFieldsInfoSql(String tableName) {
        return null;
    }

    @Override
    protected String getDefaultUsername() {
        return null;
    }

    @Override
    protected String getDefaultPassword() {
        return null;
    }

    @Override
    protected String getDefaultDriverClassName() {
        return null;
    }

    @Override
    protected String getDefaultUrlPrefix() {
        return null;
    }

    @Override
    protected void setDbStringTypeList(List<String> dbStringTypeList) {

    }

    @Override
    protected void setDatabaseInferMap(Map<String, String> databaseInferMap) {

    }

}
