package com.szqd.project.common.controller;

import com.szqd.framework.controller.SpringMVCController;
import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.*;
import com.szqd.project.common.service.MusicService;
import com.szqd.project.common.service.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by like on 5/26/15.
 */
@RequestMapping("/music")
@RestController
public class MusicController extends SpringMVCController
{
    private MusicService musicService = null;

    public MusicService getMusicService() {
        return musicService;
    }

    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
    }

    private ProjectService projectService = null;

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    private static final String LIST_MUSIC_PAGE = "/module/music/music-list.jsp";
    @RequestMapping("/list-music.do")
    public ModelAndView listMusic(MusicEntityDB music,Pager pager)
    {

        List<MusicEntityPOJO> musicList = musicService.listMusic(music,pager);
        boolean hasNextPage = musicList.size() == pager.getCapacity();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("musicList",musicList);
        modelAndView.addObject("queryCondition",music);
        modelAndView.addObject("pager",pager);
        modelAndView.addObject("hasNextPage",hasNextPage);
        modelAndView.setViewName(LIST_MUSIC_PAGE);

        return modelAndView;
    }


    private static final String ADD_MUSIC_PAGE = "/module/music/music-add-or-save.jsp";
    @RequestMapping("/add-music-page.do")
    public ModelAndView addMusicPage(Integer id)
    {
        MusicEntityPOJO edit = musicService.findMusicByID(id);
        if (edit == null)
        {
            edit = new MusicEntityPOJO();
        }

        List<ProjectStatusEnum> projectStatusEnumList = new ArrayList<>();
        projectStatusEnumList.add(ProjectStatusEnum.ENABLE);
        projectStatusEnumList.add(ProjectStatusEnum.DISABLE);

        List<PlatformEnum> platformEnumList = new ArrayList<>();
        platformEnumList.add(PlatformEnum.ANDROID);
        platformEnumList.add(PlatformEnum.IOS);
        platformEnumList.add(PlatformEnum.WINMOBILE);
        platformEnumList.add(PlatformEnum.OTHER);

        List<ProjectEntityDB> projectList = this.projectService.listProject();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("projectStatusEnumList",projectStatusEnumList);
        modelAndView.addObject("edit",edit);
        modelAndView.addObject("platformEnumList",platformEnumList);
        modelAndView.addObject("projectList",projectList);
        modelAndView.setViewName(ADD_MUSIC_PAGE);
        return modelAndView;
    }

    private static final String LIST_MUSIC_DO = "redirect:/music/list-music.do";
    @RequestMapping(value = "/save-music.do",method = RequestMethod.POST)
    public ModelAndView saveMusic(MusicEntityDB music)
    {
        this.musicService.saveOrUpdateMusic(music);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LIST_MUSIC_DO);
        return modelAndView;
    }
}
