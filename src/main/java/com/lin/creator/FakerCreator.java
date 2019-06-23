package com.lin.creator;

/**
 * Faker 表创建器
 * @author lkmc2
 * @since 1.0.4
 */
public class FakerCreator {

    /** 数据库类型常量 **/
    private static final String SQL_SERVER = "sqlServer";
    private static final String MYSQL = "mysql";

    /** 记录数据库类型 **/
    private static String databaseType = null;

    /** 静态单例 **/
    private static final class FakerCreatorHolder {
        private static final FakerCreator INSTANCE = new FakerCreator();
    }

    /**
     * 获取 MySQL 数据库的 Faker 表创建器
     * @return Faker表创建器
     */
    public static FakerCreator mysql() {
        databaseType = MYSQL;
        return FakerCreatorHolder.INSTANCE;
    }

    /**
     * 获取 SQL Server 数据库的 Faker 表创建器
     * @return Faker表创建器
     */
    public static FakerCreator sqlServer() {
       databaseType = SQL_SERVER;
        return FakerCreatorHolder.INSTANCE;
    }

    /**
     * 设置数据库的 url 地址
     * @param url 数据库的 url 地址
     * @return 数据库的 Faker 表创建器
     */
    public BaseFakerCreator url(String url) {
        switch (databaseType) {
            case MYSQL:
                return MysqlFakerCreator.url(url);
            case SQL_SERVER:
                return SqlServerFakerCreator.url(url);
            default:
                throw new RuntimeException("未找到合适的数据库类型！");
        }
    }

    /**
     * 设置数据库的名称
     * @param dbName 数据库的名称
     * @return 数据库的 Faker 表创建器
     */
    public BaseFakerCreator dbName(String dbName) {
        switch (databaseType) {
            case MYSQL:
                return MysqlFakerCreator.dbName(dbName);
            case SQL_SERVER:
                return SqlServerFakerCreator.dbName(dbName);
            default:
                throw new RuntimeException("未找到合适的数据库类型！");
        }
    }

}
