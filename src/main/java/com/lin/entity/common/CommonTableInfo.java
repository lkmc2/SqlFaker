package com.lin.entity.common;

import com.lin.entity.TableInfo;

/**
 * 通用数据表信息
 * @author lkmc2
 * @since 1.0.1
 */
public class CommonTableInfo implements TableInfo {

    /** 表名 **/
    private String tableName;

    /** 表注释 **/
    private String tableComment;

    @Override
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    @Override
    public String toString() {
        return "CommonTableInfo{" +
                "tableName='" + tableName + '\'' +
                ", tableComment='" + tableComment + '\'' +
                '}';
    }
}
