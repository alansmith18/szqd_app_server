package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.SkinEntityDB;
import com.szqd.project.common.model.SkinEntityPojo;

import java.util.List;

/**
 * Created by like on 5/18/15.
 */
public interface SkinService {

    /**
     * 根据分页罗列皮肤列表
     * @param skin
     * @param pager
     * @return
     */
    public List<SkinEntityPojo> listSkin(SkinEntityDB skin, Pager pager);

    /**
     * 根据id查询皮肤
     * @param id
     * @return
     */
    public SkinEntityDB querySkinByID(Integer id);


    /**
     * 保存或更新皮肤
     * @param edit
     */
    public void saveOrUpdateSkin(SkinEntityDB edit);

}
