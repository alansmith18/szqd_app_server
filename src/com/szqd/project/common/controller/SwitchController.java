package com.szqd.project.common.controller;

import com.szqd.project.common.model.switch_.SwitchEntityDB;
import com.szqd.project.common.service.SwitchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by like on 10/9/15.
 */
@RestController
@RequestMapping("/switch")
public class SwitchController
{
    private SwitchService switchService = null;

    public SwitchService getSwitchService() {
        return switchService;
    }

    public void setSwitchService(SwitchService switchService) {
        this.switchService = switchService;
    }

    private static final String LIST_SWITCH_PAGE = "/module/switch/switch-list.jsp";
    @RequestMapping("/list-switch.do")
    public ModelAndView listSwitch(SwitchEntityDB switchDB)
    {
        List<SwitchEntityDB> list = this.switchService.listSwitch(switchDB);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LIST_SWITCH_PAGE);
        modelAndView.addObject("list",list);
        modelAndView.addObject("queryCondition",switchDB);
        return modelAndView;
    }

    private static final String EDIT_SWITCH_PAGE = "/module/switch/switch-add-or-save.jsp";

    @RequestMapping("/create-or-replace-switch.do")
    public ModelAndView createOrReplaceSwitchPage(SwitchEntityDB switchEntityDB)
    {
        SwitchEntityDB switchDB = null;
        Long id = switchEntityDB.getId();
        if (id != null)
        {
            switchDB = this.switchService.getSwitchByID(id);
        }
        else{
            switchDB = new SwitchEntityDB();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("edit",switchDB);
        modelAndView.setViewName(EDIT_SWITCH_PAGE);
        return modelAndView;
    }

    @RequestMapping(value = "/save-switch.do",method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView createOrReplaceSwitch(SwitchEntityDB switchEntityDB)
    {
        this.switchService.createOrReplaceSwitch(switchEntityDB);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/switch/list-switch.do");
        return modelAndView;
    }

    @RequestMapping("/get-switch.do")
    public SwitchEntityDB getSwitchByID(Long id)
    {
        return this.switchService.getSwitchByID(id);
    }


}
