package com.lin.generator;

import com.lin.faker.Faker;
import com.lin.random.RandomData;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 顺序值生成器测试
 * @author lkmc2
 */
public class GeneratorTest {

    @Test
    public void testOf() {
        RandomData randomData = Generator.of("jack", "andy", "wang").repeatCount(3);
        RandomData randomData2 =  Generator.of("1", "2", "3");

        for (int i = 0; i < 20; i++) {
            System.out.println(randomData.next());
            System.out.println(randomData2.next());
            System.out.println("-----------------");
        }

    }

    @Test
    public void testOf2() {
        RandomData randomData = Generator.of(222, 333, 444).repeatCount(3);
        RandomData randomData2 =  Generator.of("1", "2", "3");

        for (int i = 0; i < 20; i++) {
            System.out.println(randomData.next());
            System.out.println(randomData2.next());
            System.out.println("-----------------");
        }

    }

    @Test
    public void testOf3() {
        Faker.tableName("user")
                .param("deptNo", Generator.of("1001", "1002", "1003"))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testOf4() {
        Faker.tableName("user")
                .param("name", Generator.of("jack", "andy", "wang").repeatCount(3))
                .param("deptNo", Generator.of("1001", "1002", "1003"))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testOf5() {
        Faker.tableName("user")
                .param("id", Generator.ofLongStart(10000L))
                .param("name", Generator.of("jack", "andy", "wang").repeatCount(3))
                .param("deptNo", Generator.of("1001", "1002", "1003"))
                .param("serialNum", Generator.ofIntStart(500))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testOf6() {
        String[] strings = {"jack", "andy", "wang"};

        Faker.tableName("user")
                .param("id", Generator.ofLongStart(10000L))
                .param("name", Generator.of(strings).repeatCount(3))
                .param("deptNo", Generator.of("1001", "1002", "1003"))
                .param("serialNum", Generator.ofIntStart(500))
                .insertCount(10)
                .onlyShowSql();
    }

    @Test
    public void testOfInteger() {
        RandomData randomData = Generator.ofIntStart(500);
        for (int i = 0; i < 20; i++) {
            System.out.println(randomData.next());
        }
    }

    @Test
    public void testOfLong() {
        RandomData randomData = Generator.ofLongStart(50000000000000L);
        for (int i = 0; i < 20; i++) {
            System.out.println(randomData.next());
        }
    }

    @Test
    public void testOfList() {
        List<String> strings = Arrays.asList("jack", "andy", "wang");

        RandomData randomData = Generator.ofList(strings);
        for (int i = 0; i < 20; i++) {
            System.out.println(randomData.next());
        }
    }

    @Test
    public void testOfList2() {
        List<String> strings = Arrays.asList("jack", "andy", "wang");

        RandomData randomData = Generator.ofList(strings).repeatCount(3);
        for (int i = 0; i < 20; i++) {
            System.out.println(randomData.next());
        }
    }

    @Test
    public void testOfList3() {
        List<Integer> strings = Arrays.asList(1111, 2222, 3333);

        RandomData randomData = Generator.ofList(strings);
        for (int i = 0; i < 20; i++) {
            System.out.println(randomData.next());
        }
    }

    @Test
    public void testOfList4() {
        List<Integer> strings = Arrays.asList(1111, 2222, 3333);

        RandomData randomData = Generator.ofList(strings).repeatCount(3);
        for (int i = 0; i < 20; i++) {
            System.out.println(randomData.next());
        }
    }

}