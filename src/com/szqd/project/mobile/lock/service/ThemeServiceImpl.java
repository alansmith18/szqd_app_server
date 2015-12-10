package com.szqd.project.mobile.lock.service;

import com.szqd.framework.model.Pager;
import com.szqd.framework.service.SuperService;
import com.szqd.project.mobile.lock.model.ThemeEntity;
import com.szqd.project.mobile.lock.model.ThemeEntityDB;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Calendar;
import java.util.List;

/**
 * Created by like on 5/5/15.
 */
public class ThemeServiceImpl extends SuperService implements ThemeService
{
    private static final Logger LOGGER = Logger.getLogger(ThemeServiceImpl.class);

    private static final int ORDER_TYPE_CREATETIME = 0;
    private static final int ORDER_TYPE_DOWNLOAD = 1;
    /**
     * 根据条件查询主题
     * @param theme
     * @param pager
     * @param orderType
     * @return
     */
    public List<ThemeEntity> queryThemes(ThemeEntityDB theme,Pager pager,Integer orderType)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            if (theme != null)
            {
                Criteria criteria = null;
                String themeName = theme.getName();
                if (themeName != null)
                {
                    criteria = Criteria.where("name").regex(themeName.trim());
                }

                if (criteria != null)
                {
                    query.addCriteria(criteria);
                }
            }

            query.skip(pager.getOffset());
            query.limit(pager.getCapacity());

            if (orderType != null)
            {

                if (orderType.intValue() == ORDER_TYPE_CREATETIME)
                {
                    Sort sort = new Sort(Sort.Direction.DESC,"createTime");
                    query.with(sort);
                }
                else if (orderType.intValue() == ORDER_TYPE_DOWNLOAD)
                {
                    Sort sort = new Sort(Sort.Direction.DESC,"downloadCount");
                    query.with(sort);
                }
            }

            List<ThemeEntity> list = mongoTemplate.find(query,ThemeEntity.class, ThemeEntityDB.ENTITY_NAME);
            return list;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.mobile.lock.service.ThemeServiceImpl.queryThemes(ThemeEntity theme)",e);
            throw new RuntimeException("根据条件查询主题出错");
        }
    }

    /**
     * 添加主题
     * @param theme
     */
    public void addTheme(ThemeEntityDB theme)
    {
        try {
            Long id = this.getNextSequenceForFieldKey(ThemeEntityDB.ENTITY_NAME);
            theme.setId(id);
            theme.setCreateTime(Calendar.getInstance().getTimeInMillis());
            theme.setDownloadCount(0);
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            mongoTemplate.insert(theme,ThemeEntityDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.mobile.lock.service.ThemeServiceImpl.addTheme()",e);
            throw new RuntimeException("添加主题出错");
        }
    }
}
