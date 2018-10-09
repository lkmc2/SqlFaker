package com.lin;

import com.lin.datatype.DataType;
import com.lin.faker.Faker;
import com.lin.utils.DBTools;

/**
 * @author lkmc2
 * @date 2018/10/3
 * @description 基本使用示范类
 * @since 1.0.0
 */
public class Main {

    public static void main(String[] args) {

        // 更详细的使用例子参见 Example.java

        // 创建数据库连接
        DBTools.url("jdbc:mysql://localhost:3306/facker")
                .username("root")
                .password("123456")
                .driverClassName("com.mysql.jdbc.Driver")
                .connect();

        // 当数据库属性都等于默认值时，可只设置数据库名
//        DBTools.dbName("facker").connect();

        // 给user表的四个字段填充5条数据
        Faker.tableName("user")
                .param("name", DataType.USERNAME)
                .param("age", DataType.AGE)
                .param("sex", DataType.SEX)
                .param("birthday", DataType.TIME)
                .insertCount(5)
                .execute();

    }

}
