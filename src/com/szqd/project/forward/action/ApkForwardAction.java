package com.szqd.project.forward.action;

import com.szqd.framework.controller.SpringMVCController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Properties;

/**
 * Created by like on 4/7/15.
 */

@RequestMapping("/download")
public class ApkForwardAction extends SpringMVCController
{

    private Properties redirectConfig = null;

    public Properties getRedirectConfig() {
        return redirectConfig;
    }

    public void setRedirectConfig(Properties redirectConfig) {
        this.redirectConfig = redirectConfig;
    }

    @RequestMapping(value = "/apk/{fileName}.apk")
    public ModelAndView downloadApk(@PathVariable("fileName") String fileName)
    {
        String path = redirectConfig.getProperty(fileName);
        StringBuilder redirectPath = new StringBuilder("redirect:");
        redirectPath.append(path);

        ModelAndView modelAndView = new ModelAndView(redirectPath.toString());

        return modelAndView;
    }



}
