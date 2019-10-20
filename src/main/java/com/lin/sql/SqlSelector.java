package com.lin.sql;

import com.lin.asserts.Asserts;
import com.lin.helper.DatabaseHelper;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.Connection;
import	java.util.List;

/**
 * SQL 选择器
 * @author lkmc2
 * @date 2019/10/20 12:04
 */
public final class SqlSelector {

    /**
     * 选择表中的一列数据
     * @param tableName 表名
     * @param columnName 列表
     * @return 表中的一列数据
     */
    public static List<String> selectColumn(String tableName, String columnName) {
        Connection connection = DatabaseHelper.getConnection();
        Asserts.isTrue(connection != null, "数据库连接获取失败，请先在 DBTools 中设置连接参数");

        String sql = String.format("select distinct %s from %s", columnName, tableName);

        // 执行 sql 并获取查询结果
        Result<Record> records = DSL.using(connection).fetch(sql);

        // 获取第 1 列的值
        return records.getValues(0, String.class);
    }

    /**
     * 选择表中的一列数据
     * @param sql sq 语句
     * @return 表中的一列数据
     */
    public static List<String> selectColumn(String sql) {
        Connection connection = DatabaseHelper.getConnection();
        Asserts.isTrue(connection != null, "数据库连接获取失败，请先在 DBTools 中设置连接参数");

        // 执行 sql 并获取查询结果
        Result<Record> records = DSL.using(connection).fetch(sql);

        // 获取第 1 列的值
        return records.getValues(0, String.class);
    }

}
