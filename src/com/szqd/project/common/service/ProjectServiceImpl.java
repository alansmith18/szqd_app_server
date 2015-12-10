package com.szqd.project.common.service;

import com.szqd.framework.service.SuperService;
import com.szqd.project.common.model.ProjectEntityDB;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by like on 5/11/15.
 */
public class ProjectServiceImpl extends SuperService implements ProjectService {

    private static final Logger LOGGER = Logger.getLogger(ProjectServiceImpl.class);




    /**
     * 查询项目列表
     * @return
     */
    public List<ProjectEntityDB> listProject()
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            List<ProjectEntityDB> list = mongoTemplate.findAll(ProjectEntityDB.class,ProjectEntityDB.ENTITY_NAME);
            return list;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.ProjectServiceImpl.listProject()",e);
            throw new RuntimeException("查询项目列表出错");
        }
    }

    /**
     * 添加项目
     * @param project
     */
    public void saveOrUpdateProject(ProjectEntityDB project)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            if (project.getId() == null)
            {
                Long id = this.getNextSequenceForFieldKey(ProjectEntityDB.ENTITY_NAME);

                project.setId(id);
            }

            String queryString = "{ id : %d }";
            queryString = String.format(queryString,project.getId());
            String fieldString = "{id:0}";

            BasicQuery query = new BasicQuery(queryString,fieldString);
            Update update = new Update();
            update.set("id",project.getId());
            update.set("name",project.getName());
            update.set("description",project.getDescription());
            mongoTemplate.upsert(query,update,ProjectEntityDB.class,ProjectEntityDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.ProjectServiceImpl.saveOrUpdateProject(ProjectEntityDB project)",e);
            throw new RuntimeException("添加项目出错");
        }
    }

}
