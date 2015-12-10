package com.szqd.project.flashlightmaster.action;

import com.szqd.framework.controller.SpringMVCController;
import com.szqd.framework.util.URLConnectionUtils;
import com.szqd.project.flashlightmaster.service.FlashLightService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by like on 5/19/15.
 */
@RequestMapping("/flashlight")
@RestController
public class FlashLightController extends SpringMVCController
{
    private FlashLightService flashLightService = null;

    public FlashLightService getFlashLightService() {
        return flashLightService;
    }

    public void setFlashLightService(FlashLightService flashLightService) {
        this.flashLightService = flashLightService;
    }

    private static final Integer JOKE_TYPE_TEXT = 0;
    private static final Integer JOKE_TYPE_IMAGE = 1;

    @RequestMapping(value = "/joke-list.do",produces = "application/json" )
    public String joke(String page,Integer jokeType)throws Exception
    {
        String url = "http://japi.juhe.cn/joke/content/list.from";
        if (JOKE_TYPE_IMAGE.equals(jokeType))
        {
            url = "http://japi.juhe.cn/joke/img/list.from";
        }

        Map<String,String> param = new HashMap<>();
        param.put("key","1516918830734568ca265bfdb1bde110");
        param.put("time", String.valueOf(Calendar.getInstance().getTimeInMillis()).substring(0,10));
        param.put("pagesize","20");
        param.put("page",page);
        param.put("sort","desc");
        String result = URLConnectionUtils.send(url, param, "GET", "UTF-8");
//        Gson gson = new Gson();
//        HashMap<String,Object> map = gson.fromJson(result,HashMap.class);
//        LinkedHashMap resultData = (LinkedHashMap)map.get("result");
//        resultData.get("data");
        return result;
    }


}
