package com.szqd.project.common.controller;

import com.szqd.framework.controller.SpringMVCController;
import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.ProjectEntityDB;
import com.szqd.project.common.model.push.*;
import com.szqd.project.common.service.ProjectService;
import com.szqd.project.common.service.PushMessageService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by like on 7/8/15.
 */
@RestController
@RequestMapping("/push-message")
public class PushMessageController extends SpringMVCController
{
    private static Logger LOGGER = Logger.getLogger(PushMessageController.class);

    private PushMessageService pushMessageService = null;



    private ProjectService projectService = null;

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }



    public PushMessageService getPushMessageService() {
        return pushMessageService;
    }

    public void setPushMessageService(PushMessageService pushMessageService) {
        this.pushMessageService = pushMessageService;
    }

    private static final String LIST_PUSH_MESSAGE_PAGE = "/module/push-message/push-message-list.jsp";
    @RequestMapping(value = "/list-push-message.do")
    public ModelAndView listPushMessage(PushMessagePojo queryCondition,Pager pager)
    {

        List<PushMessagePojo> pushMessageList = this.pushMessageService.listPushMessage(queryCondition, pager);

        boolean hasNextPage = pushMessageList.size() == pager.getCapacity();


        List<TargetURLTypeEnum> targetURLTypeEnumList = new ArrayList<>();
        targetURLTypeEnumList.add(TargetURLTypeEnum.FILE_TYPE);
        targetURLTypeEnumList.add(TargetURLTypeEnum.WEBSITE_TYPE);

        List<MessageTypeEnum> messageTypeEnumList = new ArrayList<>();
        messageTypeEnumList.add(MessageTypeEnum.OS_SECURITY);
        messageTypeEnumList.add(MessageTypeEnum.READERS);
        messageTypeEnumList.add(MessageTypeEnum.SOCIALLY);
        messageTypeEnumList.add(MessageTypeEnum.LIFE_STYLE);
        messageTypeEnumList.add(MessageTypeEnum.VIDEO);
        messageTypeEnumList.add(MessageTypeEnum.MANUALLY);
        messageTypeEnumList.add(MessageTypeEnum.THEME);
        messageTypeEnumList.add(MessageTypeEnum.OFFICE);
        messageTypeEnumList.add(MessageTypeEnum.PHOTOGRAPHY);
        messageTypeEnumList.add(MessageTypeEnum.SHOPPING);
        messageTypeEnumList.add(MessageTypeEnum.MAP_TRAVELLING);
        messageTypeEnumList.add(MessageTypeEnum.EDUCATION);
        messageTypeEnumList.add(MessageTypeEnum.FINANCIAL);
        messageTypeEnumList.add(MessageTypeEnum.HEALTH);
        messageTypeEnumList.add(MessageTypeEnum.GAME_ACTION_ADVENTURE);
        messageTypeEnumList.add(MessageTypeEnum.GAME_ATHLETIC);
        messageTypeEnumList.add(MessageTypeEnum.GAME_BUSINESS_STRATEGE);
        messageTypeEnumList.add(MessageTypeEnum.GAME_CASUAL_PUZZLE);
        messageTypeEnumList.add(MessageTypeEnum.GAME_CHESS_WORLD);
        messageTypeEnumList.add(MessageTypeEnum.GAME_CHILDREN_GAME);
        messageTypeEnumList.add(MessageTypeEnum.GAME_FLIGHT_SHOOTTING);
        messageTypeEnumList.add(MessageTypeEnum.GAME_ONLINE_GAME);
        messageTypeEnumList.add(MessageTypeEnum.GAME_ROLE_PLAY);


        List<PushTypeEnum> pushTypeEnumList = new ArrayList<>();
        pushTypeEnumList.add(PushTypeEnum.STANDARD);
        pushTypeEnumList.add(PushTypeEnum.DETAIL);

        List<ProjectEntityDB> projectList = this.projectService.listProject();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("hasNextPage", hasNextPage);
        modelAndView.addObject("pushMessageList", pushMessageList);
        modelAndView.addObject("targetURLTypeEnumList",targetURLTypeEnumList);
        modelAndView.addObject("messageTypeEnumList", messageTypeEnumList);
        modelAndView.addObject("pushTypeEnumList", pushTypeEnumList);
        modelAndView.addObject("queryCondition",queryCondition);
        modelAndView.addObject("pager",pager);
        modelAndView.addObject("projectList",projectList);
        modelAndView.setViewName(LIST_PUSH_MESSAGE_PAGE);
        return modelAndView;
    }

    private static final String EDIT_PUSH_MESSAGE_PAGE = "/module/push-message/push-message-add-or-save.jsp";
    @RequestMapping(value = "/add-push-message-page.do")
    public ModelAndView addPushMessagePage(Integer id)
    {
        PushMessagePojo edit = this.pushMessageService.fetchPushMessageByID(id);
        if (edit == null)
        {
            edit = new PushMessagePojo();
        }

        List<TargetURLTypeEnum> targetURLTypeEnumList = new ArrayList<>();
        targetURLTypeEnumList.add(TargetURLTypeEnum.FILE_TYPE);
        targetURLTypeEnumList.add(TargetURLTypeEnum.WEBSITE_TYPE);

        List<MessageTypeEnum> messageTypeEnumList = new ArrayList<>();
        messageTypeEnumList.add(MessageTypeEnum.OS_SECURITY);
        messageTypeEnumList.add(MessageTypeEnum.READERS);
        messageTypeEnumList.add(MessageTypeEnum.SOCIALLY);
        messageTypeEnumList.add(MessageTypeEnum.LIFE_STYLE);
        messageTypeEnumList.add(MessageTypeEnum.VIDEO);
        messageTypeEnumList.add(MessageTypeEnum.MANUALLY);
        messageTypeEnumList.add(MessageTypeEnum.THEME);
        messageTypeEnumList.add(MessageTypeEnum.OFFICE);
        messageTypeEnumList.add(MessageTypeEnum.PHOTOGRAPHY);
        messageTypeEnumList.add(MessageTypeEnum.SHOPPING);
        messageTypeEnumList.add(MessageTypeEnum.MAP_TRAVELLING);
        messageTypeEnumList.add(MessageTypeEnum.EDUCATION);
        messageTypeEnumList.add(MessageTypeEnum.FINANCIAL);
        messageTypeEnumList.add(MessageTypeEnum.HEALTH);

        messageTypeEnumList.add(MessageTypeEnum.GAME_ACTION_ADVENTURE);
        messageTypeEnumList.add(MessageTypeEnum.GAME_ATHLETIC);
        messageTypeEnumList.add(MessageTypeEnum.GAME_BUSINESS_STRATEGE);
        messageTypeEnumList.add(MessageTypeEnum.GAME_CASUAL_PUZZLE);
        messageTypeEnumList.add(MessageTypeEnum.GAME_CHESS_WORLD);
        messageTypeEnumList.add(MessageTypeEnum.GAME_CHILDREN_GAME);
        messageTypeEnumList.add(MessageTypeEnum.GAME_FLIGHT_SHOOTTING);
        messageTypeEnumList.add(MessageTypeEnum.GAME_ONLINE_GAME);
        messageTypeEnumList.add(MessageTypeEnum.GAME_ROLE_PLAY);


        List<PushTypeEnum> pushTypeEnumList = new ArrayList<>();
        pushTypeEnumList.add(PushTypeEnum.STANDARD);
        pushTypeEnumList.add(PushTypeEnum.DETAIL);

        List<ProjectEntityDB> projectList = this.projectService.listProject();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("edit", edit);
        modelAndView.addObject("targetURLTypeEnumList",targetURLTypeEnumList);
        modelAndView.addObject("messageTypeEnumList",messageTypeEnumList);
        modelAndView.addObject("pushTypeEnumList",pushTypeEnumList);
        modelAndView.addObject("projectList",projectList);

        modelAndView.setViewName(EDIT_PUSH_MESSAGE_PAGE);
        return modelAndView;
    }

    private static final String LIST_PUSH_MESSAGE_DO = "redirect:/push-message/list-push-message.do";
    @RequestMapping(value = "/add-push-message.do",method = RequestMethod.POST)
    public ModelAndView addPushMessage(PushMessageDB pushMessage, Date beginTimeText,Date endTimeText)
    {
        pushMessage.setBeginTime(beginTimeText.getTime());
        pushMessage.setEndTime(endTimeText.getTime());
        if (pushMessage.getId() == null)
        {
            this.pushMessageService.reducePushOrder();
        }

        this.pushMessageService.savePushMessage(pushMessage);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LIST_PUSH_MESSAGE_DO);
        return modelAndView;
    }

    @RequestMapping(value = "/top-push-message.do")
    public ModelAndView topPushMessageByID(Integer id)
    {
        this.pushMessageService.reducePushOrderBeforeByID(id);
        this.pushMessageService.topPushMessageByID(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LIST_PUSH_MESSAGE_DO);
        return modelAndView;
    }

    @RequestMapping(value = "/send-many-push-message-to-client.do",method = RequestMethod.POST)
    public List<PushMessageDB> sendManyPushMessageToClient(String userID,Integer projectID,String interestJson)
    {
        return this.pushMessageService.fetchManyPushMessage(userID, projectID, interestJson);
    }

    @RequestMapping(value = "/send-push-message-to-client.do",method = RequestMethod.POST)
    public PushMessageDB sendPushMessageToClient(String userID,Integer projectID,String interestJson)
    {
        return this.pushMessageService.fetchPushMessage(userID,projectID,interestJson);
    }

    @RequestMapping(value = "/forecast-push-message-count-for-tomorrow.do")
    public Long forecastPushCountForTomorrow()
    {
        return this.pushMessageService.forecastPushCountForTomorrow();
    }

    @RequestMapping(value = "/receive-install-app.do", method = RequestMethod.POST)
    public void receiveIntallAppFromClient(String json)
    {
        this.pushMessageService.receiveInstallApp(json);
    }


//    @RequestMapping(value = "/save-notice-from-client.do",method = RequestMethod.POST)
    public void receiveNoticeFromClient(String json)
    {
        this.pushMessageService.saveNoticeFromClientJson(json);
    }


    private static final String LIST_CLIENT_NOTICE_PAGE = "/module/push-message/client-notice-list.jsp";
    @RequestMapping(value = "/list-client-notice.do")
    public ModelAndView listClientNotice(Pager pager, NoticeFromClientPojo queryCondition)
    {

        List<NoticeFromClientPojo> clientNoticeList = this.pushMessageService.queryClientNoticeByCondition(pager,queryCondition);
        if (pager == null)
        {
            pager = new Pager();
            pager.setPageNo(1);
            pager.setCapacity(20);
        }
        boolean hasNextPage = clientNoticeList.size() == pager.getCapacity();


        List<MessageTypeEnum> messageTypeEnumList = new ArrayList<>();
        messageTypeEnumList.add(MessageTypeEnum.OS_SECURITY);
        messageTypeEnumList.add(MessageTypeEnum.READERS);
        messageTypeEnumList.add(MessageTypeEnum.SOCIALLY);
        messageTypeEnumList.add(MessageTypeEnum.LIFE_STYLE);
        messageTypeEnumList.add(MessageTypeEnum.VIDEO);
        messageTypeEnumList.add(MessageTypeEnum.MANUALLY);
        messageTypeEnumList.add(MessageTypeEnum.THEME);
        messageTypeEnumList.add(MessageTypeEnum.OFFICE);
        messageTypeEnumList.add(MessageTypeEnum.PHOTOGRAPHY);
        messageTypeEnumList.add(MessageTypeEnum.SHOPPING);
        messageTypeEnumList.add(MessageTypeEnum.MAP_TRAVELLING);
        messageTypeEnumList.add(MessageTypeEnum.EDUCATION);
        messageTypeEnumList.add(MessageTypeEnum.FINANCIAL);
        messageTypeEnumList.add(MessageTypeEnum.HEALTH);

        messageTypeEnumList.add(MessageTypeEnum.GAME_ACTION_ADVENTURE);
        messageTypeEnumList.add(MessageTypeEnum.GAME_ATHLETIC);
        messageTypeEnumList.add(MessageTypeEnum.GAME_BUSINESS_STRATEGE);
        messageTypeEnumList.add(MessageTypeEnum.GAME_CASUAL_PUZZLE);
        messageTypeEnumList.add(MessageTypeEnum.GAME_CHESS_WORLD);
        messageTypeEnumList.add(MessageTypeEnum.GAME_CHILDREN_GAME);
        messageTypeEnumList.add(MessageTypeEnum.GAME_FLIGHT_SHOOTTING);
        messageTypeEnumList.add(MessageTypeEnum.GAME_ONLINE_GAME);
        messageTypeEnumList.add(MessageTypeEnum.GAME_ROLE_PLAY);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("clientNoticeList",clientNoticeList);
        modelAndView.addObject("pager",pager);
        modelAndView.addObject("queryCondition",queryCondition);
        modelAndView.addObject("messageTypeEnumList",messageTypeEnumList);
        modelAndView.addObject("hasNextPage",hasNextPage);
        modelAndView.setViewName(LIST_CLIENT_NOTICE_PAGE);

        return modelAndView;
    }


}
