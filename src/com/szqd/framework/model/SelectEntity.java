package com.szqd.framework.model;

/**
 * Created by like on 4/20/15.
 */
public class SelectEntity {
    private String text;
    private String value;

    public SelectEntity() {
    }

    public SelectEntity(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
