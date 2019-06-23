package com.lin.entity.mysql;

/**
 * 数据库表字段信息
 * @author lkmc2
 * @since 1.0.1
 */
public class FieldInfo {
    /** 字段名 **/
    private String fieldName;

    /** 注释 **/
    private String comment;

    /** 字段类型 **/
    private String dataType;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "fieldName='" + fieldName + '\'' +
                ", comment='" + comment + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }

}
