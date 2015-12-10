package com.szqd.project.mobile.lock.controller;

import com.szqd.framework.controller.SpringMVCController;

import com.szqd.framework.model.SelectEntity;
import com.szqd.project.mobile.lock.model.HowLongUseEnum;
import com.szqd.project.mobile.lock.model.URLCategoryEntity;
import com.szqd.project.mobile.lock.service.AppAnalysisService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by like on 3/24/15.
 */

@RestController
@RequestMapping("/app-analysis")
public class AppAnalysisController extends SpringMVCController
{
    private AppAnalysisService appAnalysisService = null;

    public AppAnalysisService getAppAnalysisService() {
        return appAnalysisService;
    }

    public void setAppAnalysisService(AppAnalysisService appAnalysisService) {
        this.appAnalysisService = appAnalysisService;
    }

//    @RequestMapping(value = "/receive-app-useage.do", method = RequestMethod.POST)
    public void receiveAppUseageInfoFromAppClient(String json)
    {
        if (json == null)
        {
            throw new RuntimeException("从客户端传递上来的json为空");
        }

        this.appAnalysisService.saveAppFromClientJson(json);
    }

    private static final String HOW_LONG_USE_PAGE = "/module/report/how-long-use.jsp";
    @RequestMapping(value = "/count-time-seage-for-oneapp.do")
    public ModelAndView countTimeUseageByAppID(String appID,Integer categoryID)
    {

        List<HashMap> timeUseageListForOneApp = this.appAnalysisService.countTimeUseageByAppID(appID,categoryID);

        List<SelectEntity> appList = this.appAnalysisService.queryAppListWithAnalysis();

        List categoryList = this.appAnalysisService.getCategoryList();

        SelectEntity app = new SelectEntity();
        if (appID == null)
        {
            app.setValue("");
        }
        else {
            app.setValue(appID);
        }

        SelectEntity category = new SelectEntity();
        if (categoryID == null)
        {
            category.setValue("");
        }
        else
        {
            category.setValue(categoryID.toString());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("timeUseageListForOneApp",timeUseageListForOneApp);
        modelAndView.addObject("appList",appList);
        modelAndView.addObject("app",app);
        modelAndView.addObject("category",category);
        modelAndView.addObject("categoryList",categoryList);

        modelAndView.setViewName(HOW_LONG_USE_PAGE);
        return modelAndView;
    }

    private static final String HOW_LONG_USE_TOTAL_PAGE = "/module/report/how-long-use-total.jsp";
    @RequestMapping(value = "/count-total-time-seage-for-category.do")
    public ModelAndView countTotalTimeUseage()
    {
        List<HashMap> list = this.appAnalysisService.countTotalTimeUseage();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list",list);
        modelAndView.setViewName(HOW_LONG_USE_TOTAL_PAGE);
        return modelAndView;
    }

    private static final String WHAT_TIME_USE_PAGE = "/module/report/what-time-use.jsp";
    @RequestMapping(value = "/count-time-for-oneapp.do")
    public ModelAndView countTimeByAppID(String appID,Integer categoryID)
    {
        List<HashMap> timeListForOneApp = this.appAnalysisService.countTimeByAppID(appID,categoryID);

        List<SelectEntity> appList = this.appAnalysisService.queryAppListWithAnalysis();

        List categoryList = this.appAnalysisService.getCategoryList();

        SelectEntity app = new SelectEntity();
        if (appID == null)
        {
            app.setValue("");
        }
        else {
            app.setValue(appID);
        }

        SelectEntity category = new SelectEntity();
        if (categoryID == null)
        {
            category.setValue("");
        }
        else
        {
            category.setValue(categoryID.toString());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("timeListForOneApp",timeListForOneApp);
        modelAndView.addObject("appList",appList);
        modelAndView.addObject("app",app);
        modelAndView.addObject("category",category);
        modelAndView.addObject("categoryList",categoryList);

        modelAndView.setViewName(WHAT_TIME_USE_PAGE);
        return modelAndView;
    }

    private static final String FREQUENCY_USE_PAGE = "/module/report/frequency-use.jsp";
    @RequestMapping(value = "/count-frequency-for-oneapp.do")
    public ModelAndView countFrequencyByAppID(String appID,Integer categoryID)
    {
        ModelAndView modelAndView = new ModelAndView();

        HashMap frequencyFor3To5 = this.appAnalysisService.countFrequencyByAppID(appID,categoryID,3,5);
        HashMap frequencyFor1To2 = this.appAnalysisService.countFrequencyByAppID(appID,categoryID,1,2);
        HashMap frequencyFor6To9 = this.appAnalysisService.countFrequencyByAppID(appID,categoryID,6,9);
        HashMap frequencyFor10To19 = this.appAnalysisService.countFrequencyByAppID(appID,categoryID,10,19);
        HashMap frequencyFor20To49 = this.appAnalysisService.countFrequencyByAppID(appID,categoryID,20,49);
        HashMap frequencyFor50ToEnd = this.appAnalysisService.countFrequencyByAppID(appID,categoryID,50,null);
        modelAndView.addObject("frequencyFor1To2",frequencyFor1To2);
        modelAndView.addObject("frequencyFor3To5",frequencyFor3To5);
        modelAndView.addObject("frequencyFor6To9",frequencyFor6To9);
        modelAndView.addObject("frequencyFor10To19",frequencyFor10To19);
        modelAndView.addObject("frequencyFor20To49",frequencyFor20To49);
        modelAndView.addObject("frequencyFor50ToEnd",frequencyFor50ToEnd);

        List categoryList = this.appAnalysisService.getCategoryList();
        modelAndView.addObject("categoryList",categoryList);

        List<SelectEntity> appList = this.appAnalysisService.queryAppListWithAnalysis();
        modelAndView.addObject("appList",appList);

        SelectEntity app = new SelectEntity();
        if (appID == null)
        {
            app.setValue("");
        }
        else
        {
            app.setValue(appID);
        }
        modelAndView.addObject("app",app);

        SelectEntity category = new SelectEntity();
        if (categoryID == null)
        {
            category.setValue("");
        }
        else
        {
            category.setValue(categoryID.toString());
        }
        modelAndView.addObject("category",category);

        modelAndView.setViewName(FREQUENCY_USE_PAGE);

        return modelAndView;
    }


}
