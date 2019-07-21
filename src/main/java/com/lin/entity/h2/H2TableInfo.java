package com.lin.entity.h2;

import com.lin.entity.TableInfo;

/**
 * H2 数据库的表信息
 * @author lkmc2
 * @since 1.0.4
 */
public class H2TableInfo implements TableInfo {

    /** 表名 **/
    private String tableName;

    public void setTABLE_NAME(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getTableComment() {
        return null;
    }

}
