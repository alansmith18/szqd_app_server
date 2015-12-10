package com.szqd.project.common.service;

import com.szqd.framework.service.SuperService;
import com.szqd.project.common.model.switch_.SwitchEntityDB;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by like on 10/8/15.
 */
public class SwitchServiceImpl extends SuperService implements SwitchService
{

    /**
     * 罗列开关
     * @param switchDB
     * @return
     */
    public List<SwitchEntityDB> listSwitch(SwitchEntityDB switchDB)
    {
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        Query query = new Query();
        Criteria criteria = Criteria.where("");
        String name = switchDB.getName();
        if (name != null && !"".equals(name))
        {
            criteria = criteria.and("name").regex(name);
        }
        query.addCriteria(criteria);
        return mongoTemplate.find(query,SwitchEntityDB.class,SwitchEntityDB.ENTITY_NAME);
    }

    public void createOrReplaceSwitch(SwitchEntityDB switchEntityDB)
    {
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        Long id = switchEntityDB.getId();
        if (id == null)
        {
            id = this.getNextSequenceForFieldKey(SwitchEntityDB.ENTITY_NAME);
        }

        Update update = new Update();
        update.setOnInsert("_id",id);
        String name = switchEntityDB.getName();
        if (name != null) {
            update.set("name", name);
        }
        update.set("status",switchEntityDB.getStatus());
        mongoTemplate.upsert(new Query(Criteria.where("id").is(id)),update,SwitchEntityDB.ENTITY_NAME);
    }

    public SwitchEntityDB getSwitchByID(Long id)
    {
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        SwitchEntityDB switchEntityDB = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),SwitchEntityDB.class,SwitchEntityDB.ENTITY_NAME);
        return switchEntityDB;
    }



}
