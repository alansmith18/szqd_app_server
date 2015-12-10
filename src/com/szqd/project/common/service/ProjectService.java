package com.szqd.project.common.service;

import com.szqd.project.common.model.ProjectEntityDB;

import java.util.List;

/**
 * Created by like on 5/11/15.
 */
public interface ProjectService {
    /**
     * 查询项目列表
     * @return
     */
    public List<ProjectEntityDB> listProject();

    /**
     * 添加项目
     * @param project
     */
    public void saveOrUpdateProject(ProjectEntityDB project);
}
