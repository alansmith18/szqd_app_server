package com.szqd.project.common.controller;

import com.szqd.framework.config.SystemConfigParameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by like on 9/6/15.
 */
@RestController
public class ImageRedirectController
{
    public static final String OPERATION = "redirect:";

    @RequestMapping(value = {"/red-gift/{imgName}.jpg","/red-gift/{imgName}.png","/wallpaper/{imgName}.jpg","/wallpaper/{imgName}.png","/advertising/{imgName}.jpg","/advertising/{imgName}.png","/push-message/{imgName}.jpg","/push-message/{imgName}.png"},method = RequestMethod.GET)
    public ModelAndView redirectImage(@PathVariable String imgName,HttpServletRequest request)
    {
        StringBuilder url = new StringBuilder();
        url.append(OPERATION).append(SystemConfigParameter.HTTP_IMAGE_DOMAIN()).append(request.getRequestURI());
        ModelAndView modelAndView = new ModelAndView(url.toString());
        return modelAndView;
    }
}
