package com.lin.creator;

import com.lin.creator.enums.DatabaseType;

/**
 * Faker 表创建器
 * @author lkmc2
 * @since 1.0.4
 */
public class FakerCreator {

    /** 记录数据库类型 **/
    private static DatabaseType databaseType = null;

    /** 静态单例 **/
    private static final class FakerCreatorHolder {
        private static final FakerCreator INSTANCE = new FakerCreator();
    }

    /**
     * 获取 MySQL 数据库的 Faker 表创建器
     * @return Faker表创建器
     */
    public static FakerCreator mysql() {
        return setDatabaseType(DatabaseType.MYSQL);
    }

    /**
     * 获取 SQL Server 数据库的 Faker 表创建器
     * @return Faker表创建器
     */
    public static FakerCreator sqlServer() {
        return setDatabaseType(DatabaseType.SQL_SERVER);
    }

    /**
     * 获取 Oracle 数据库的 Faker 表创建器
     * @return Faker表创建器
     */
    public static FakerCreator oracle() {
        return setDatabaseType(DatabaseType.ORACLE);
    }

    /**
     * 获取 Sqlite 数据库的 Faker 表创建器
     * @return Faker表创建器
     */
    public static FakerCreator sqlite() {
        return setDatabaseType(DatabaseType.SQLITE);
    }

    /**
     * 获取 H2 数据库的 Faker 表创建器
     * @return Faker表创建器
     */
    public static FakerCreator h2() {
        return setDatabaseType(DatabaseType.H2);
    }

    /**
     * 设置数据库类型，并获取 Faker 表创建器
     * @param dbType 数据库类型
     * @return Faker 表创建器
     */
    private static FakerCreator setDatabaseType(DatabaseType dbType) {
        databaseType = dbType;
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
            case ORACLE:
                return OracleFakerCreator.url(url);
            case SQLITE:
                return SqliteFakerCreator.url(url);
            case H2:
                return H2FakerCreator.url(url);
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
            case ORACLE:
                return OracleFakerCreator.dbName(dbName);
            case SQLITE:
                return SqliteFakerCreator.dbName(dbName);
            case H2:
                return H2FakerCreator.dbName(dbName);
            default:
                throw new RuntimeException("未找到合适的数据库类型！");
        }
    }

}
