package com.lin.helper;

import com.lin.logger.Logger;
import com.lin.logger.LoggerFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * 数据库操作助手类
 * @author lkmc2
 * @since 1.0.0
 */
public final class DatabaseHelper {

    // 日志记录器
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    // 持有数据库连接的本地线程变量
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    // 查询执行器
    private static final QueryRunner QUERY_RUNNER;

    // 数据库连接池
    private static final BasicDataSource DATA_SOURCE;


    static {
        CONNECTION_HOLDER = new ThreadLocal<Connection>();
        QUERY_RUNNER = new QueryRunner();

        // 创建数据库连接
        DATA_SOURCE = new BasicDataSource();
    }

    /**
     * 获取数据源对象
     * @return 数据源对象
     */
    public static BasicDataSource getDataSource() {
        return DATA_SOURCE;
    }

    /**
     * 获取数据库连接
     * @return 数据库连接
     */
    public static Connection getConnection() {
        // 获取本地线程的数据库连接
        Connection conn = CONNECTION_HOLDER.get();

        // 数据库连接为空
        if (conn == null) {
            try {
                // 获取数据库连接
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("获取数据库连接失败");
                throw new RuntimeException(e);
            } finally {
                // 设置数据库连接到本地线程
                CONNECTION_HOLDER.set(conn);
            }
        }

        return conn;
    }


    /**
     * 查询实体列表
     * @param entityClass 实体类型
     * @param sql 执行的sql
     * @param params sql对应的参数
     * @param <T> 实体泛型
     * @return 实体列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;

        try {
            // 获取数据库连接
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (Exception e) {
            LOGGER.error("查询实体列表失败");
            throw new RuntimeException(e);
        }

        return entityList;
    }

    /**
     * 查询实体
     * @param entityClass 实体类型
     * @param sql 执行的sql
     * @param params sql对应的参数
     * @param <T> 实体泛型
     * @return 实体
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;

        try {
            // 获取数据库连接
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("查询实体失败");
            throw new RuntimeException(e);
        }

        return entity;
    }

    /**
     * 执行查询语句（可连接多表查询）
     * @param sql 查询语句
     * @param params sql对应的参数
     * @return 查询结果
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result;

        try {
            // 获取数据库连接
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("执行查询失败");
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 查询并返回单个列值
     * @param sql 查询语句
     * @param params sql对应的参数
     * @param <T> 返回值泛型
     * @return 查询结果
     */
    public static <T> T query(String sql, Object... params) {
        T obj;

        try {
            // 获取数据库连接
            Connection conn = getConnection();
            obj = QUERY_RUNNER.query(conn, sql, new ScalarHandler<T>(), params);
        } catch (SQLException e) {
            LOGGER.error("执行查询失败");
            throw new RuntimeException(e);
        }

        return obj;
    }

    /**
     * 查询并返回多个列值
     * @param sql 查询语句
     * @param params sql对应的参数
     * @param <T> 返回值泛型
     * @return 查询结果列表
     */
    public static <T> List<T> queryList(String sql, Object... params) {
        List<T> list;

        try {
            // 获取数据库连接
            Connection conn = getConnection();
            list = QUERY_RUNNER.query(conn, sql, new ColumnListHandler<T>(), params);
        } catch (SQLException e) {
            LOGGER.error("执行查询并返回列值失败");
            throw new RuntimeException(e);
        }

        return list;
    }

    /**
     * 查询并返回多个列值（去重）
     * @param sql 查询语句
     * @param params 查询参数
     * @param <T> 返回值泛型
     * @return 查询结果集合
     */
    public static <T> Set<T> querySet(String sql, Object... params) {
        Collection<T> valueList = queryList(sql, params);
        return new LinkedHashSet<T>(valueList);
    }

    /**
     * 执行更新语句（包括update、insert、delete）
     * @param sql 执行的sql
     * @param params sql对应的参数
     * @return 受影响行数
     */
    public static int executeUpdate(String sql, Object... params) {
        // 受影响行数
        int rows = 0;

        try {
            // 获取数据库连接
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOGGER.error("执行更新失败");
            throw new RuntimeException(e);
        }

        return rows;
    }

    /**
     * 执行更新语句（包括update、insert、delete）
     * @param sqlList sql 列表
     * @return 受影响行数
     */
    public static int executeInsertBatch(List<String> sqlList) {
        // 受影响行数
        int effectCount = 0;
        // 当前行数
        int rowsCount = 0;
        // 批量提交条数
        final int commitSize = 1000;

        try {
            // 获取数据库连接
            Connection conn = getConnection();
            conn.setAutoCommit(false);

            for (String sql : sqlList) {
                effectCount += QUERY_RUNNER.update(conn, sql);

                rowsCount++;

                if(rowsCount % commitSize == 0){
                    conn.commit();
                }
            }

            // 存在未处理的 sql
            if(rowsCount % commitSize != 0){
                conn.commit();
            }
        }catch (SQLException e) {
            LOGGER.error("执行更新失败");
            throw new RuntimeException(e);
        }

        return effectCount;
    }

    /**
     * 插入实体
     * @param entityClass 实体类型
     * @param fieldMap 对象信息
     * @param <T> 实体泛型
     * @return 是否插入成功
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        // 对象信息为空，插入失败
        if (fieldMap == null || fieldMap.isEmpty()) {
            LOGGER.error("不能插入实体：fieldMap为空");
            return false;
        }

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }

        // 将最后位置的逗号换成空格
        columns.replace(columns.lastIndexOf(", "), columns.length(), "");
        values.replace(values.lastIndexOf(", "), values.length(), "");

        // 将执行的sql
        String sql = String.format("INSERT INTO %s(%s) VALUES (%s)", getTableName(entityClass), columns, values);

        // 将对象信息的值转换成参数数组
        Object[] params = fieldMap.values().toArray();

        // 打印sql信息
        logSqlInfo(sql, params);

        return executeUpdate(sql, params) == 1;
    }

    /**
     * 更新实体
     * @param entityClass 实体类型
     * @param id 实体id
     * @param fieldMap 对象信息
     * @param <T> 实体泛型
     * @return 是否更新成功
     */
    public static <T> boolean updateEntity(Class<T> entityClass,
                                           long id,
                                           Map<String, Object> fieldMap) {
        // 对象信息为空，插入失败
        if (fieldMap == null || fieldMap.isEmpty()) {
            LOGGER.error("不能插入实体：fieldMap为空");
            return false;
        }

        StringBuilder columns = new StringBuilder();

        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(" = ?, ");
        }

        // 将执行的sql
        String sql = String.format("UPDATE %s SET %s WHERE id = ?",
                getTableName(entityClass),
                columns.substring(0, columns.lastIndexOf(", ")));

        List<Object> paramsList = new ArrayList<Object>(fieldMap.values());
        paramsList.add(id);

        Object[] params = paramsList.toArray();

        // 打印sql信息
        logSqlInfo(sql, params);

        return executeUpdate(sql, params) == 1;
    }

    /**
     * 删除实体
     * @param entityClass 实体类型
     * @param id 实体id
     * @param <T> 实体泛型
     * @return 是否删除成功
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = String.format("DELETE FROM %s WHERE id = ?", getTableName(entityClass));

        // 打印sql信息
        logSqlInfo(sql, id);

        return executeUpdate(sql, id) == 1;
    }

    /**
     * 打印sql信息
     * @param sql 执行的sql
     * @param values 参数值
     */
    private static void logSqlInfo(String sql, Object... values) {
        for (Object value : values) {
            String result = String.valueOf(value);

            if (value instanceof String) {
                result = String.format("'%s'", result);
            }

            sql = sql.replaceFirst("\\?", result);
        }

        LOGGER.info("当前执行的SQL为：" + sql);
    }

    /**
     * 根据实体类型获取对应的数据库表名
     */
    private static <T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName();
    }

    /**
     * 执行sql文件
     * @param filePath sql文件路径
     */
    public static void executeSqlFile(String filePath) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            isr = new InputStreamReader(is);
            reader = new BufferedReader(isr);

            String sql;
            // 执行文件中的每一条sql
            while ((sql = reader.readLine()) != null) {
                if (sql.isEmpty()) {
                    continue;
                }
                DatabaseHelper.executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("执行SQL文件失败");
            throw new RuntimeException(e);
        } finally {
            closeStream(reader);
            closeStream(isr);
            closeStream(is);
        }
    }

    /**
     * 关闭流
     * @param closeable 可关闭对象
     */
    private static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();

        if (conn != null) {
            try {
                // 关闭自动提交事务（开启事务）
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("开启事务失败");
                throw new RuntimeException(e);
            } finally {
                // 设置当前连接到本地线程变量
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();

        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("提交事务失败");
                throw new RuntimeException(e);
            } finally {
                // 移除本地线程的数据库连接
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();

        if (conn != null) {
            try {
                LOGGER.info("进行事务回滚");
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("回滚事务失败");
                throw new RuntimeException(e);
            } finally {
                // 移除本地线程的数据库连接
                CONNECTION_HOLDER.remove();
            }
        }
    }

}
