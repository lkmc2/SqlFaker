package com.lin.entity.h2;

import com.lin.entity.FieldInfo;

/**
 * H2 数据库的字段信息
 * @author lkmc2
 * @since 1.0.4
 */
public class H2FieldInfo implements FieldInfo {

    /** 字段名 **/
    private String field;

    /** 字段类型 **/
    private String type;

    @Override
    public String getFieldName() {
        return field;
    }

    @Override
    public String getComments() {
        return null;
    }

    @Override
    public String getDataType() {
        return type;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SqliteFieldInfo{" +
                "field='" + field + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
