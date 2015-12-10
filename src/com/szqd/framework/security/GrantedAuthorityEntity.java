package com.szqd.framework.security;


import org.springframework.security.core.GrantedAuthority;

/**
 * Created by mac on 14-5-28.
 */

public class GrantedAuthorityEntity implements GrantedAuthority
{

    private String authority = null;
    public GrantedAuthorityEntity(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority()
    {
        return authority;
    }
}

