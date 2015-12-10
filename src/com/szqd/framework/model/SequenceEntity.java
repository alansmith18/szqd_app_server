package com.szqd.framework.model;


import org.bson.types.ObjectId;


/**
 * Created by like on 4/15/15.
 */

public class SequenceEntity {

    public static final String ENTITY_NAME = "SequenceEntity";


    private String id;
    private long seq;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
}
