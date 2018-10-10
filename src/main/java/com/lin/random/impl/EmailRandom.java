package com.lin.random.impl;

import com.lin.random.RandomData;
import com.lin.utils.RandomUtils;

/**
 * 邮箱随机生成器
 * @author lkmc2
 * @since 1.0.0
 */
public class EmailRandom implements RandomData<String> {

    @Override
    public String next() {
        // 邮箱前缀
        String prefix = createEmailPrefix();

        // 邮箱后缀
        String suffix = createEmailSuffix();

        return String.format("%s@%s.com", prefix, suffix);
    }

    /**
     * 创建邮箱前缀
     * @return 邮箱前缀
     */
    private String createEmailPrefix() {
        // 选择一个英文名，保证邮箱名有规律
        String englishName = RandomUtils.selectOneInArray(englishNames);

        StringBuilder prefixSB = new StringBuilder(englishName);

        // 英文名后的数字长度
        int prefixLength = RandomUtils.nextIntRange(2, 6);

        // 生成英文名后的数字
        for (int i = 0; i < prefixLength; i++) {
            String num = RandomUtils.selectOneInArray(chars);
            prefixSB.append(num);
        }

        return prefixSB.toString();
    }

    /**
     * 创建邮箱后缀
     * @return 邮箱后缀
     */
    private String createEmailSuffix() {
        return RandomUtils.selectOneInArray(emailSuffix);
    }

    // 英文名
    private static final String[] englishNames = {
            "Jacob", "Michael", "Ethan", "Joshua", "Alexander", "Anthony", "William", "Christopher",
            "Jayden", "Andrew", "Joseph", "David", "Noad", "Aiden", "James", "Ryan", "Logan", "John",
            "Nathan", "Elijah", "Christian", "Gabriel", "Benjamin", "Jonathan", "Tyler", "Samuel",
            "Nicholas", "Gavin", "Dylan", "Jackson", "Brandon", "Caleb", "Jackson", "Brandon", "Caleb",
            "Mason", "Angel", "Isaac", "Evan", "Jack", "Kevin", "Jose", "Isaiah", "Luke", "Landon",
            "Justin", "Lucas", "Zachary", "Jordan", "Robert", "Aaron", "Brayden", "Thomas", "Cameron",
            "Hunter", "Austin", "Adrian", "Connor", "Owen", "Aidan", "Jason", "Julian", "Wyatt", "Charles",
            "Luis", "Carter", "Juan", "Chase", "Diego", "Jeremiah", "Brody", "Zavier", "Adam", "Carlos",
            "Liam", "Hayden", "Jesus", "Ian", "Tristan", "Bryan", "Sean", "Cole", "Alex", "Eric", "Brian",
            "Jaden", "Carson", "Blake", "Ayden", "Coope", "Dominic", "Brady", "Caden", "Josiah", "Kyle",
            "Colton", "Kaden", "Eli"
    };

    // 数字字符串数组
    private static final String[] chars = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
    };

    // 邮箱后缀数组
    private static final String[] emailSuffix = {
            "126", "163", "gmail", "qq", "188", "hotmail", "yahoo", "sina",
            "sohu", "msn", "live", "tom", "sogou"
    };

}
