package com.lin.entity.mysql;

/**
 * 数据库表字段信息
 * @author lkmc2
 * @since 1.0.1
 */
public class FieldInfo {
    private String Field; // 字段名
    private String Type; // 字段类型
    private String Null; // 是否可以为空
    private String Key; // 是否是主键
    private String Default; // 默认值
    private String Extra; // 其他数据（auto_increment等）

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getNull() {
        return Null;
    }

    public void setNull(String aNull) {
        Null = aNull;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getDefault() {
        return Default;
    }

    public void setDefault(String aDefault) {
        Default = aDefault;
    }

    public String getExtra() {
        return Extra;
    }

    public void setExtra(String extra) {
        Extra = extra;
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "Field='" + Field + '\'' +
                ", Type='" + Type + '\'' +
                ", Key='" + Key + '\'' +
                '}';
    }
}
