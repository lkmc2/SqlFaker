package com.lin.entity;

/**
 * 字段信息
 * @author lkmc2
 * @since 1.0.4
 */
public interface FieldInfo {

    /**
     * 获取字段名
     * @return 字段名
     */
    String getFieldName();

    /**
     * 获取字段注释
     * @return 字段注释
     */
    String getComments();

    /**
     * 获取字段类型
     * @return 字段类型
     */
    String getDataType();

}
