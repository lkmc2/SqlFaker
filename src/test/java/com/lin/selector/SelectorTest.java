package com.lin.selector;

import com.lin.sql.SqlSelector;
import com.lin.utils.DBTools;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 选择器测试
 * @author lkmc2
 * @date 2019/10/20 12:16
 */
public class SelectorTest {

    @Before
    public void setUp() {
        // 建立数据库连接
        DBTools.dbName("facker").connect();
    }

    @Test
    public void testSelectColumn() {
        // 选择 user 表的名称为 name 那一列的数据
        List<String> nameList = SqlSelector.selectColumn("user", "name");
        System.out.println(nameList);
    }

    @Test
    public void testSelectColumnBySql() {
        // 使用 sql 语句选择 user 表的名称为 name 那一列的数据
        List<String> nameList = SqlSelector.selectColumn("select distinct name from user");
        System.out.println(nameList);
    }

    @Test
    public void testSelectColumnUseUrl() {
        // 指定连接的数据库，并选择 user 表的名称为 name 那一列的数据
        List<String> nameList = SqlSelector.db("jdbc:mysql://127.0.0.1:3306/facker?allowMultiQueries=true&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF8", "root", "123456")
                                            .selectColumn("user", "name");

        System.out.println(nameList);
    }

    @Test
    public void testSelectColumnUseUrlBySql() {
        // 指定连接的数据库，并选择 user 表的名称为 name 那一列的数据
        List<String> nameList = SqlSelector.db("jdbc:mysql://127.0.0.1:3306/facker?allowMultiQueries=true&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF8", "root", "123456")
                                            .selectColumn("select distinct name from user");

        System.out.println(nameList);
    }

}
