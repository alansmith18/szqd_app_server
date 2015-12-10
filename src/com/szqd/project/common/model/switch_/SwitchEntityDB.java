package com.szqd.project.common.model.switch_;

/**
 * Created by like on 10/8/15.
 */
public class SwitchEntityDB {

    public static final String ENTITY_NAME = "COMMON_SWITCH";

    private Long id;
    private String name;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
