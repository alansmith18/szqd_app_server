package com.szqd.framework.security;


import com.szqd.project.popularize.analysis.model.PlatformUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mac on 14-5-28.
 */
public class UserDetailsEntity implements UserDetails
{
    private PlatformUser userEntity = null;

    public PlatformUser getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(PlatformUser userEntity) {
        this.userEntity = userEntity;
    }

    private List<Menu> menuList = new ArrayList<Menu>();

    public List<Menu> getMenuList() {
        return menuList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        boolean isAdmin = userEntity.getLoginName().equals("admin");
        Integer roleID = userEntity.getRole();
        List<GrantedAuthorityEntity> authorityEntityList = new ArrayList<GrantedAuthorityEntity>();
        String roleString = null;
        if (UsersRole.ROLE_ADMIN.getRoleId().equals(roleID))
        {
            roleString = UsersRole.ROLE_ADMIN.toString();
            menuList.add(Menu.ACTIVE_INFO);
            menuList.add(Menu.ACCESS);
//            menuList.add(Menu.WALLPAPER);
            menuList.add(Menu.FEEDBACK);
//            menuList.add(Menu.THEME);
//            menuList.add(Menu.PROJECT);
//            menuList.add(Menu.RECOMMEND);
//            menuList.add(Menu.RECOMMEND_CATEGORY);
//            menuList.add(Menu.SKIN_MANAGMENT);
//            menuList.add(Menu.ADVERTISING);
//            menuList.add(Menu.MUSIC);
//            menuList.add(Menu.WEATHER_ENABLE);
//            menuList.add(Menu.REPORT_TIME_USEAGE);
//            menuList.add(Menu.REPORT_TIME);
//            menuList.add(Menu.REPORT_FREQUENCY);
            menuList.add(Menu.UNINSTALL_INFO);
//            menuList.add(Menu.PUSH_MESSAGE);
//            menuList.add(Menu.CLIENT_NOTICE);
//            menuList.add(Menu.VERSION);
            menuList.add(Menu.TRACKING_STATISTICS);
        }
        else if (UsersRole.ROLE_CLIENT_DEVELOPER.getRoleId().equals(roleID))
        {
            roleString = UsersRole.ROLE_CLIENT_DEVELOPER.toString();
            menuList.add(Menu.FEEDBACK);
        }
        else if (UsersRole.ROLE_ART_DEVELOPER.getRoleId().equals(roleID))
        {
            roleString = UsersRole.ROLE_ART_DEVELOPER.toString();
            menuList.add(Menu.WALLPAPER);
            menuList.add(Menu.SKIN_MANAGMENT);
            menuList.add(Menu.MUSIC);
            menuList.add(Menu.THEME);
        }
        else if (UsersRole.ROLE_ACTIVITY_CHANNEL.getRoleId().equals(roleID))
        {
            roleString = UsersRole.ROLE_ACTIVITY_CHANNEL.toString();
            menuList.add(Menu.ACTIVE_INFO);
        }
        else if (UsersRole.ROLE_OPERATIONS.getRoleId().equals(roleID))
        {
            roleString = UsersRole.ROLE_OPERATIONS.toString();
            menuList.add(Menu.PUSH_MESSAGE);
            menuList.add(Menu.CLIENT_NOTICE);
            menuList.add(Menu.ADVERTISING);
            menuList.add(Menu.RED_GIFT);
            menuList.add(Menu.SWITCH);
            menuList.add(Menu.AD_PLATFORM_LIST_AD);
            menuList.add(Menu.IMAGE_MANAGER);
            menuList.add(Menu.VERSION);
//            menuList.add(Menu.RECOMMEND);
//            menuList.add(Menu.RECOMMEND_CATEGORY);
        }

        else if (UsersRole.ROLE_AD_PLATFORM_ADVERTISER.getRoleId().equals(roleID))
        {
            roleString = UsersRole.ROLE_AD_PLATFORM_ADVERTISER.toString();
            menuList.add(Menu.AD_PLATFORM_LIST_AD);
        }
        else if (UsersRole.ROLE_AD_PLATFORM_CHANNEL.getRoleId().equals(roleID))
        {
            roleString = UsersRole.ROLE_AD_PLATFORM_CHANNEL.toString();
            menuList.add(Menu.AD_PLATFORM_LIST_AD);
        }

        authorityEntityList.add(new GrantedAuthorityEntity(roleString));;

        return authorityEntityList;
    }

    @Override
    public String getPassword() {
        return userEntity.getLoginPwd();
    }

    @Override
    public String getUsername() {
        return userEntity.getLoginName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
