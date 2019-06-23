package com.lin;

import com.lin.creator.MysqlFakerCreator;
import com.lin.creator.SqlServerFakerCreator;

/**
 * FakerCreator的示范例子
 * @author lkmc2
 * @since 1.0.1
 */
public class FakerCreatorExample {

    public static void main(String[] args) {
        // 为 MySQL 数据库的所有表生成带 Faker 表结构的 java 文件
        // 方式1：简单设置数据库名，并创建Faker表结构
        MysqlFakerCreator.dbName("test").build();

        // 方式2：完整设置数据库信息，并创建Faker表结构
        MysqlFakerCreator.url("jdbc:mysql://localhost:3306/test")
                .username("root")
                .password("123456")
                .driverClassName("com.mysql.jdbc.Driver")
                .build();

        // 为 Sql Server 数据库的所有表生成带 Faker 表结构的 java 文件
        // 方式1：简单设置数据库名，并创建Faker表结构
        SqlServerFakerCreator.dbName("test").build();

        // 方式2：完整设置数据库信息，并创建Faker表结构
        SqlServerFakerCreator.url("jdbc:sqlserver://localhost:1433;DatabaseName=test")
                .username("sa")
                .password("123456")
                .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                .build();

        // 执行以上代码，将对数据库的所有表生成 Faker 形式的 java 文件
    }

}
