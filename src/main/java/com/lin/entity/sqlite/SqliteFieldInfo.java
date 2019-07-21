package com.lin.entity.sqlite;

import com.lin.entity.FieldInfo;

/**
 * Sqlite 数据库的字段信息
 * @author lkmc2
 * @since 1.0.4
 */
public class SqliteFieldInfo implements FieldInfo {

    /** 字段名 **/
    private String name;

    /** 字段类型 **/
    private String type;

    @Override
    public String getFieldName() {
        return name;
    }

    @Override
    public String getComments() {
        return null;
    }

    @Override
    public String getDataType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SqliteFieldInfo{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
