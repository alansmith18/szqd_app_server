package com.szqd.project.mobile.lock.model;

/**
 * Created by like on 5/5/15.
 */
public class ThemeDiyControlTypeEntity {

    private static final String ENTITY_NAME = "MOBILE_LOCK_THEME_DIY_CONTROL_TYPE";

    private Integer id;

    private String typeName;
    
    private Integer parentDiyID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getParentDiyID() {
        return parentDiyID;
    }

    public void setParentDiyID(Integer parentDiyID) {
        this.parentDiyID = parentDiyID;
    }
}
