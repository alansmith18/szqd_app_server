package com.szqd.framework.security;


import com.szqd.framework.persistence.CoreDao;
import com.szqd.framework.persistence.MongoDao;
import com.szqd.project.popularize.analysis.model.PlatformUser;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by mac on 14-5-25.
 */
public class SecurityUserService implements UserDetailsService
{
    private CoreDao coreDao = null;


    private MongoDao mongoDao = null;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        return this.loadPopularizeUserByUsername(username);

    }

    private UserDetails loadPopularizeUserByUsername(String username)
    {
        MongoTemplate mongoTemplate = this.mongoDao.getMongoTemplate();
        String queryString = "{loginName:'%s'}";
        queryString = String.format(queryString,username);
        String fields = "{_id:1,loginName:1,loginPwd:1,platformName:1,platformID:1,access:1,incremental:1,scale:1,role:1}";
        BasicQuery query = new BasicQuery(queryString,fields);
        List<PlatformUser> list = mongoTemplate.find(query, PlatformUser.class,PlatformUser.ENTITY_NAME);
        if (list != null && list.size() == 1)
        {
            PlatformUser platformUser = list.get(0);
            UserDetailsEntity user = new UserDetailsEntity();
            user.setUserEntity(platformUser);
            return user;
        }
        throw new UsernameNotFoundException("用户名或者密码错误");
    }

    public CoreDao getCoreDao() {
        return coreDao;
    }

    public void setCoreDao(CoreDao coreDao) {
        this.coreDao = coreDao;
    }

    public MongoDao getMongoDao() {
        return mongoDao;
    }

    public void setMongoDao(MongoDao mongoDao) {
        this.mongoDao = mongoDao;
    }
}
