package com.szqd.project.tracking_statistics.controller;


import com.google.gson.Gson;
import com.szqd.framework.model.Pager;
import com.szqd.framework.util.DateUtils;
import com.szqd.framework.util.StringUtil;
import com.szqd.project.common.model.ProjectEntityDB;
import com.szqd.project.common.service.ProjectService;
import com.szqd.project.tracking_statistics.model.TrackingDataDB;
import com.szqd.project.tracking_statistics.model.TrackingDataPOJO;
import com.szqd.project.tracking_statistics.model.TrackingEventPOJO;
import com.szqd.project.tracking_statistics.service.TrackingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

/**
 * Created by like on 11/18/15.
 */
@RestController
@RequestMapping("/tracking-statistics")
public class TrackingController {

    private TrackingService trackingService = null;

    public TrackingService getTrackingService() {
        return trackingService;
    }

    public void setTrackingService(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    private ProjectService projectService = null;

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }



    private static final String LIST_EVENT_PAGE = "/project/tracking_statistics/event-list.jsp";
    @RequestMapping(value = "/list-event.do")
    public ModelAndView listEvent(TrackingEventPOJO condition,Pager pager)
    {
        if (condition == null) condition = new TrackingEventPOJO();
        List<ProjectEntityDB> projectList = this.projectService.listProject();
        List<TrackingEventPOJO> list = this.trackingService.listEvent(condition,pager);
        boolean hasNextPage = list.size() == pager.getCapacity();
        ModelAndView view = new ModelAndView();
        view.addObject("list",list);
        view.addObject("queryCondition",condition);
        view.addObject("pager",pager);
        view.addObject("hasNextPage",hasNextPage);
        view.addObject("projectList",projectList);
        view.setViewName(LIST_EVENT_PAGE);
        return view;
    }

    private static final String UPSERT_EVENT_PAGE = "/project/tracking_statistics/upsert-event.jsp";
    @RequestMapping(value = "/upsert-event-page.do")
    public ModelAndView upsertEventPage(Long id)
    {
        List<ProjectEntityDB> projectList = this.projectService.listProject();
        TrackingEventPOJO edit = this.trackingService.queryTrackingEventByIDFromDB(id);
        if (edit == null) edit = new TrackingEventPOJO();
        ModelAndView view = new ModelAndView();
        view.addObject("edit",edit);
        view.addObject("projectList",projectList);
        view.setViewName(UPSERT_EVENT_PAGE);

        return view;
    }

    private static final String LIST_EVENT_DO = "redirect:/tracking-statistics/list-event.do";
    @RequestMapping(value = "/upsert-event.do",method = RequestMethod.POST)
    public ModelAndView upsertEvent(TrackingEventPOJO edit)
    {
        this.trackingService.upsertTrackingEvent(edit);
        ModelAndView view = new ModelAndView();
        view.setViewName(LIST_EVENT_DO);
        return view;
    }

    @RequestMapping(value = "/tracking-data.do")
    public ModelAndView addTrackAndForward(Long id,HttpServletRequest request)
    {

        TrackingEventPOJO event = this.trackingService.queryTrackingEventByIDFromCache(id);
        String forwardURL = event.getForwardURL();
        TrackingDataDB tracking = new TrackingDataDB();
        tracking.setEventID(id);
        String ipString = request.getRemoteAddr();
        long ip = StringUtil.ipToLong(ipString);
        tracking.setIp(ip);
        Calendar now = Calendar.getInstance();
        tracking.setCreateTime(now.getTimeInMillis());
        tracking.setCreateDay(DateUtils.truncateDate(now).getTimeInMillis());

        this.trackingService.addTracking(tracking);

        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:"+forwardURL);
        return view;

    }

    private static final String REPORT_NUMBER_OF_TRIGGER_EVENT_PAGE = "/project/tracking_statistics/report/event-report.jsp";
    @RequestMapping(value = "/report-number-of-trigger-event.do")
    public ModelAndView reportNumberOfTriggerEvent(TrackingDataPOJO query)
    {

        List<TrackingDataPOJO> list = this.trackingService.fetchTrackingForReport(query);
        list.forEach(e -> e.setCreateTimeText());
        List<TrackingEventPOJO> eventList = this.trackingService.listEvent(new TrackingEventPOJO(),null);

        ModelAndView view = new ModelAndView();
        view.addObject("queryCondition",query);
        view.addObject("list",new Gson().toJson(list));
        view.addObject("eventList",eventList);
        view.setViewName(REPORT_NUMBER_OF_TRIGGER_EVENT_PAGE);
        return view;

    }

}
