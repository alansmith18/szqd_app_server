package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.image.ImageEntityDB;
import com.szqd.project.common.model.image.ImageEntityPOJO;

import java.util.List;

/**
 * Created by like on 11/16/15.
 */
public interface ImageService {

    public List<ImageEntityPOJO> queryImageList(ImageEntityPOJO condition,Pager page,String...fields);

    public ImageEntityPOJO queryImageByID(Long id);

    public void upsertImage(ImageEntityDB image);

}
