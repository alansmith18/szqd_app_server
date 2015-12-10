package com.szqd.project.common.controller;

import com.szqd.framework.controller.SpringMVCController;
import com.szqd.framework.model.Pager;
import com.szqd.framework.model.SelectEntity;
import com.szqd.project.common.model.VersionEntity;
import com.szqd.project.common.model.VersionPOJO;
import com.szqd.project.common.service.VersionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by like on 4/21/15.
 */
@RestController
@RequestMapping("/version")
public class VersionController extends SpringMVCController
{
    private VersionService versionService = null;

    private static final String LIST_VERSION_DO = "redirect:/version/version-list.do";
    @RequestMapping(value = "/upsert-version.do",method = RequestMethod.POST)
    public ModelAndView saveOrUpdateVersion(VersionPOJO edit)
    {
        this.versionService.saveOrUpdateVersion(edit);
        ModelAndView view = new ModelAndView();
        view.setViewName(LIST_VERSION_DO);
        return view;
    }

    @RequestMapping(value = "/find-version-info.do",method = RequestMethod.GET)
    public VersionEntity findVersionInfo(VersionPOJO condition)
    {
        return this.versionService.findVersionInfo(condition);
    }

    private static final String VERSION_LIST_PAGE = "/module/version/version-list.jsp";
    @RequestMapping(value = "/version-list.do",method = RequestMethod.GET)
    public ModelAndView getVersionListByCondition(VersionPOJO condition,Pager pager)
    {

        ModelAndView modelAndView = new ModelAndView();
        List<VersionPOJO> list = this.versionService.getVersionListByCondition(condition,pager);
        boolean hasNextPage = list.size() == pager.getCapacity();
        Map param = new HashMap();
        param.put("versionList", list);
        param.put("hasNextPage",hasNextPage);
        param.put("queryCondition",condition);
        modelAndView.setViewName(VERSION_LIST_PAGE);
        modelAndView.addAllObjects(param);
        return modelAndView;
    }

    private static final String EDIT_VERSION_PAGE = "/module/version/version-edit.jsp";
    @RequestMapping(value = "/upsert-version-page.do",method = RequestMethod.GET)
    public ModelAndView editVersionPage(Long id)
    {

        VersionPOJO edit = this.versionService.findVersionInfoByID(id);
        if (edit == null) edit = new VersionPOJO();

        SelectEntity statusOpen = new SelectEntity();
        statusOpen.setText("打开");
        statusOpen.setValue("1");

        SelectEntity statusClose = new SelectEntity();
        statusClose.setText("关闭");
        statusClose.setValue("0");


        ModelAndView view = new ModelAndView();
        view.addObject("edit",edit);
        view.addObject("statusOptions", Arrays.asList(statusOpen,statusClose));
        view.setViewName(EDIT_VERSION_PAGE);
        return view;
    }


    public VersionService getVersionService() {
        return versionService;
    }

    public void setVersionService(VersionService versionService) {
        this.versionService = versionService;
    }
}
