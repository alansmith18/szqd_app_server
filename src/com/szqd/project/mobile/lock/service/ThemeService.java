package com.szqd.project.mobile.lock.service;

import com.szqd.framework.model.Pager;
import com.szqd.project.mobile.lock.model.ThemeEntity;
import com.szqd.project.mobile.lock.model.ThemeEntityDB;

import java.util.List;

/**
 * Created by like on 5/5/15.
 */
public interface ThemeService {

    /**
     * 根据条件查询主题
     * @param theme
     * @param pager
     * @param orderType
     * @return
     */
    public List<ThemeEntity> queryThemes(ThemeEntityDB theme,Pager pager,Integer orderType);

    /**
     * 添加主题
     * @param theme
     */
    public void addTheme(ThemeEntityDB theme);
}
