package com.szqd.framework.model;

/**
 * Created by like on 4/16/15.
 */
public class FileEntity {

    private Resolution resolution;

    /**
     * 文件的绝对路径
     */
    private String realPath;

    /**
     * 文件的绝对路径的目录
     */
    private String realFolderPath;

    /**
     * 文件的相对路径
     */
    private String relativePath;

    /**
     * 文件的相对路径的目录
     */
    private String relativeFolderPath;

    /**
     * 文件类型
     */
    private String fileType;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public String getRealFolderPath() {
        return realFolderPath;
    }

    public void setRealFolderPath(String realFolderPath) {
        this.realFolderPath = realFolderPath;
    }

    public String getRelativeFolderPath() {
        return relativeFolderPath;
    }

    public void setRelativeFolderPath(String relativeFolderPath) {
        this.relativeFolderPath = relativeFolderPath;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}
