package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.framework.service.SuperService;
import com.szqd.project.common.model.SkinEntityDB;
import com.szqd.project.common.model.SkinEntityPojo;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by like on 5/18/15.
 */
public class SkinServiceImpl extends SuperService implements SkinService {

    private static final Logger LOGGER = Logger.getLogger(SkinServiceImpl.class);

    /**
     * 根据分页罗列皮肤列表
     * @param skin
     * @param pager
     * @return
     */
    public List<SkinEntityPojo> listSkin(SkinEntityDB skin, Pager pager)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            Criteria criteria = Criteria.where("");

            String name = skin.getName();
            if (name != null) {
                criteria = criteria.and("name").regex(name);
            }

            List<Integer> projects = skin.getProjects();
            if (projects != null)
            {
                criteria.and("projects").in(projects);
            }

            query.addCriteria(criteria);
            if (pager != null) {
                query.skip(pager.getOffset());
                query.limit(pager.getCapacity());
            }
            List<SkinEntityPojo> list = mongoTemplate.find(query,SkinEntityPojo.class,SkinEntityDB.ENTITY_NAME);
            return list;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.SkinServiceImpl.listSkin(SkinEntityDB skin, Pager pager)",e);
            throw new RuntimeException("根据分页罗列皮肤列表出错");
        }
    }


    /**
     * 根据id查询皮肤
     * @param id
     * @return
     */
    public SkinEntityDB querySkinByID(Integer id)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = Query.query(Criteria.where("id").is(id));
            SkinEntityDB skin = mongoTemplate.findOne(query,SkinEntityDB.class,SkinEntityDB.ENTITY_NAME);
            return skin;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.SkinServiceImpl.querySkinByID(Integer id)",e);
            throw new RuntimeException("根据id查询皮肤出错");
        }
    }


    /**
     * 保存或更新皮肤
     * @param edit
     */
    public void saveOrUpdateSkin(SkinEntityDB edit)
    {
        try
        {
            Update update = new Update();
            Long id = edit.getId();
            if (id == null)
            {
                id = this.getNextSequenceForFieldKey(SkinEntityDB.ENTITY_NAME);
                update.set("id",id);
                update.set("createTime", Calendar.getInstance().getTimeInMillis());
            }

            update.set("name",edit.getName());
            update.set("desc",edit.getDesc());
            update.set("platforms",edit.getPlatforms());
            update.set("projects",edit.getProjects());
            update.set("status",edit.getStatus());
            update.set("number",edit.getNumber());
            update.set("fontColor",edit.getFontColor());
            update.set("brightnessColor",edit.getBrightnessColor());
            update.set("isRecommend",edit.getIsRecommend());
            update.set("pic",edit.getPic());
            update.set("skinPackagePath",edit.getSkinPackagePath());
            update.set("modifyTime", Calendar.getInstance().getTimeInMillis());

            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = Query.query(Criteria.where("id").is(id));
            mongoTemplate.upsert(query,update,SkinEntityDB.class,SkinEntityDB.ENTITY_NAME);


        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.SkinServiceImpl.saveOrUpdateSkin(SkinEntityDB edit)",e);
            throw new RuntimeException("保存或更新皮肤出错");
        }

    }

}
