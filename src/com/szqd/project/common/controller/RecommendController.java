package com.szqd.project.common.controller;

import com.szqd.framework.controller.SpringMVCController;
import com.szqd.project.common.model.*;
import com.szqd.project.common.service.ProjectService;
import com.szqd.project.common.service.RecommendService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by like on 5/11/15.
 */
@RestController
@RequestMapping("/recommend")
public class RecommendController extends SpringMVCController {

    private RecommendService recommendService = null;
    private ProjectService projectService = null;

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public RecommendService getRecommendService() {
        return recommendService;
    }

    public void setRecommendService(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    private static final String RECOMMEND_LIST_PAGE = "/module/recommend/recommend-list.jsp";
    @RequestMapping(value = "/recommend-list.do")
    public ModelAndView listRecommend()
    {
        ModelAndView modelAndView = new ModelAndView();

        List<RecommendEntityPojo> recommendList = this.recommendService.listRecommend();

        modelAndView.addObject("recommendList",recommendList);
        modelAndView.setViewName(RECOMMEND_LIST_PAGE);
        return modelAndView;
    }

    private static final String EDIT_RECOMMEND_PAGE = "/module/recommend/recommend-add-or-save.jsp";
    @RequestMapping(value = "/edit-recommend-page.do")
    public ModelAndView editRecommendPage(Integer id)
    {
        ModelAndView modelAndView = new ModelAndView();
        RecommendEntityDB edit = this.recommendService.getRecommendByID(id);
        if (edit == null)
        {
            edit = new RecommendEntityDB();
        }

        List<ProjectEntityDB> projectList = this.projectService.listProject();

        List<PlatformEnum> platformEnumList = new ArrayList<>();
        platformEnumList.add(PlatformEnum.ANDROID);
        platformEnumList.add(PlatformEnum.IOS);
        platformEnumList.add(PlatformEnum.WINMOBILE);
        platformEnumList.add(PlatformEnum.OTHER);

        List<NetOperatorsEnum> netOperatorsEnumList = new ArrayList<>();
        netOperatorsEnumList.add(NetOperatorsEnum.NET2G);
        netOperatorsEnumList.add(NetOperatorsEnum.NET3G);
        netOperatorsEnumList.add(NetOperatorsEnum.NET4G);
        netOperatorsEnumList.add(NetOperatorsEnum.NETWIFI);

        List<RecommendCategoryEnum> recommendCategoryEnumList = new ArrayList<>();
        recommendCategoryEnumList.add(RecommendCategoryEnum.RECOMMEND);
        recommendCategoryEnumList.add(RecommendCategoryEnum.MAIN_INTERFACE);

        List<ProjectStatusEnum> projectStatusEnumList = new ArrayList<>();
        projectStatusEnumList.add(ProjectStatusEnum.ENABLE);
        projectStatusEnumList.add(ProjectStatusEnum.DISABLE);

        List<RecommendFlagEnum> recommendFlagEnumList = new ArrayList<>();
        recommendFlagEnumList.add(RecommendFlagEnum.HOT);
        recommendFlagEnumList.add(RecommendFlagEnum.QUALITY);

        List<RecommendOtherFlagEnum> recommendOtherFlagEnumList = new ArrayList<>();
        recommendOtherFlagEnumList.add(RecommendOtherFlagEnum.COOPERATION_360);
        recommendOtherFlagEnumList.add(RecommendOtherFlagEnum.OTHER);

        modelAndView.addObject("recommendOtherFlagList",recommendOtherFlagEnumList);
        modelAndView.addObject("recommendFlagList",recommendFlagEnumList);
        modelAndView.addObject("projectStatusList",projectStatusEnumList);
        modelAndView.addObject("recommendCategoryList",recommendCategoryEnumList);
        modelAndView.addObject("netOperatorsList",netOperatorsEnumList);
        modelAndView.addObject("platformList",platformEnumList);
        modelAndView.addObject("edit", edit);
        modelAndView.addObject("projectList",projectList);

        modelAndView.setViewName(EDIT_RECOMMEND_PAGE);
        return modelAndView;

    }

    private static final String RECOMMEND_LIST_DO = "/recommend/recommend-list.do";
    @RequestMapping(value = "/add-recommend.do")
    public ModelAndView addRecommend(RecommendEntityDB recommend)
    {
        this.recommendService.addOrUpdateRecommend(recommend);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(RECOMMEND_LIST_DO);
        return modelAndView;
    }

    private static final String LIST_CATEGORY_PAGE = "/module/recommend/recommend-category-list.jsp";
    @RequestMapping(value = "/list-category.do")
    public ModelAndView listRecommendCategory()
    {
        List<RecommendCategoryEntityDB> listCategory = this.recommendService.listRecommendCategory();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listCategory", listCategory);
        modelAndView.setViewName(LIST_CATEGORY_PAGE);
        return modelAndView;
    }


    private static final String ADD_RECOMMEND_CATEGORY_PAGE = "/module/recommend/recommend-category-add-or-save.jsp";
    @RequestMapping(value = "/add-recommend-category-page.do")
    public ModelAndView addRecommendCategoryPage(RecommendCategoryEntityDB edit)
    {
        List<ProjectEntityDB> projectList = this.projectService.listProject();
        RecommendCategoryEntityDB category = this.recommendService.getCategoryByID(edit.getId());
        List<PlatformEnum> platformList = new ArrayList<>();
        platformList.add(PlatformEnum.ANDROID);
        platformList.add(PlatformEnum.IOS);
        platformList.add(PlatformEnum.WINMOBILE);
        platformList.add(PlatformEnum.OTHER);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("projectList",projectList);
        modelAndView.addObject("platformList",platformList);

        if (category == null)
        {
            category = new RecommendCategoryEntityDB();
        }
        modelAndView.addObject("edit",category);
        modelAndView.setViewName(ADD_RECOMMEND_CATEGORY_PAGE);

        return modelAndView;
    }

    private static final String LIST_CATEGORY = "redirect:/recommend/list-category.do";
    @RequestMapping(value = "/save-update-recommend-category.do")
    public ModelAndView saveOrUpdateRecommendCategory(RecommendCategoryEntityDB edit)
    {
        this.recommendService.saveOrUpdateRecommendCategory(edit);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LIST_CATEGORY);
        return modelAndView;
    }

    private static final String ORDERBY_CATEGORY_PAGE = "/module/recommend/category-orderby-list.jsp";
    @RequestMapping(value = "/category-orderby.do")
    public ModelAndView orderByCategoryPage(RecommendCategoryEntityDB edit)
    {
        List<HashMap> recommendList = this.recommendService.queryRecommendByCategory(edit);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("recommendList", recommendList);
        modelAndView.addObject("category",edit);
        modelAndView.setViewName(ORDERBY_CATEGORY_PAGE);

        return modelAndView;
    }

    private static final String LIST_CATEGORY_DO = "redirect:/recommend/list-category.do";
    @RequestMapping(value = "/save-orderby-category.do",method = RequestMethod.POST)
    public ModelAndView saveOrderbyCategory(@RequestParam MultiValueMap param)
    {

        String orderFieldNameKey = ((List<String>)param.get("orderFieldName")).get(0);
        List<String> orderList = ((List<String>)param.get(orderFieldNameKey));
        List<String> recommendIDList = (List<String>)param.get("recommendID");

        this.recommendService.updateCategoryOrderby(orderFieldNameKey, recommendIDList, orderList);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LIST_CATEGORY_DO);

        return modelAndView;
    }

    @RequestMapping(value = "/find-project-recommend-list.do")
    public List<RecommendEntityDB> queryRecommendListByProjectID(Integer projectID)
    {
        List<RecommendEntityDB> list = this.recommendService.queryRecommendListByProjectID(projectID);
        return list;
    }

}
