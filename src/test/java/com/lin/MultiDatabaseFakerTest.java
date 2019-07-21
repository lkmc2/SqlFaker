package com.lin;

import com.lin.datatype.DataType;
import com.lin.faker.Faker;
import com.lin.utils.DBTools;
import com.lin.value.Times;
import com.lin.value.Values;
import org.junit.Test;

/**
 * 多数据库连接测试
 * @author lkmc2
 */
public class MultiDatabaseFakerTest {

    @Test
    public void testMySQL() {
        // 创建 MySQL 数据库连接
        DBTools.url("jdbc:mysql://localhost:3306/facker?characterEncoding=UTF-8")
//                .driverClassName("com.mysql.jdbc.Driver")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("root")
                .password("123456")
                .connect();

        // 给 user_table 表的 3 个字段填充 5 条数据
        Faker.tableName("user_table")
                .param("id", DataType.ID)
                .param("name", DataType.USERNAME)
                .param("age", DataType.AGE)
                .insertCount(5)
                .execute();

        // 给 product 表的 9 个字段填充 5 条数据
        Faker.tableName("product")
                .param("id", DataType.ID)
                .param("name", DataType.USERNAME)
                .param("price", Values.ofIntRange(10, 100))
                .param("tenant_id  ", DataType.ID)
                .param("created_by", DataType.USERNAME)
                .param("updated_by", DataType.USERNAME)
                .param("created_at", Values.ofTimeRange(
                        Times.of(2018, 7, 12),
                        Times.of(2019, 9, 22, 18, 22, 33)
                ))
                .param("updated_at", DataType.TIME)
                .param("dr", Values.of("0", "1"))
                .insertCount(5)
                .execute();
    }

    @Test
    public void tesSqlServer() {
        // 创建 SQL Server 数据库连接
        DBTools.url("jdbc:sqlserver://127.0.0.1:1433;DataBaseName=facker")
                .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                .username("sa")
                .password("123456")
                .connect();

        // 给 user_table 表的 3 个字段填充 5 条数据
        Faker.tableName("user_table")
                .param("id", DataType.ID)
                .param("name", DataType.USERNAME)
                .param("age", DataType.AGE)
                .insertCount(5)
                .execute();

        // 给 product 表的 9 个字段填充 5 条数据
        Faker.tableName("product")
                .param("id", DataType.ID)
                .param("name", DataType.USERNAME)
                .param("price", Values.ofIntRange(10, 100))
                .param("tenant_id  ", DataType.ID)
                .param("created_by", DataType.USERNAME)
                .param("updated_by", DataType.USERNAME)
                .param("created_at", Values.ofTimeRange(
                        Times.of(2018, 7, 12),
                        Times.of(2019, 9, 22, 18, 22, 33)
                ))
                .param("updated_at", DataType.TIME)
                .param("dr", Values.of("0", "1"))
                .insertCount(5)
                .execute();
    }

    @Test
    public void testOracle() {
        // 创建 Oracle 数据库连接
        DBTools.url("jdbc:oracle:thin:@localhost:1521/orcl")
                .driverClassName("oracle.jdbc.driver.OracleDriver")
                .username("test1")
                .password("123456")
                .connect();

        // 给 user_table 表的 3 个字段填充 5 条数据
        Faker.tableName("user_table")
                .param("id", DataType.ID)
                .param("name", DataType.USERNAME)
                .param("age", DataType.AGE)
                .insertCount(5)
                .execute();

        // 给 product 表的 9 个字段填充 5 条数据
        Faker.tableName("product")
                .param("id", DataType.ID)
                .param("name", DataType.USERNAME)
                .param("price", Values.ofIntRange(10, 100))
                .param("tenant_id  ", DataType.ID)
                .param("created_by", DataType.USERNAME)
                .param("updated_by", DataType.USERNAME)
                .param("created_at", Values.ofTimeRange(
                        Times.of(2018, 7, 12),
                        Times.of(2019, 9, 22, 18, 22, 33)
                ))
                .param("updated_at", DataType.TIME)
                .param("dr", Values.of("0", "1"))
                .insertCount(5)
                .execute();
    }

    @Test
    public void testSqlite() {
        // 创建 Sqlite 数据库连接
        DBTools.url("jdbc:sqlite:F:/SqliteWorkplace/my_sqlite.db")
                .driverClassName("org.sqlite.JDBC")
                .username("")
                .password("")
                .connect();

        // 给 user_table 表的 3 个字段填充 5 条数据
        Faker.tableName("user_table")
                .param("id", DataType.ID)
                .param("name", DataType.USERNAME)
                .param("age", DataType.AGE)
                .insertCount(5)
                .execute();

        // 给 product 表的 9 个字段填充 5 条数据
        Faker.tableName("product")
                .param("id", DataType.ID)
                .param("name", DataType.USERNAME)
                .param("price", Values.ofIntRange(10, 100))
                .param("tenant_id  ", DataType.ID)
                .param("created_by", DataType.USERNAME)
                .param("updated_by", DataType.USERNAME)
                .param("created_at", Values.ofTimeRange(
                        Times.of(2018, 7, 12),
                        Times.of(2019, 9, 22, 18, 22, 33)
                ))
                .param("updated_at", DataType.TIME)
                .param("dr", Values.of("0", "1"))
                .insertCount(5)
                .execute();
    }

}
