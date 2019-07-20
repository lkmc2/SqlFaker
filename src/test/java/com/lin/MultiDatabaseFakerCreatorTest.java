package com.lin;

import com.lin.creator.FakerCreator;
import org.junit.Test;

/**
 * 多数据库 FakerCreator 测试
 * @author lkmc2
 */
public class MultiDatabaseFakerCreatorTest {

    @Test
    public void testMySQL() {
        // 为 MySQL 数据库的所有表生成带 Faker 表结构的 java 文件
        // 方式1：简单设置数据库名，并创建Faker表结构
//        FakerCreator.mysql().dbName("test").build();

        // 方式2：完整设置数据库信息，并创建Faker表结构
        FakerCreator.mysql()
                .url("jdbc:mysql://localhost:3306/facker")
                .username("root")
                .password("123456")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Test
    public void tesSqlServer() {
        // 为 Sql Server 数据库的所有表生成带 Faker 表结构的 java 文件
        // 方式1：简单设置数据库名，并创建Faker表结构
//        FakerCreator.sqlServer().dbName("facker").build();

        // 方式2：完整设置数据库信息，并创建Faker表结构
        FakerCreator.sqlServer()
                .url("jdbc:sqlserver://localhost:1433;DatabaseName=facker")
                .username("sa")
                .password("123456")
                .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                .build();
    }

    @Test
    public void testOracle() {
        // 为 Oracle 数据库的所有表生成带 Faker 表结构的 java 文件
        // 方式1：简单设置数据库名，并创建Faker表结构
//        FakerCreator.oracle().dbName("orcl").build();

        // 方式2：完整设置数据库信息，并创建Faker表结构
        FakerCreator.oracle()
                .url("jdbc:oracle:thin:@localhost:1521/orcl")
                .username("test1")
                .password("123456")
                .driverClassName("oracle.jdbc.driver.OracleDriver")
                .build();
    }

}
