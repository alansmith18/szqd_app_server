package com.szqd.project.tracking_statistics.model;

/**
 * Created by like on 11/18/15.
 */
public class TrackingEventDB
{
    public static final String  ENTITY_NAME = "TRACKING_STATISTICS_TRACKING_EVENT";

    private Long id;
    private String name;
    private String forwardURL;
    private Long projectID;


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

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }

    public String getForwardURL() {
        return forwardURL;
    }

    public void setForwardURL(String forwardURL) {
        this.forwardURL = forwardURL;
    }
}
