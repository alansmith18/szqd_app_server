package com.szqd.project.common.controller;

import com.szqd.framework.controller.SpringMVCController;
import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.*;
import com.szqd.project.common.service.ProjectService;
import com.szqd.project.common.service.SkinService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by like on 5/18/15.
 */
@RequestMapping(value = "/skin")
@RestController
public class SkinController extends SpringMVCController {

    private SkinService skinService = null;

    private ProjectService projectService = null;

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public SkinService getSkinService() {
        return skinService;
    }

    public void setSkinService(SkinService skinService) {
        this.skinService = skinService;
    }

    private static final String SKIN_LIST_PAGE = "/module/skin/skin-list.jsp";
    @RequestMapping(value = "/list-skin.do")
    public ModelAndView listSkin(SkinEntityDB skin, Pager pager)
    {


        List<SkinEntityPojo> list = this.skinService.listSkin(skin,pager);
        boolean hasNextPage = list.size() == pager.getCapacity();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("skinList",list);
        modelAndView.addObject("hasNextPage",hasNextPage);
        modelAndView.addObject("queryCondition",skin);
        modelAndView.setViewName(SKIN_LIST_PAGE);
        return modelAndView;
    }

    @RequestMapping(value = "/list-skin-json.do")
    public List<SkinEntityPojo> listSkinJson(SkinEntityDB skin, Pager pager)
    {
        List<SkinEntityPojo> list = this.skinService.listSkin(skin,pager);
        return list;
    }

    private static final String ADD_SKIN_PAGE = "/module/skin/skin-add-or-save.jsp";
    @RequestMapping(value = "/add-skin-page.do")
    public ModelAndView addOrUpdateSkinPage(Integer id)
    {

        SkinEntityDB skin = null;
        if (id != null) {
            skin = this.skinService.querySkinByID(id);
        }
        else {
            skin = new SkinEntityDB();
        }

        List<PlatformEnum> platformList = new ArrayList<>();
        platformList.add(PlatformEnum.ANDROID);
        platformList.add(PlatformEnum.IOS);
        platformList.add(PlatformEnum.WINMOBILE);
        platformList.add(PlatformEnum.OTHER);

        List<ProjectEntityDB> projectList = this.projectService.listProject();

        List<ProjectStatusEnum> projectStatusList = new ArrayList<>();
        projectStatusList.add(ProjectStatusEnum.ENABLE);
        projectStatusList.add(ProjectStatusEnum.DISABLE);

        List<IsRecommendEnum> isRecommendList = new ArrayList<>();
        isRecommendList.add(IsRecommendEnum.RECOMMEND);
        isRecommendList.add(IsRecommendEnum.NO_RECOMMEND);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("edit",skin);
        modelAndView.addObject("platformList",platformList);
        modelAndView.addObject("projectList",projectList);
        modelAndView.addObject("projectStatusList",projectStatusList);
        modelAndView.addObject("isRecommendList",isRecommendList);

        modelAndView.setViewName(ADD_SKIN_PAGE);
        return modelAndView;
    }

    private static final String LIST_SKIN_DO = "redirect:/skin/list-skin.do";
    @RequestMapping(value = "/add-skin.do")
    public ModelAndView addOrUpdateSkin(SkinEntityDB edit)
    {
        this.skinService.saveOrUpdateSkin(edit);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LIST_SKIN_DO);

        return modelAndView;
    }
}
