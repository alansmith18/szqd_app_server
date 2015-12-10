package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.framework.service.SuperService;
import com.szqd.framework.util.DateUtils;
import com.szqd.project.common.model.image.ImageEntityDB;
import com.szqd.project.common.model.image.ImageEntityPOJO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by like on 11/16/15.
 */
public class ImageServiceImpl extends SuperService implements ImageService
{
    public void upsertImage(ImageEntityDB image)
    {
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        Update update = new Update();

        Long id = image.getId();
        if (id == null)
        {
            id = this.getNextSequenceForFieldKey(ImageEntityDB.ENTITY_NAME);
            image.setId(id);
        }
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(id);
        query.addCriteria(criteria);
        update.setOnInsert("_id",image.getId());
        update.set("title",image.getTitle());
        update.set("url",image.getUrl());
        update.set("targetURL",image.getTargetURL());
        update.set("createTime",image.getCreateTime());
        mongoTemplate.upsert(query,update,ImageEntityDB.ENTITY_NAME);

    }

    public List<ImageEntityPOJO> queryImageList(ImageEntityPOJO condition,Pager pager,String...fields)
    {

        Criteria criteria = Criteria.where("");
        if (condition != null)
        {
            String titleRegex = condition.getTitleRegex();
            if (titleRegex != null && !"".equals(titleRegex))
            {
                criteria = criteria.and("title").regex(titleRegex);
            }

            Boolean isQueryTodayImage = condition.getIsQueryTodayImage();
            if (isQueryTodayImage != null)
            {
                criteria = criteria.and("createTime").is(DateUtils.getTodayForSpecifiedTime(0,0,0,0).getTimeInMillis());
            }

        }
        Query query = new Query();
        query.addCriteria(criteria);
        if (fields != null)
        {
            for (String fieldVar : fields) {
                query.fields().include(fieldVar);
            }
        }
        if (pager != null) {
            query.skip(pager.getOffset());
            query.limit(pager.getCapacity());
        }
        query.with(new Sort(Sort.Direction.DESC,"createTime"));

        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        List<ImageEntityPOJO> list = mongoTemplate.find(query,ImageEntityPOJO.class,ImageEntityPOJO.ENTITY_NAME);
        return list;
    }


    public ImageEntityPOJO queryImageByID(Long id)
    {
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(id);
        query.addCriteria(criteria);

        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        return mongoTemplate.findOne(query,ImageEntityPOJO.class,ImageEntityPOJO.ENTITY_NAME);
    }




}
