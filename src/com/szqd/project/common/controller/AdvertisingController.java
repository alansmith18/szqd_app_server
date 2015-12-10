package com.szqd.project.common.controller;

import com.szqd.framework.controller.SpringMVCController;
import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.*;
import com.szqd.project.common.service.AdvertisingService;
import com.szqd.project.common.service.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by like on 5/19/15.
 */

@RequestMapping("/advertising")
@RestController
public class AdvertisingController extends SpringMVCController
{
    private AdvertisingService advertisingService = null;
    private ProjectService projectService = null;

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public AdvertisingService getAdvertisingService() {
        return advertisingService;
    }

    public void setAdvertisingService(AdvertisingService advertisingService) {
        this.advertisingService = advertisingService;
    }

    private static final String ADD_ADVERTISING_PAGE = "/module/advertising/advertising-add-or-save.jsp";
    @RequestMapping(value = "/add-advertising-page.do")
    public ModelAndView addAdvertisingPage(Long id)
    {
        ModelAndView modelAndView = new ModelAndView();
        AdvertisingEntityDB advertisingEntityDB = this.advertisingService.loadAdvertisingByIDFromDB(id);

        String effectiveStartTimeText = "";
        String effectiveEndTimeText = "";

        if (advertisingEntityDB == null)
        {
            advertisingEntityDB = new AdvertisingEntityDB();
        }
        else {
            Long effectiveStartTime = advertisingEntityDB.getEffectiveStartTime();
            Date effectiveStartDate = null;
            if (effectiveStartTime != null) {
                effectiveStartDate = new Date(effectiveStartTime);
            }
            Long effectiveEndTime = advertisingEntityDB.getEffectiveEndTime();
            Date effectiveEndDate = null;
            if (effectiveEndTime != null) {
                effectiveEndDate = new Date(effectiveEndTime);
            }

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            if (effectiveStartDate != null && effectiveEndDate != null) {
                effectiveStartTimeText = sf.format(effectiveStartDate);
                effectiveEndTimeText = sf.format(effectiveEndDate);
            }
        }

        modelAndView.addObject("effectiveStartTimeText",effectiveStartTimeText);
        modelAndView.addObject("effectiveEndTimeText",effectiveEndTimeText);


        modelAndView.addObject("edit",advertisingEntityDB);

        List<PlatformEnum> platformEnumList = new ArrayList<>();
        platformEnumList.add(PlatformEnum.ANDROID);
        platformEnumList.add(PlatformEnum.IOS);
        platformEnumList.add(PlatformEnum.WINMOBILE);
        platformEnumList.add(PlatformEnum.OTHER);
        modelAndView.addObject("platformEnumList",platformEnumList);

        List<NetOperatorsEnum> netOperatorsEnumList = new ArrayList<>();
        netOperatorsEnumList.add(NetOperatorsEnum.NET2G);
        netOperatorsEnumList.add(NetOperatorsEnum.NET3G);
        netOperatorsEnumList.add(NetOperatorsEnum.NET4G);
        netOperatorsEnumList.add(NetOperatorsEnum.NETWIFI);
        modelAndView.addObject("netOperatorsEnumList", netOperatorsEnumList);

        List<ProjectEntityDB> projectList = this.projectService.listProject();
        modelAndView.addObject("projectList", projectList);

        AdvertisingTypeEnum startUp = AdvertisingTypeEnum.START_UP;
        List<AdvertisingTypeEnum> advertisingTypeList = new ArrayList<>();
        advertisingTypeList.add(startUp);
        advertisingTypeList.add(AdvertisingTypeEnum.WELCOME_PAGE);

        modelAndView.addObject("advertisingTypeList", advertisingTypeList);

        List<FrameTypeEnum> frameTypeEnumList = new ArrayList<>();
        frameTypeEnumList.add(FrameTypeEnum.FULL_SCREEN);
        frameTypeEnumList.add(FrameTypeEnum.TWO_THIRDS_SCREEN);
        modelAndView.addObject("frameTypeEnumList", frameTypeEnumList);

        List<ClickTypeEnum> clickTypeEnumList = new ArrayList<>();
        clickTypeEnumList.add(ClickTypeEnum.DOWNLOAD_FILE);
        clickTypeEnumList.add(ClickTypeEnum.WEBSITE);
        modelAndView.addObject("clickTypeEnumList", clickTypeEnumList);

        modelAndView.setViewName(ADD_ADVERTISING_PAGE);

        return modelAndView;
    }

    @RequestMapping(value = "/list-advertising-json.do")
    public List<AdvertisingEntityPojo> listAdvertisingForJson(AdvertisingEntityDB advertising,Pager pager)
    {
        List<AdvertisingEntityPojo> list = this.advertisingService.listAdvertising(advertising,pager);
        return list;
    }

    private static final String LIST_ADVERTISING_PAGE = "/module/advertising/advertising-list.jsp";
    @RequestMapping(value = "/list-advertising.do")
    public ModelAndView listAdvertising(AdvertisingEntityDB advertising,Pager pager)
    {

        List<AdvertisingEntityPojo> list = this.advertisingService.listAdvertising(advertising,pager);

        boolean hasNextPage = pager.getCapacity() == list.size();

        List<ProjectEntityDB> projectList = this.projectService.listProject();

        AdvertisingTypeEnum startUp = AdvertisingTypeEnum.START_UP;
        List<AdvertisingTypeEnum> advertisingTypeList = new ArrayList<>();
        advertisingTypeList.add(startUp);
        advertisingTypeList.add(AdvertisingTypeEnum.WELCOME_PAGE);

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

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("platformEnumList",platformEnumList);
        modelAndView.addObject("netOperatorsEnumList",netOperatorsEnumList);
        modelAndView.addObject("advertisingTypeList",advertisingTypeList);
        modelAndView.addObject("advertisingList",list);
        modelAndView.addObject("projectList",projectList);
        modelAndView.addObject("queryCondition",advertising);
        modelAndView.addObject("pager",pager);
        modelAndView.addObject("hasNextPage",hasNextPage);

        modelAndView.setViewName(LIST_ADVERTISING_PAGE);
        return modelAndView;
    }

    private static final String LIST_ADVERTISING_DO = "redirect:/advertising/list-advertising.do";
    @RequestMapping(value = "/add-advertising.do",method = RequestMethod.POST)
    public ModelAndView addAdvertising(AdvertisingEntityDB advertising, String effectiveStartTimeText, String effectiveEndTimeText)
    {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date effectStartTimeDate = sf.parse(effectiveStartTimeText);
            Date effectEndTimeDate = sf.parse(effectiveEndTimeText);
            advertising.setEffectiveStartTime(effectStartTimeDate.getTime());
            advertising.setEffectiveEndTime(effectEndTimeDate.getTime());
            String targetURL = advertising.getTargetURL().trim();
            if ("".equals(targetURL))
                advertising.setTargetURL(null);

        } catch (ParseException e) {
            throw new RuntimeException("有效时间出错");
        }


        this.advertisingService.saveAdvertising(advertising);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(LIST_ADVERTISING_DO);

        return modelAndView;
    }

    @RequestMapping(value = "/fetch-today-advertising-by-project.do", method = RequestMethod.GET)
    public List<AdvertisingEntityPojo> fetchTodayAdvertisingListByProjectID(AdvertisingEntityPojo condition)
    {
        if (condition == null) {
            condition = new AdvertisingEntityPojo();
            condition.setProjectID(-1l);

        }
        if (condition.getType() == null)
        {
            condition.setType(AdvertisingTypeEnum.START_UP.getId());
        }

        List<AdvertisingEntityPojo> list = this.advertisingService.fetchTodayAdvertisingListByProjectID(condition);
        return list;
    }



}
