package com.szqd.project.common.service;

import com.szqd.framework.service.SuperService;
import com.szqd.project.common.model.RecommendCategoryEntityDB;
import com.szqd.project.common.model.RecommendEntityDB;
import com.szqd.project.common.model.RecommendEntityPojo;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by like on 5/11/15.
 */
public class RecommendServiceImpl extends SuperService implements RecommendService
{
    private static final Logger LOGGER = Logger.getLogger(RecommendServiceImpl.class);


    /**
     * 更新分类的排序
     * @param recommendIDList
     * @param orderbyList
     */
    public void updateCategoryOrderby(String orderFieldName,List<String> recommendIDList, List<String> orderbyList)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            for (int i = 0; i < recommendIDList.size(); i++)
            {
                Integer id = Integer.valueOf(recommendIDList.get(i));
                Integer order = Integer.valueOf(orderbyList.get(i));

                Query query = Query.query(Criteria.where("id").is(id));
                Update update = new Update();
                update.set(orderFieldName,order);
                mongoTemplate.findAndModify(query,update,RecommendEntityDB.class,RecommendEntityDB.ENTITY_NAME);
            }

        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.RecommendServiceImpl.updateCategoryOrderby(List<Integer> recommendIDList, List<String> orderbyList)",e);
            throw new RuntimeException("更新分类的排序出错");
        }
    }

    /**
     * 通过分类查询精品推荐
     * @param category
     * @return
     */
    public List<HashMap> queryRecommendByCategory(RecommendCategoryEntityDB category)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = Query.query(Criteria.where("projects").in(category.getProject()));
            List<HashMap> recommendList = mongoTemplate.find(query,HashMap.class,RecommendEntityDB.ENTITY_NAME);
            return recommendList;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.RecommendServiceImpl.queryProjectByCategory(RecommendCategoryEntityDB category)",e);
            throw new RuntimeException("通过分类查询项目出错");
        }
    }

    /**
     * 保存或者修改精品分类
     */
    public void saveOrUpdateRecommendCategory(RecommendCategoryEntityDB category)
    {
        try
        {

            Update update = new Update();
            Long id = category.getId();
            if(id == null)
            {
                id = this.getNextSequenceForFieldKey(RecommendCategoryEntityDB.ENTITY_NAME);
                update.set("id",id);
            }
            update.set("name",category.getName());
            update.set("desc",category.getDesc());
            update.set("platform",category.getPlatform());
            update.set("project",category.getProject());

            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = Query.query(Criteria.where("id").is(id));

            mongoTemplate.upsert(query,update,RecommendCategoryEntityDB.class,RecommendCategoryEntityDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.RecommendServiceImpl.saveOrUpdateRecommendCategory()",e);
            throw new RuntimeException("保存或者修改精品分类出错");
        }
    }

    /**
     * 罗列精品推荐分类
     * @return
     */
    public List<RecommendCategoryEntityDB> listRecommendCategory()
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            List<RecommendCategoryEntityDB> categoryList = mongoTemplate.findAll(RecommendCategoryEntityDB.class, RecommendCategoryEntityDB.ENTITY_NAME);
            return categoryList;

        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.RecommendServiceImpl.listRecommendCategory()",e);
            throw new RuntimeException("罗列精品推荐分类出错");
        }
    }


    /**
     * 通过id获取精品分类
     * @param id
     * @return
     */
    public RecommendCategoryEntityDB getCategoryByID(Long id)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = Query.query(Criteria.where("id").is(id));
            RecommendCategoryEntityDB category = mongoTemplate.findOne(query, RecommendCategoryEntityDB.class, RecommendCategoryEntityDB.ENTITY_NAME);
            return category;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.RecommendServiceImpl.getCategoryByID(Integer id)",e);
            throw new RuntimeException("通过id获取精品分类出错");
        }
    }

    /**
     * 精品推荐列表
     * @return
     */
    public List<RecommendEntityPojo> listRecommend()
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            List<RecommendEntityPojo> list = mongoTemplate.findAll(RecommendEntityPojo.class, RecommendEntityDB.ENTITY_NAME);
            return list;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.RecommendServiceImpl.listRecommend()",e);
            throw new RuntimeException("获取精品推荐列表出错");
        }
    }

    /**
     * 通过id获取精品推荐
     * @param id
     * @return
     */
    public RecommendEntityDB getRecommendByID(Integer id)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = Query.query(Criteria.where("id").is(id));
            RecommendEntityDB recommend = mongoTemplate.findOne(query, RecommendEntityDB.class, RecommendEntityDB.ENTITY_NAME);
            return recommend;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.RecommendServiceImpl.getRecommendByID(Integer id)",e);
            throw new RuntimeException("通过id获取精品推荐出错");
        }
    }

    /**
     * 添加精品推荐
     * @param recommend
     */
    public void addOrUpdateRecommend(RecommendEntityDB recommend)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();

            Update update = new Update();
            Long id = recommend.getId();
            if (id == null)
            {
                id = this.getNextSequenceForFieldKey(RecommendEntityDB.ENTITY_NAME);
                update.set("modifyTime", Calendar.getInstance().getTimeInMillis());
            }
            else {
                update.set("createTime",Calendar.getInstance().getTimeInMillis());
            }
            query.addCriteria(Criteria.where("id").is(id));

            update.set("id",id);
            update.set("bannerURL",recommend.getBannerURL());
            update.set("icon",recommend.getIcon());
            update.set("pic",recommend.getPic());
            update.set("appName",recommend.getAppName());
            update.set("dialogTitle",recommend.getDialogTitle());
            update.set("shortDesc",recommend.getShortDesc());
            update.set("platform",recommend.getPlatform());
            update.set("netOperators",recommend.getNetOperators());
            update.set("packageName",recommend.getPackageName());
            update.set("downloadURL",recommend.getDownloadURL());
            update.set("recommendCategory",recommend.getRecommendCategory());
            update.set("projects",recommend.getProjects());
            update.set("status",recommend.getStatus());
            update.set("mark",recommend.getMark());
            update.set("otherMark",recommend.getOtherMark());

            mongoTemplate.upsert(query,update,RecommendEntityDB.class,RecommendEntityDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.RecommendServiceImpl.addRecommend(RecommendEntityDB recommend)",e);
            throw new RuntimeException("添加精品推荐出错");
        }
    }

    /**
     * 根据项目查询所属的精品推荐
     * @param projectID
     * @return
     */
    public List<RecommendEntityDB> queryRecommendListByProjectID(Integer projectID)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            Criteria criteria = Criteria.where("projects").is(projectID);
            query.addCriteria(criteria);

            String projectSortField = "order"+projectID;
            Sort sort = new Sort(Sort.Direction.ASC,projectSortField);
            query.with(sort);
            List<RecommendEntityDB> list = mongoTemplate.find(query,RecommendEntityDB.class,RecommendEntityDB.ENTITY_NAME);
            return list;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.RecommendServiceImpl.queryRecommendListByProjectID(Integer projectID)");
            throw new RuntimeException("根据项目查询所属的精品推荐出错");
        }
    }

}
