package com.lin.utils;

import java.util.List;

/**
 * @author lkmc2
 * @date 2018/10/5
 * @description 字符串工具类
 */
public final class StringUtils {

    /**
     * 判断字符串是否非空
     * @param str 字符串
     * @return 字符串是否非空
     */
    public static boolean isNotEmpty(String str) {
        return (str != null) && !str.isEmpty();
    }

    /**
     * 删除StringBuilder中最后的逗号，并返回字符串
     * @param sb 字符串
     * @return 字符串
     */
    public static String deleteLastComma(StringBuilder sb) {
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }

    /**
     * 在字符串两边加上英文单引号
     * @param word 字符串
     * @return 两边加上单引号的字符串
     */
    public static String addSingleQuotesAround(String word) {
        return String.format("'%s'", word);
    }

    /**
     * 在列表中选出一个词
     * @param list 列表
     * @return 一个词
     */
    public static String chooseWordInList(List<String> list) {
        int index = RandomUtils.randomByLength(list.size());
        String line = list.get(index);

        String[] words = line.split(" ");
        index = RandomUtils.randomByLength(words.length);

        return words[index];
    }

}
