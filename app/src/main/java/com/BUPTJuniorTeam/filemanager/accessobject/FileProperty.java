package com.BUPTJuniorTeam.filemanager.accessobject;

public class FileProperty {
    public FileProperty() {
    }

    private String name;
    private String modified_time;
    private long size; //单位是字节
    private String path;
    private String type;


    public void setName(String name) {
        this.name = name;
    }

    public void setModified_time(String modified_time) {
        this.modified_time = modified_time;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModified_time() {
        return modified_time;
    }

    public long getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
