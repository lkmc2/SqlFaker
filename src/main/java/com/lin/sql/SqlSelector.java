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
 * @since 1.0.7
 */
public final class SqlSelector {

    /**
     * 选择表中的一列数据
     * @param tableName 表名
     * @param columnName 列表
     * @return 表中的一列数据
     */
    public static List<String> selectColumn(String tableName, String columnName) {
        String sql = String.format("select distinct %s from %s", columnName, tableName);
        return selectColumn(sql);
    }

    /**
     * 选择表中的一列数据
     * @param sql sql 语句
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

    /**
     * 配置数据库信息
     * @param url 数据库 url
     * @param username 数据库用户名
     * @param password 数据库密码
     * @return 内部 sql 选择器
     */
    public static InnerSqlSelector db(String url, String username, String password) {
        return new InnerSqlSelector(url, username, password);
    }

    /** 内部 SQL 选择器 **/
    public static class InnerSqlSelector {
        private String url;
        private String username;
        private String password;

        public InnerSqlSelector(String url, String username, String password) {
            this.url = url;
            this.username = username;
            this.password = password;
        }

        /**
         * 选择表中的一列数据
         * @param tableName 表名
         * @param columnName 列表
         * @return 表中的一列数据
         */
        public List<String> selectColumn(String tableName, String columnName) {
            String sql = String.format("select distinct %s from %s", columnName, tableName);
            return selectColumn(sql);
        }

        /**
         * 选择表中的一列数据
         * @param sql sql 语句
         * @return 表中的一列数据
         */
        public List<String> selectColumn(String sql) {
            // 执行 sql 并获取查询结果
            Result<Record> records = DSL.using(url, username, password).fetch(sql);

            // 获取第 1 列的值
            return records.getValues(0, String.class);
        }
    }


}
