package com.szqd.framework.security;


import java.util.Arrays;
import java.util.List;

/**
 * Created by mac on 14-5-28.
 */
public enum Menu
{
    ACTIVE_INFO(Arrays.asList(UsersRole.ROLE_ACTIVITY_CHANNEL,UsersRole.ROLE_ADMIN),"app激活信息","/resource/images/nav/application.png","/popularize/query-activation.do"),
    ACCESS(Arrays.asList(UsersRole.ROLE_ADMIN),"权限管理","/resource/images/icon/key_green.png","/popularize/query-system-users.do"),
    WALLPAPER(Arrays.asList(UsersRole.ROLE_ART_DEVELOPER,UsersRole.ROLE_ADMIN),"壁纸管理","/resource/images/nav/application.png","/wallpaper/list-wallpapertype.do"),
    FEEDBACK(Arrays.asList(UsersRole.ROLE_ADMIN,UsersRole.ROLE_ART_DEVELOPER),"意见反馈","/resource/images/nav/application.png","/feedback/find-feedback-pager.do"),
    VERSION(Arrays.asList(UsersRole.ROLE_ADMIN),"版本控制","/resource/images/nav/application.png","/version/version-list.do"),
    THEME(Arrays.asList(UsersRole.ROLE_ADMIN,UsersRole.ROLE_ART_DEVELOPER),"主题管理","/resource/images/nav/application.png","/theme/theme-list.do"),
    PROJECT(Arrays.asList(UsersRole.ROLE_ADMIN,UsersRole.ROLE_CLIENT_DEVELOPER),"项目管理","/resource/images/nav/application.png","/project/list-project.do"),
    RECOMMEND(Arrays.asList(UsersRole.ROLE_ADMIN,UsersRole.ROLE_CLIENT_DEVELOPER),"精品推荐","/resource/images/nav/application.png","/recommend/recommend-list.do"),
    RECOMMEND_CATEGORY(Arrays.asList(UsersRole.ROLE_ADMIN,UsersRole.ROLE_CLIENT_DEVELOPER),"精品分类","/resource/images/nav/application.png","/recommend/list-category.do"),
    SKIN_MANAGMENT(Arrays.asList(UsersRole.ROLE_ADMIN,UsersRole.ROLE_ART_DEVELOPER),"皮肤管理","/resource/images/nav/application.png","/skin/list-skin.do"),
    ADVERTISING(Arrays.asList(UsersRole.ROLE_ADMIN,UsersRole.ROLE_CLIENT_DEVELOPER),"广告管理","/resource/images/nav/application.png","/advertising/list-advertising.do"),
    MUSIC(Arrays.asList(UsersRole.ROLE_ADMIN,UsersRole.ROLE_ART_DEVELOPER),"音乐管理","/resource/images/nav/application.png","/music/list-music.do"),
    WEATHER_ENABLE(Arrays.asList(UsersRole.ROLE_ADMIN,UsersRole.ROLE_CLIENT_DEVELOPER),"开关","/resource/images/nav/application.png","/weather/is-enable-wt.do"),
    REPORT_TIME_USEAGE(Arrays.asList(UsersRole.ROLE_ADMIN),"使用时长","/resource/images/nav/application.png","/app-analysis/count-time-seage-for-oneapp.do"),
    REPORT_TIME(Arrays.asList(UsersRole.ROLE_ADMIN),"使用时段","/resource/images/nav/application.png","/app-analysis/count-time-for-oneapp.do"),
    REPORT_FREQUENCY(Arrays.asList(UsersRole.ROLE_ADMIN),"使用频率","/resource/images/nav/application.png","/app-analysis/count-frequency-for-oneapp.do"),
    UNINSTALL_INFO(Arrays.asList(UsersRole.ROLE_ADMIN),"卸载信息","/resource/images/nav/application.png","/feedback/uninstall-list.do"),
    PUSH_MESSAGE(Arrays.asList(UsersRole.ROLE_ADMIN,UsersRole.ROLE_OPERATIONS),"推送消息","/resource/images/nav/application.png","/push-message/list-push-message.do"),
    CLIENT_NOTICE(Arrays.asList(UsersRole.ROLE_ADMIN,UsersRole.ROLE_OPERATIONS),"通知信息","/resource/images/nav/application.png","/push-message/list-client-notice.do"),
    RED_GIFT(Arrays.asList(UsersRole.ROLE_OPERATIONS),"红包","/resource/images/nav/application.png","/red-gift/list-gift.do"),
    SWITCH(Arrays.asList(UsersRole.ROLE_OPERATIONS),"开关","/resource/images/nav/application.png","/switch/list-switch.do"),
    AD_PLATFORM_LIST_AD(Arrays.asList(UsersRole.ROLE_AD_PLATFORM_ADVERTISER,UsersRole.ROLE_AD_PLATFORM_CHANNEL,UsersRole.ROLE_OPERATIONS),"广告平台列表","/resource/images/nav/application.png","/ad-alliance/list-advertising.do"),
    IMAGE_MANAGER(Arrays.asList(UsersRole.ROLE_OPERATIONS),"图片管理","/resource/images/nav/application.png","/image-manager/image-list.do"),
    TRACKING_STATISTICS(Arrays.asList(UsersRole.ROLE_ADMIN),"跟踪统计","/resource/images/nav/application.png","/tracking-statistics/list-event.do");


    private List<UsersRole> roles;
    private String menuName;
    private String menuImage;
    private String menuURL;

    Menu(List<UsersRole> roles, String menuName, String menuImage, String menuURL) {
        this.roles = roles;
        this.menuName = menuName;
        this.menuImage = menuImage;
        this.menuURL = menuURL;
    }


    public List<UsersRole> getRoles() {
        return roles;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public String getMenuURL() {
        return menuURL;
    }




}
