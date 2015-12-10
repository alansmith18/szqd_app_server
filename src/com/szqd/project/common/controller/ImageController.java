package com.szqd.project.common.controller;

import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.image.ImageEntityPOJO;
import com.szqd.project.common.service.ImageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by like on 11/16/15.
 */

@RestController
@RequestMapping("/image-manager")
public class ImageController {

    private ImageService imageService = null;

    public ImageService getImageService() {
        return imageService;
    }

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    private static final String LIST_IMAGE_PAGE = "/function/common/image-list.jsp";
    @RequestMapping(value = "/image-list.do")
    public ModelAndView listImage(ImageEntityPOJO condition,Pager pager)
    {
        List<ImageEntityPOJO> list = this.imageService.queryImageList(condition,pager);
        boolean hasNextPage = list.size() == pager.getCapacity();
        ModelAndView view = new ModelAndView();
        view.addObject("list",list);
        view.addObject("queryCondition",condition);
        view.addObject("pager",pager);
        view.addObject("hasNextPage",hasNextPage);
        view.setViewName(LIST_IMAGE_PAGE);
        return view;
    }

    private static final String ADD_IMAGE_PAGE = "/function/common/image-add-or-save.jsp";
    @RequestMapping(value = "/upsert-image-page.do")
    public ModelAndView upsertImagePage(Long id)
    {
        ImageEntityPOJO image = this.imageService.queryImageByID(id);
        ModelAndView view = new ModelAndView();
        view.addObject("edit",image);
        view.setViewName(ADD_IMAGE_PAGE);
        return view;
    }

    private static final String LIST_IMAGE_DO = "/image-manager/image-list.do";
    @RequestMapping(value = "/upsert-image.do")
    public ModelAndView upsertImage(ImageEntityPOJO image)
    {
        this.imageService.upsertImage(image);
        ModelAndView view = new ModelAndView();
        view.setViewName(LIST_IMAGE_DO);
        return view;
    }

    @RequestMapping(value = "/fetch-image.do")
    public List<ImageEntityPOJO> fetchImage()
    {
        ImageEntityPOJO condition = new ImageEntityPOJO();
        condition.setIsQueryTodayImage(true);
        return this.imageService.queryImageList(condition,null,new String[]{"title","url","targetURL"});
    }
}
