package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.framework.service.SuperService;
import com.szqd.project.common.model.MusicEntityDB;
import com.szqd.project.common.model.MusicEntityPOJO;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Calendar;
import java.util.List;

/**
 * Created by like on 5/26/15.
 */
public class MusicServiceImpl extends SuperService implements MusicService
{
    private static final Logger LOGGER = Logger.getLogger(MusicServiceImpl.class);

    /**
     * 音乐列表
     * @param music
     * @param pager
     * @return
     */
    public List<MusicEntityPOJO> listMusic(MusicEntityDB music, Pager pager)
    {
        List<MusicEntityPOJO> list = null;
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            Criteria criteria = new Criteria();
            query.addCriteria(criteria);
            String name = music.getName();
            if (name != null)
            {
                criteria.and("name").regex(name);
            }
            query.skip(pager.getOffset());
            query.limit(pager.getCapacity());

            list = mongoTemplate.find(query,MusicEntityPOJO.class, MusicEntityDB.ENTITY_NAME);
        }
        catch (Exception e)
        {
            LOGGER.error("com.szqd.project.common.service.MusicServiceImpl.listMusic(MusicEntityDB music, Pager pager)",e);
            throw new RuntimeException("音乐列表");

        }

        return list;
    }
    /**
     * 根据id返回
     * @param musicID
     * @return
     */
    public MusicEntityPOJO findMusicByID(Integer musicID)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(musicID));
            MusicEntityPOJO music = mongoTemplate.findOne(query,MusicEntityPOJO.class,MusicEntityPOJO.ENTITY_NAME);
            return music;
        }
        catch (Exception e)
        {
            LOGGER.error("com.szqd.project.common.service.MusicServiceImpl.findMusicByID(Integer musicID),#musicID = "+musicID);
            throw new RuntimeException("根据id返回出错");
        }
    }

    /**
     * 保存音乐
     * @param music
     */
    public void saveOrUpdateMusic(MusicEntityDB music)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Long id = music.getId();
            Query query = new Query();
            Update update = new Update();
            if(id == null)
            {
                id = this.getNextSequenceForFieldKey(MusicEntityDB.ENTITY_NAME);
                update.setOnInsert("id", id);
                update.set("createTime", Calendar.getInstance().getTimeInMillis());
            }
            else
            {
                update.set("modifyTime",Calendar.getInstance().getTimeInMillis());
            }
            Criteria criteria = new Criteria();
            criteria.and("id").is(id);
            query.addCriteria(criteria);

            update.set("name",music.getName());
            update.set("status",music.getStatus());
            update.set("platform",music.getPlatform());
            update.set("projects",music.getProjects());
            update.set("musicFilePath",music.getMusicFilePath());

            mongoTemplate.upsert(query,update,MusicEntityDB.class,MusicEntityDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.MusicServiceImpl.saveOrUpdateMusic(MusicEntityDB music)",e);
            throw new RuntimeException("保存音乐出错");
        }
    }
}
