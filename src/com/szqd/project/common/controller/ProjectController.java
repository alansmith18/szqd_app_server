package com.szqd.project.common.controller;

import com.szqd.framework.controller.SpringMVCController;
import com.szqd.project.common.model.ProjectEntityDB;
import com.szqd.project.common.service.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by like on 5/11/15.
 */
@RestController
@RequestMapping("/project")
public class ProjectController extends SpringMVCController
{
    public ProjectService projectService = null;

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    private static final String PROJECT_PAGE = "/module/project/project-list.jsp";
    @RequestMapping(value = "/list-project.do")
    public ModelAndView listProject()
    {
        List<ProjectEntityDB> projectList = this.projectService.listProject();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("projectList",projectList);
        modelAndView.setViewName(PROJECT_PAGE);
        return modelAndView;
    }

    private static final String LIST_PROJECT_DO = "redirect:/project/list-project.do";
    @RequestMapping(value = "/upsert-project.do",method = RequestMethod.POST)
    public ModelAndView addProject(ProjectEntityDB project)
    {
        this.projectService.saveOrUpdateProject(project);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LIST_PROJECT_DO);
        return modelAndView;

    }

}
