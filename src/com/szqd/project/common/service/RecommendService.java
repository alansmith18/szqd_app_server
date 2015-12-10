package com.szqd.project.common.service;

import com.szqd.project.common.model.RecommendCategoryEntityDB;
import com.szqd.project.common.model.RecommendEntityDB;
import com.szqd.project.common.model.RecommendEntityPojo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by like on 5/11/15.
 */
public interface RecommendService {


    /**
     * 更新分类的排序
     * @param recommendIDList
     * @param orderbyList
     */
    public void updateCategoryOrderby(String orderFieldName,List<String> recommendIDList, List<String> orderbyList);


    /**
     * 通过分类查询精品推荐
     * @param category
     * @return
     */
    public List<HashMap> queryRecommendByCategory(RecommendCategoryEntityDB category);

    /**
     * 保存或者修改精品分类
     */
    public void saveOrUpdateRecommendCategory(RecommendCategoryEntityDB category);

    /**
     * 罗列精品推荐分类
     * @return
     */
    public List<RecommendCategoryEntityDB> listRecommendCategory();

    /**
     * 通过id获取精品分类
     * @param id
     * @return
     */
    public RecommendCategoryEntityDB getCategoryByID(Long id);

    /**
     * 精品推荐列表
     * @return
     */
    public List<RecommendEntityPojo> listRecommend();

    /**
     * 通过id获取精品推荐
     * @param id
     * @return
     */
    public RecommendEntityDB getRecommendByID(Integer id);


    /**
     * 添加精品推荐
     * @param recommend
     */
    public void addOrUpdateRecommend(RecommendEntityDB recommend);

    /**
     * 根据项目查询所属的精品推荐
     * @param projectID
     * @return
     */
    public List<RecommendEntityDB> queryRecommendListByProjectID(Integer projectID);
}
