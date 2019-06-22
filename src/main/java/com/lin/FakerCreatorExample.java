package com.lin;

import com.lin.creator.MysqlFakerCreator;

/**
 * FakerCreator的示范例子
 * @author lkmc2
 * @since 1.0.1
 */
public class FakerCreatorExample {

    public static void main(String[] args) {
        // 简单设置数据库名，并创建Faker表结构
//        MysqlFakerCreator.dbName("test").build();

        // 完整设置数据库信息，并创建Faker表结构
        MysqlFakerCreator.url("jdbc:mysql://localhost:3306/test")
                .username("root")
                .password("123456")
                .build();

        // 执行以上代码，将对数据库的所有表生成 Faker 形式的 java 文件
    }

}
