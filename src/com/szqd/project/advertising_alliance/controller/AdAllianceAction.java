package com.szqd.project.advertising_alliance.controller;

import com.google.gson.Gson;
import com.szqd.framework.model.Pager;
import com.szqd.framework.model.SelectEntity;
import com.szqd.framework.security.UserDetailsEntity;
import com.szqd.framework.security.UsersRole;
import com.szqd.framework.util.DateUtils;
import com.szqd.project.advertising_alliance.model.Activation;
import com.szqd.project.advertising_alliance.model.AdvertisingPOJO;
import com.szqd.project.advertising_alliance.pojo.ActivationPOJO;
import com.szqd.project.advertising_alliance.service.AdvertisingAllianceService;
import com.szqd.project.popularize.analysis.model.PlatformUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


/**
 * Created by like on 4/1/15.
 */

@RequestMapping("/ad-alliance")
@RestController
public class AdAllianceAction
{
    private AdvertisingAllianceService allianceService = null;

    public AdvertisingAllianceService getAllianceService() {
        return allianceService;
    }

    public void setAllianceService(AdvertisingAllianceService allianceService) {
        this.allianceService = allianceService;
    }

    private static final String ADD_USER_PAGER = "/project/ad_platform/register-user.jsp";
    @RequestMapping(value = "/add-user-page.do",method = RequestMethod.GET)
    public ModelAndView showAddUserPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        List<UsersRole> roleList = new ArrayList<>();
        roleList.add(UsersRole.ROLE_AD_PLATFORM_ADVERTISER);
        roleList.add(UsersRole.ROLE_AD_PLATFORM_CHANNEL);

        modelAndView.addObject("roleList", roleList);
        modelAndView.setViewName(ADD_USER_PAGER);
        return modelAndView;
    }


    @RequestMapping(value = "/register-user.do",method = RequestMethod.POST)
    public ModelAndView saveOrUpdateUser(PlatformUser user)
    {
        ModelAndView modelAndView = new ModelAndView();
        this.allianceService.upsetPlatformUser(user);
        modelAndView.setViewName("redirect:/login.jsp");
        return modelAndView;
    }


    @RequestMapping(value = "/update-activation.do")
    public void updateActivation(Long advertiserID,Long channelID,Long numberOfActivation)
    {
        this.allianceService.updateActivation(advertiserID,channelID,numberOfActivation);
    }

    private static final String ACTIVATION_PAGE = "/project/ad_platform/activation.jsp";
    @RequestMapping(value = "/activation-page.do")
    public ModelAndView activationPage(ActivationPOJO condition)
    {

        AdvertisingPOJO advertising = this.allianceService.fetchAdvertisingByIDFromDB(condition.getAdID());
        List<PlatformUser> channelList = this.allianceService.queryUsersWithIDs(advertising.getChannelIDList());
        Long channelID = condition.getChannelID();
        if (channelID == null){
            if (!channelList.isEmpty())
            {
                condition.setChannelID(channelList.get(0).getId());
            }
        }
        Long date = condition.getDate();
        if (date == null){
            date = DateUtils.truncateDate(Calendar.getInstance()).getTimeInMillis();
            condition.setDate(date);
        }

        ActivationPOJO activation = this.allianceService.queryActivationWithDate(condition);

        ModelAndView view = new ModelAndView();
        Gson gson = new Gson();
        String activationsJson = gson.toJson(activation);
        view.addObject("activation",activationsJson);
        view.addObject("channelList",channelList);
        view.addObject("adID",condition.getAdID());
        view.addObject("condition",condition);
        view.setViewName(ACTIVATION_PAGE);
        return view;
    }

    private static final String LIST_AD_PAGE = "/project/ad_platform/ad-list.jsp";
    @RequestMapping(value = "/list-advertising.do",method = RequestMethod.GET)
    public ModelAndView listAdvertising(Pager pager,AdvertisingPOJO queryCondition)
    {
        Object loginUserObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loginUserObj == null || "anonymousUser".equals(loginUserObj))
        {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/login.jsp");
            return modelAndView;
        }
        UserDetailsEntity loginUser = (UserDetailsEntity) loginUserObj;
        Integer roleID = loginUser.getUserEntity().getRole();

        if (queryCondition.getStatus() == null){
            boolean isChannel = roleID.equals(UsersRole.ROLE_AD_PLATFORM_CHANNEL.getRoleId());
            if (isChannel)
                queryCondition.setStatus(AdvertisingPOJO.STATUS_IN_USE);
            else
                queryCondition.setStatus(AdvertisingPOJO.STATUS_SUBMIT);
        }


        boolean isAdvertiser = roleID.equals(UsersRole.ROLE_AD_PLATFORM_ADVERTISER.getRoleId());
        if (isAdvertiser) queryCondition.setUid(loginUser.getUserEntity().getId());

        List<AdvertisingPOJO> list = this.allianceService.listAdvertising(pager,queryCondition);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", list);
        modelAndView.addObject("queryCondition",queryCondition);
        SelectEntity s1 = new SelectEntity();
        s1.setValue(String.valueOf(AdvertisingPOJO.STATUS_SUBMIT));
        s1.setText("已提交");

        SelectEntity s2 = new SelectEntity();
        s2.setValue(String.valueOf(AdvertisingPOJO.STATUS_IN_USE));
        s2.setText("使用中");

        SelectEntity s3 = new SelectEntity();
        s3.setValue(String.valueOf(AdvertisingPOJO.STATUS_FAILED));
        s3.setText("未通过");

        List statusList = new ArrayList<>();
        statusList.add(s1);
        statusList.add(s2);
        statusList.add(s3);
        modelAndView.addObject("statusList",statusList);

        modelAndView.setViewName(LIST_AD_PAGE);
        return modelAndView;
    }

    private static final String SUBMIT_ADVERTISING_PAGE = "/project/ad_platform/ad-add-or-save.jsp";
    @RequestMapping(value = "/submit-advertising-page.do")
    public ModelAndView submitAdvertisingPage(Long id)
    {
        AdvertisingPOJO advertisingPOJO = allianceService.fetchAdvertisingByIDFromDB(id);
        List<PlatformUser> userList = null;
        if (advertisingPOJO != null && advertisingPOJO.getPendingChannelIDList() != null) {
            userList = allianceService.queryUsersWithIDs(advertisingPOJO.getPendingChannelIDList());
            advertisingPOJO.setPendingChannelTextList(userList);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("edit",advertisingPOJO);
        modelAndView.setViewName(SUBMIT_ADVERTISING_PAGE);
        return modelAndView;
    }


    private static final String LIST_AD_DO = "redirect:/ad-alliance/list-advertising.do";
    @RequestMapping(value = "/submit-advertising.do",method = RequestMethod.POST)
    public ModelAndView submitAdvertising(AdvertisingPOJO advertising)
    {
        Object loginUserObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loginUserObj == null || "anonymousUser".equals(loginUserObj))
        {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/login.jsp");
            return modelAndView;
        }

        UserDetailsEntity loginUser = (UserDetailsEntity) loginUserObj;
        Integer roleID = loginUser.getUserEntity().getRole();
        boolean isAdvertiser = UsersRole.ROLE_AD_PLATFORM_ADVERTISER.getRoleId().equals(roleID);
        if (isAdvertiser) {
            advertising.setUid(loginUser.getUserEntity().getId());
            advertising.setStatus(AdvertisingPOJO.STATUS_SUBMIT);
        }

        allianceService.submitAdvertising(advertising);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LIST_AD_DO);
        return modelAndView;
    }

    @RequestMapping(value = "/select-ad.do")
    public void selectAd(Long id)
    {

        Object loginUserObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loginUserObj == null || "anonymousUser".equals(loginUserObj))
        {
            throw new RuntimeException("重新登录");
        }
        UserDetailsEntity loginUser = (UserDetailsEntity) loginUserObj;
        AdvertisingPOJO ad = this.allianceService.fetchAdvertisingByIDFromDB(id);
        Set<Long> adChannelIDList = ad.getChannelIDList();
        if (adChannelIDList == null) adChannelIDList = new HashSet<>();
        adChannelIDList.add(loginUser.getUserEntity().getId());
        ad.setChannelIDList(adChannelIDList);

        Set<Long> pendingChannelIDList = ad.getPendingChannelIDList();
        if (pendingChannelIDList == null) pendingChannelIDList = new HashSet<>();
        pendingChannelIDList.add(loginUser.getUserEntity().getId());
        ad.setPendingChannelIDList(pendingChannelIDList);

        this.allianceService.submitAdvertising(ad);

    }


    

}
