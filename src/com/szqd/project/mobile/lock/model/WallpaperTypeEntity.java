package com.szqd.project.mobile.lock.model;

/**
 * Created by like on 4/14/15.
 */
public class WallpaperTypeEntity
{
    public static final String ENTITY_NAME = "WallpaperTypeEntity";

    private Long id;
    private String typeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
