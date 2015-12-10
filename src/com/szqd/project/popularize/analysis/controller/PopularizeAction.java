package com.szqd.project.popularize.analysis.controller;

import com.szqd.framework.controller.JQueryValidateRemoteValue;
import com.szqd.framework.security.Menu;
import com.szqd.framework.security.UserDetailsEntity;
import com.szqd.framework.security.UsersRole;
import com.szqd.project.popularize.analysis.model.AppActivationEntity;
import com.szqd.project.popularize.analysis.model.AppInfoEntity;
import com.szqd.project.popularize.analysis.model.PlatformUser;
import com.szqd.project.popularize.analysis.service.PopularizeAnalysisService;

import net.sf.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import javax.annotation.Resource;
import java.util.*;


/**
 * Created by like on 4/1/15.
 */

@RequestMapping("/popularize")
@RestController
public class PopularizeAction
{

    @Resource(name = "popularizeAnalysisService")
    private PopularizeAnalysisService popularizeAnalysisService = null;


    @RequestMapping("/add-activation.do")
    public void addAppActiveInfo(AppActivationEntity appActivation)
    {
        this.popularizeAnalysisService.addAppActivationInfo(appActivation);
    }

    private static final String INDEX_PAGE = "/module/index.jsp";
    @RequestMapping("/index.do")
    public ModelAndView index()
    {
        UserDetailsEntity loginUser = (UserDetailsEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Menu> menuList = loginUser.getMenuList();
        Map map = new HashMap();
        map.put("menuList",menuList);
        ModelAndView mv = new ModelAndView(INDEX_PAGE,map);
        return mv;
    }

    private static final String QUERY_ACTIVATION_PAGE = "/module/platform/popularize-list.jsp";
    @RequestMapping(value = "/query-activation.do", method = RequestMethod.GET)
    public ModelAndView queryAppActiveInfo(AppActivationEntity appCondition,PlatformUser platformCondition)
    {
        Map map = new HashMap();

        UserDetailsEntity loginUser = (UserDetailsEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PlatformUser loginPlatformUser = loginUser.getUserEntity();
        boolean isAdmin = loginPlatformUser.getLoginName().equals("admin");

        List<PlatformUser> allPlatformUsersExceptAdmin = this.popularizeAnalysisService.queryAllPlatformUsersExceptAdmin();
        if (isAdmin)
        {
            map.put("allPlatformUsersExceptAdmin",allPlatformUsersExceptAdmin);
        }
        else {

            platformCondition.setPlatformID(loginPlatformUser.getPlatformID());
        }

        /**
         * 查询分析数据
         */
        List<AppActivationEntity> list = this.popularizeAnalysisService.queryAppActivationInfoByPlatformIDList(platformCondition, appCondition);

        /**
         * 查询app产品信息
         */
        List<AppInfoEntity> allAppProductList = this.popularizeAnalysisService.queryAllAppInfo();

        /**
         * 组合公式和名称字段
         */
        for (AppActivationEntity appActivationEntityVar : list)
        {
            for (PlatformUser platformUserVar:allPlatformUsersExceptAdmin)
            {
                boolean isThePlateformUser = appActivationEntityVar.getPlatformID().equals(platformUserVar.getPlatformID());
                if (isThePlateformUser)
                {
                    appActivationEntityVar.setIncremental(platformUserVar.getIncremental());
                    appActivationEntityVar.setScale(platformUserVar.getScale());
                    appActivationEntityVar.setPlateformName(platformUserVar.getPlatformName());
                    break;
                }
            }

            for (AppInfoEntity appInfoEntityVar :allAppProductList)
            {
                boolean isTheApp = appActivationEntityVar.getAppID().equals(appInfoEntityVar.getAppID());
                if (isTheApp)
                {
                    appActivationEntityVar.setAppName(appInfoEntityVar.getAppName());
                    break;
                }
            }
        }

        map.put("analysisInfoForPlatform",list);
        map.put("allAppProductList",allAppProductList);

        map.put("appCondition", appCondition);
        map.put("platformCondition", platformCondition);

        ModelAndView mv = new ModelAndView(QUERY_ACTIVATION_PAGE,map);
        return mv;
    }

    @RequestMapping(value = "/add-activation.do", method = RequestMethod.POST)
    public void addActivation(String json)
    {
        JSONObject jsonObject = JSONObject.fromObject(json);
        AppActivationEntity appActivationEntity = (AppActivationEntity)jsonObject.toBean(jsonObject, AppActivationEntity.class);

        AppInfoEntity appInfo = new AppInfoEntity();
        String appID = appActivationEntity.getAppID();
        String appName = appActivationEntity.getAppName();
        appInfo.setAppID(appID);
        appInfo.setAppName(appName);

        //清空多余字段
        appActivationEntity.setAppName(null);
        this.popularizeAnalysisService.addAppInfo(appInfo);
        this.popularizeAnalysisService.addAppActivationInfo(appActivationEntity);


    }


    private static final String LIST_USERS_PAGE = "/module/admin/user-access.jsp";
    @RequestMapping(value = "/query-system-users.do")
    public ModelAndView queryPlatformListExceptAdmin()
    {
        List<PlatformUser> platformUsers = this.popularizeAnalysisService.queryAllPlatformUsersExceptAdmin();
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("platformUsers",platformUsers);

        ModelAndView modelAndView = new ModelAndView(LIST_USERS_PAGE,param);

        return modelAndView;
    }

    private static final String EDIT_USERS_PAGE = "/module/admin/user-edit.jsp";
    @RequestMapping(value = "/edit-user.do")
    public ModelAndView editPlatformUserPage(PlatformUser platformUserParam)
    {

        PlatformUser editUser = this.popularizeAnalysisService.queryPlateformUserByPlatformCondition(platformUserParam);
        Map param = new HashMap();
        param.put("user",editUser);

        List<UsersRole> roleList = new ArrayList<UsersRole>();

        UsersRole[] roles = UsersRole.values();
        for (UsersRole role : roles) {
            if (role.equals(UsersRole.ROLE_ADMIN)) continue;

            roleList.add(role);
        }

        param.put("roleList", roleList);

        ModelAndView modelAndView = new ModelAndView(EDIT_USERS_PAGE,param);

        return modelAndView;
    }

    private static final String LIST_USERS_DO = "/popularize/query-system-users.do";
    @RequestMapping(value = "/update-user.do")
    public ModelAndView updatePlatFormUser(PlatformUser platformUserEdit)
    {
        this.popularizeAnalysisService.updatePlatformUser(platformUserEdit);
        ModelAndView modelAndView = new ModelAndView(LIST_USERS_DO);
        return modelAndView;
    }

    private static final String ADD_USER_PAGER = "/module/admin/user-save.jsp";
    @RequestMapping(value = "/show-adduser-page.do",method = RequestMethod.GET)
    public ModelAndView showAddUserPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        List<UsersRole> roleList = new ArrayList<>();

        UsersRole[] roles = UsersRole.values();
        for (UsersRole role : roles) {
            if (role.equals(UsersRole.ROLE_ADMIN)) continue;

            roleList.add(role);
        }

        modelAndView.addObject("roleList", roleList);
        modelAndView.setViewName(ADD_USER_PAGER);
        return modelAndView;
    }

    @RequestMapping(value = "/check-duplicate-loginname.do", method = RequestMethod.GET)
    public JQueryValidateRemoteValue checkDuplicateLoginName(String loginName)
    {
        boolean isDuplicateUser = this.popularizeAnalysisService.checkDuplicateLoginName(loginName);
        JQueryValidateRemoteValue remote = new JQueryValidateRemoteValue();
        remote.setValue(isDuplicateUser);
        return remote;
    }

    private static final String QUERY_SYSTEM_USERS = "/popularize/query-system-users.do";
    @RequestMapping(value = "/save-user.do")
    public ModelAndView saveUser(PlatformUser newPlatformUser)
    {
        ModelAndView modelAndView = new ModelAndView();
        this.popularizeAnalysisService.savePlatformUser(newPlatformUser);
        modelAndView.setViewName(QUERY_SYSTEM_USERS);
        return modelAndView;
    }
    
}
