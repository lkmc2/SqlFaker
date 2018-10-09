package com.lin;

import com.lin.custom.EnglishNameRandom;
import com.lin.datatype.DataType;
import com.lin.faker.Faker;
import com.lin.utils.DBTools;
import com.lin.value.Times;
import com.lin.value.Values;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lkmc2
 * @date 2018/10/4
 * @description 示范例子
 * @since 1.0.0
 */
public class Example {

    @Before
    public void setUp() {
        // 建立数据库连接
        DBTools.dbName("facker").connect();
//        Logger.setDebug(false); // 关闭SQL语句输出
    }

    @Test
    public void testInsertByMapSuccess() {
        // 给user表的四个字段填充5条数据
        Faker.tableName("user")
                .param("name", DataType.USERNAME)
                .param("age", DataType.AGE)
                .param("sex", DataType.SEX)
                .param("address", DataType.ADDRESS)
                .param("birthday", DataType.TIME)
                .insertCount(5)
                .execute();
    }

    @Test
    public void testInsertUseOfMethodWithString() {
        // 自定义随机数数组，从【"天津", "武汉", "北海", "云南"】中抽取一个作为地址的值
        Faker.tableName("user")
                .param("address", Values.of("天津", "武汉", "北海", "云南"))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfMethodWithInteger() {
        // 自定义随机数数组，从【1, 3, 5, 7】中抽取一个作为年龄的值
        Faker.tableName("user")
                .param("age", Values.of(1, 3, 5, 7))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfMethodWithFloat() {
        // 自定义随机数数组，从【1.1f, 3.2f, 5.8f, 7.6f】中抽取一个作为年龄的值
        Faker.tableName("user")
                .param("age", Values.of(1.1f, 3.2f, 5.8f, 7.6f))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfMethodWithDouble() {
        // 自定义随机数数组，从【1.1d, 3.2d, 5.8d, 7.6d】中抽取一个作为年龄的值
        Faker.tableName("user")
                .param("age", Values.of(1.1d, 3.2d, 5.8d, 7.6d))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfMethodWithLong() {
        // 自定义随机数数组，从【13L, 66L, 79L, 1584854856545L】中抽取一个作为年龄的值
        Faker.tableName("user")
                .param("age", Values.of(13L, 66L, 79L, 1584854856545L))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfMethodWithBoolean() {
        // 自定义随机数数组，从【true, false】中抽取一个作为isGood的值
        Faker.tableName("user")
                .param("isGood", Values.of(true, false))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfMethodWithChar() {
        // 自定义随机数数组，从【'A', 'C'】中抽取一个作为type的值
        Faker.tableName("user")
                .param("type", Values.of('A', 'C'))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertOnlyShowSql() {
        // 给user表的5个字段生成5条数据，并显示在控制台，并不在数据库中执行
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
        // 给user表的5个字段生成5条数据，并使用自定义的实现了RandomData接口的类进行生成name的数据
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
        // 使用自带的DataType类型指定name数值的生成器
        Faker.tableName("user")
                .param("name", DataType.USERNAME)
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfIntRange() {
        // 使用ofIntRange指定生成的年龄范围在[18,33]区间
        Faker.tableName("user")
                .param("age", Values.ofIntRange(18, 33))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertUseOfLongRange() {
        // 使用ofLongRange指定生成的年龄范围在[66666, 8888888888L)区间
        Faker.tableName("user")
                .param("age", Values.ofLongRange(66666, 8888888888L))
                .insertCount(5)
                .onlyShowSql();
    }

    @Test
    public void testInsertValuesOfFloatRange() {
        // 使用ofFloatRange指定生成的年龄范围在[66666, 8888888888L]区间，默认精确到小数点后2位
        Faker.tableName("user")
                .param("age", Values.ofFloatRange(22.22f, 33.33f))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertValuesOfFloatRangeWithPrecision() {
        // 使用ofFloatRange指定生成的年龄范围在[1666.66f, 8888.88f]区间，生成的浮点数精确到小数点后6位
        Faker.tableName("user")
                .param("age", Values.ofFloatRange(1666.66f, 8888.88f, 6))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertValuesOfDoubleRange() {
        // 使用ofDoubleRange指定生成的年龄范围在[11.11, 55.55]区间，默认精确到小数点后2位
        Faker.tableName("user")
                .param("age", Values.ofDoubleRange(11.11, 55.55))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertValuesOfDoubleRangeWithPrecision() {
        // 使用ofDoubleRange指定生成的年龄范围在[3333.333, 8888.88]区间，默认精确到小数点后4位
        Faker.tableName("user")
                .param("age", Values.ofDoubleRange(3333.333, 8888.88, 4))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertValuesOfTimeRange() {
        // 使用ofTimeRange指定生成的时间范围在[2016年3月12日, 2018年4月22日]区间
        Faker.tableName("user")
                .param("birthday", Values.ofTimeRange(Times.of(2016, 3, 12), Times.of(2018, 4, 22)))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertValuesOfTimeRangeToSecond() {
        // 使用ofTimeRange指定生成的时间范围在[2016年3月12日5点12分33秒, 2018年4月22日7点14分22秒]区间
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
        // 使用onlyShowSql()方法，只生成SQL并显示在控制台，但不在数据库中执行
        Faker.tableName("user")
                .param("age", DataType.AGE)
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testInsertIgnored() {
        // 使用ignored()方法，忽略SQL代码的生成和执行
        Faker.tableName("test")
                .param("email", DataType.EMAIL)
                .insertCount(10)
                .ignored();
    }

}
