package com.lin.mapping;

import com.lin.datatype.DataType;
import com.lin.random.RandomData;
import com.lin.random.impl.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lkmc2
 * @date 2018/10/7
 * @description 数据类型映射关系
 */
public final class DataTypeMapping {

    // 数据类型映射关系Map
    private static final Map<DataType, Class<? extends RandomData>> DATA_TYPE_MAP;

    static {
        // 初始化数据类型与随机生成器的映射关系
        DATA_TYPE_MAP = new LinkedHashMap<DataType, Class<? extends RandomData>>() {{
            put(DataType.ID, IdRandom.class);
            put(DataType.USERNAME, UserNameRandom.class);
            put(DataType.PHONE, PhoneRandom.class);
            put(DataType.TIME, TimeRandom.class);
            put(DataType.ADDRESS, AddressRandom.class);
            put(DataType.AGE, AgeRandom.class);
            put(DataType.SEX, SexRandom.class);
            put(DataType.EMAIL, EmailRandom.class);
        }};
    }

    /**
     * 获取枚举对应的随机生成器类型
     * @return 枚举对应的随机生成器类型
     */
    public static Class<? extends RandomData> getMapping(DataType dataType) {
        return DATA_TYPE_MAP.get(dataType);
    }

}
