package com.lin.entity.common;

import com.lin.entity.FieldInfo;

/**
 * 通用数据库表字段信息
 * @author lkmc2
 * @since 1.0.1
 */
public class CommonFieldInfo implements FieldInfo {

    /** 字段名 **/
    private String fieldName;

    /** 注释 **/
    private String comments;

    /** 字段类型 **/
    private String dataType;


    @Override
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "CommonFieldInfo{" +
                "fieldName='" + fieldName + '\'' +
                ", comments='" + comments + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }

}
