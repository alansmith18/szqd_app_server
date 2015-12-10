package com.szqd.project.common.model;

/**
 * Created by like on 5/11/15.
 */
public class ProjectEntityDB
{
    public static final String ENTITY_NAME = "COMMON_PROJECT";

    private Long id;
    private String name;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
