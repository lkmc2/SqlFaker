package com.lin.generator;

import com.lin.random.RandomData;
import org.junit.Test;

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
    public void testOfInteger() {
        RandomData randomData = Generator.ofInt(500);
        for (int i = 0; i < 20; i++) {
            System.out.println(randomData.next());
        }
    }

    @Test
    public void testOfLong() {
        RandomData randomData = Generator.ofLong(50000000000000L);
        for (int i = 0; i < 20; i++) {
            System.out.println(randomData.next());
        }
    }

}