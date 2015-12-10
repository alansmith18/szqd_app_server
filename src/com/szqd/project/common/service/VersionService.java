package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.VersionEntity;
import com.szqd.project.common.model.VersionPOJO;

import java.util.List;

/**
 * Created by like on 4/21/15.
 */
public interface VersionService {

    /**
     * 获取所有的版本更新
     * @return
     */
    public List<VersionPOJO> getVersionListByCondition(VersionPOJO condition,Pager pager);

    public VersionPOJO findVersionInfoByID(Long id);

    /**
     * 检查app版本
     * @return
     */
    public VersionPOJO findVersionInfo(VersionPOJO condition);

    /**
     * 编辑或者新增app版本
     * @param newVersion
     */
    public void saveOrUpdateVersion(VersionEntity newVersion);


}
