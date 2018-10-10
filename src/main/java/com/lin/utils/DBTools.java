package com.lin.utils;

import com.lin.helper.DatabaseHelper;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * 数据库连接配置工具
 * @author lkmc2
 * @since 1.0.0
 */
public final class DBTools {

    // 数据库连接url
    private String url;

    // 数据库用户名
    private String username;

    // 数据库密码
    private String password;

    // 数据库驱动名
    private String driverClassName;

    private DBTools() {
    }

    // 静态单例
    private static final class DBToolsHolder {
        private static final DBTools INSTANCE = new DBTools();
    }

    /**
     * 创建数据库连接配置工具实例，并设置数据库连接url
     * @param url 数据库连接url
     * @return 数据库连接配置工具
     */
    public static DBTools url(String url) {
        return DBToolsHolder.INSTANCE.setUrl(url);
    }

    /**
     * 设置数据库连接url
     * @param url 数据库连接url
     * @return 数据库连接配置工具
     */
    private DBTools setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 创建数据库连接配置工具实例，并设置连接的数据库名
     * @param dbName 数据库名
     * @return 数据库连接配置工具
     */
    public static DBTools dbName(String dbName) {
        return DBToolsHolder.INSTANCE.setDbName(dbName);
    }

    /**
     * 设置连接的数据库名
     * @param dbName 数据库名
     * @return 数据库连接配置工具
     * 默认会加上前缀：jdbc:mysql://localhost:3306/
     */
    private DBTools setDbName(String dbName) {
        if (this.url == null) {
            this.url = "jdbc:mysql://localhost:3306/" + dbName;
        }
        return this;
    }

    /**
     * 设置连接的数据库用户名
     * @param username 数据库用户名
     * @return 数据库连接配置工具
     * 默认值为：root
     */
    public DBTools username(String username) {
        this.username = username;
        return this;
    }

    /**
     * 设置连接的数据库密码
     * @param password 数据库密码
     * @return 数据库连接配置工具
     * 默认值为：123456
     */
    public DBTools password(String password) {
        this.password = password;
        return this;
    }

    /**
     * 设置连接的数据库驱动名
     * @param driverClassName 数据库驱动名
     * @return 数据库连接配置工具
     * 默认值为：com.mysql.jdbc.Driver
     */
    public DBTools driverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    /**
     * 进行数据库连接
     */
    public void connect() {
        // 获取数据库连接池
        BasicDataSource dataSource = DatabaseHelper.getDataSource();

        if (url.isEmpty()) {
            throw new RuntimeException("数据库url不能为空");
        }

        // 对url进行处理
        if (!url.contains("?")) {
            // url不包括问号时，添加SSL和编码信息
            url += "?useSSL=true&characterEncoding=utf8";
        } else if (url.contains("?") && !url.toLowerCase().contains("utf8")) {
            // url包括参数但不包括编码信息时，添加编码信息
            url += "&characterEncoding=utf8";
        }

        // 设置数据库信息
        dataSource.setUrl(url);
        dataSource.setUsername((username != null) ? username : "root");
        dataSource.setPassword((password != null) ? password : "123456");
        dataSource.setDriverClassName((driverClassName != null) ? driverClassName : "com.mysql.jdbc.Driver");
    }

}
