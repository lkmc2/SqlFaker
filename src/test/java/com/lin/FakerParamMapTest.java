package com.lin;

import com.lin.custom.EnglishNameRandom;
import com.lin.datatype.DataType;
import com.lin.utils.DBTools;
import com.lin.value.Times;
import com.lin.value.Values;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lkmc2
 * @date 2018/10/4
 * @description 数据伪造器测试
 */
public class FakerParamMapTest {

    @Before
    public void setUp() {
        DBTools.dbName("facker").connect();
//        Logger.setDebug(false); // 关闭SQL语句输出
    }

    @Test(expected = RuntimeException.class)
    public void testInsertNoTableName() {
        Faker.tableName("")
                .param("name", DataType.USERNAME)
                .param("age", DataType.AGE)
                .param("sex", DataType.SEX)
                .param("birthday", DataType.TIME)
                .insertCount(5)
                .execute();
    }

    @Test(expected = RuntimeException.class)
    public void testInsertNotSetInsertCount() {
        Faker.tableName("123")
                .param("name", DataType.USERNAME)
                .param("age", DataType.AGE)
                .param("sex", DataType.SEX)
                .param("birthday", DataType.TIME)
                .execute();
    }

    @Test(expected = RuntimeException.class)
    public void testInsertNegativeInsertCount() {
        Faker.tableName("456")
                .param("name", DataType.USERNAME)
                .param("age", DataType.AGE)
                .param("sex", DataType.SEX)
                .param("birthday", DataType.TIME)
                .insertCount(-3)
                .execute();
    }

    @Test(expected = RuntimeException.class)
    public void testInsertNoParam() {
        Faker.tableName("789")
                .insertCount(5)
                .execute();
    }

    @Test(expected = RuntimeException.class)
    public void testInsertNewErrorType() {
        Faker.tableName("user")
                .param("name", new Object())
                .insertCount(5)
                .execute();
    }

    @Test(expected = RuntimeException.class)
    public void testInsertErrorClassType() {
        Faker.tableName("user")
                .param("name", Object.class)
                .insertCount(5)
                .execute();
    }

    @Test
    public void testInsertUseOfMethodWithString() {
        Faker.tableName("user")
                .param("address", Values.of("天津", "武汉", "北海", "云南"))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfMethodWithInteger() {
        Faker.tableName("user")
                .param("address", Values.of(1, 3, 5, 7))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfMethodWithFloat() {
        Faker.tableName("user")
                .param("age", Values.of(1.1f, 3.2f, 5.8f, 7.6f))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfMethodWithDouble() {
        Faker.tableName("user")
                .param("age", Values.of(1.1d, 3.2d, 5.8d, 7.6d))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfMethodWithLong() {
        Faker.tableName("user")
                .param("age", Values.of(13L, 66L, 79L, 1584854856545L))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfMethodWithBoolean() {
        Faker.tableName("user")
                .param("age", Values.of(true, false))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfMethodWithChar() {
        Faker.tableName("user")
                .param("age", Values.of('A', 'C'))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertOnlyShowSql() {
        Faker.tableName("user")
                .param("name", DataType.USERNAME)
                .param("age", DataType.AGE)
                .param("sex", DataType.SEX)
                .param("address", DataType.ADDRESS)
                .param("birthday", DataType.TIME)
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseCustomRandom() {
        Faker.tableName("user")
                .param("name", EnglishNameRandom.class)
                .param("age", DataType.AGE)
                .param("sex", DataType.SEX)
                .param("address", DataType.ADDRESS)
                .param("birthday", DataType.TIME)
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUserNameRandom() {
        Faker.tableName("user")
                .param("name", DataType.USERNAME)
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfIntRange() {
        Faker.tableName("user")
                .param("age", Values.ofIntRange(18, 33))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfLongRange() {
        Faker.tableName("user")
                .param("age", Values.ofLongRange(66666, 8888888888L))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertValuesOfFloatRange() {
        Faker.tableName("user")
                .param("name", Values.ofFloatRange(22.22f, 33.33f))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertValuesOfFloatRangeWithPrecision() {
        Faker.tableName("user")
                .param("name", Values.ofFloatRange(1666.66f, 8888.88f, 6))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertValuesOfDoubleRange() {
        Faker.tableName("user")
                .param("name", Values.ofDoubleRange(11.11, 55.55))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertValuesOfDoubleRangeWithPrecision() {
        Faker.tableName("user")
                .param("name", Values.ofDoubleRange(3333.333, 8888.88, 4))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertValuesOfTimeRange() {
        Faker.tableName("user")
                .param("birthday", Values.ofTimeRange(Times.of(2016, 3, 12), Times.of(2018, 4, 22)))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertValuesOfTimeRangeToSecond() {
        Faker.tableName("user")
                .param("birthday",
                        Values.ofTimeRange(
                            Times.of(2016, 3, 12, 5, 12, 33),
                            Times.of(2018, 4, 22, 7, 14, 22)
                        )
                )
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertAgeRandom() {
        Faker.tableName("user")
                .param("age", DataType.AGE)
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertTimeRandom() {
        Faker.tableName("user")
                .param("birthday", DataType.TIME)
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertPhoneRandom() {
        Faker.tableName("user")
                .param("phone", DataType.PHONE)
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertIdRandom() {
        Faker.tableName("test")
                .param("id", DataType.ID)
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertEmailRandom() {
        Faker.tableName("test")
                .param("email", DataType.EMAIL)
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertIgnored() {
        Faker.tableName("test")
                .param("email", DataType.EMAIL)
                .insertCount(10)
                .ignored();
    }

    @Test
    public void testInsertByMapSuccess() {
        Faker.tableName("user")
                .param("name", DataType.USERNAME)
                .param("age", DataType.AGE)
                .param("sex", DataType.SEX)
                .param("address", DataType.ADDRESS)
                .param("birthday", DataType.TIME)
                .insertCount(5)
                .execute();
    }

}
