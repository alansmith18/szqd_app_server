package com.szqd.framework.persistence;

import org.springframework.data.mongodb.core.MongoTemplate;


/**
 * Created by like on 3/31/15.
 */
public class MongoDao
{

    private MongoTemplate mongoTemplate = null;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
