package com.lin.entity.mysql;

/**
 * 路径信息
 * @author lkmc2
 */
public class PathInfo {
    private String packageName; // 包名
    private String currentFilePath; // 当前文件路径

    public PathInfo() {
    }

    public PathInfo(String packageName, String currentFilePath) {
        this.packageName = packageName;
        this.currentFilePath = currentFilePath;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }
}
