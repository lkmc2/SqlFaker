package com.lin.entity;

/**
 * 数据表信息
 * @author lkmc2
 * @since 1.0.4
 */
public interface TableInfo {

    /**
     * 获取表名
     * @return 表名
     */
    String getTableName();

    /**
     * 获取表注释
     * @return 表注释
     */
    String getTableComment();

}
