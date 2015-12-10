package com.szqd.project.mobile.lock.controller;

import com.szqd.framework.controller.SpringMVCController;
import com.szqd.framework.model.FileEntity;
import com.szqd.framework.model.Pager;
import com.szqd.project.mobile.lock.model.ThemeEntity;
import com.szqd.project.mobile.lock.model.ThemeEntityDB;
import com.szqd.project.mobile.lock.service.ThemeService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * Created by like on 5/5/15.
 */
@RestController
@RequestMapping("/theme")
public class ThemeController extends SpringMVCController
{
    private ThemeService themeService = null;

    public ThemeService getThemeService() {
        return themeService;
    }

    public void setThemeService(ThemeService themeService) {
        this.themeService = themeService;
    }

    private static final String THEME_LIST_PAGE = "/module/theme/theme-list.jsp";
    @RequestMapping(value = "/theme-list.do")
    public ModelAndView queryTheme(ThemeEntityDB theme,Pager pager,Integer orderType)
    {

        ModelAndView modelAndView = new ModelAndView();
        List<ThemeEntity> list = this.themeService.queryThemes(theme, pager, orderType);
        modelAndView.addObject("themeList", list);
        boolean hasNextPage = list.size() == pager.getCapacity();
        modelAndView.addObject("hasNextPage",hasNextPage);
        modelAndView.addObject("queryCondition",theme);
        modelAndView.setViewName(THEME_LIST_PAGE);
        return modelAndView;

    }

    private static final String THEME_LIST_DO = "redirect:/theme/theme-list.do";
    @RequestMapping(value = "/add-theme.do",method = RequestMethod.POST)
    public ModelAndView addTheme(ThemeEntityDB theme) {
        this.themeService.addTheme(theme);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(THEME_LIST_DO);
        return modelAndView;
    }

    private static final String SUBPATH = "/theme";
    @RequestMapping(value = "/upload-theme.do",method = RequestMethod.POST)
    public FileEntity upload(MultipartFile uploadFile,HttpServletRequest request)
    {
        FileEntity fileEntity = this.saveUploadFile(uploadFile,request,SUBPATH,null,null,null,null);
        return fileEntity;

    }

    @RequestMapping(value = "/theme-download.do")
    @ResponseBody
    public FileSystemResource downloadTheme(String url)
    {
        URI uri = URI.create(url);
        File file = new File(uri);
        FileSystemResource f = new FileSystemResource(file);
        return f;
    }

}
