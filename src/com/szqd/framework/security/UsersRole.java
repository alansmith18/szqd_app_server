package com.szqd.framework.security;

public enum UsersRole
{
//    ROLE_USER("ROLE_USER","普通用户",0),
    ROLE_ADMIN("ROLE_ADMIN","管理员",1),ROLE_ACTIVITY_CHANNEL("ROLE_ACTIVITY_CHANNEL","运营商用户",2)
    ,ROLE_CLIENT_DEVELOPER("ROLE_CLIENT_DEVELOPER","客户端开发",3),ROLE_ART_DEVELOPER("ROLE_ART_DEVELOPER","美术",4)
    ,ROLE_OPERATIONS("ROLE_OPERATIONS","运营",5),ROLE_AD_PLATFORM_ADVERTISER("ROLE_AD_PLATFORM_ADVERTISER","广告平台广告主",6)
    ,ROLE_AD_PLATFORM_CHANNEL("ROLE_AD_PLATFORM_CHANNEL","广告平台渠道商",7);

    private String role = null;
    private Integer roleId = 0;
    private String roleText = null;

    private UsersRole(String role,String roleText,int roleId) {
        this.role = role;
        this.roleId = roleId;
        this.roleText = roleText;
    }

    public Integer getRoleId()
    {
        return this.roleId;
    }

    public String getRole()
    {

        return this.role;
    }

    public String getRoleText()
    {
        return this.roleText;
    }

    @Override
    public String toString() {
        return this.role;
    }
}