package com.szqd.project.red.gift.controller;

import com.szqd.framework.config.SystemConfigParameter;

import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.FeedbackEntity;
import com.szqd.project.red.gift.model.GiftEntityDB;
import com.szqd.project.red.gift.model.GiftEntityPOJO;
import com.szqd.project.red.gift.service.RedGiftService;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Created by like on 9/10/15.
 */
@CrossOrigin(allowCredentials = "false", exposedHeaders = {"Access-Control-Allow-Origin"})
@RestController
@RequestMapping(value = "/red-gift")
public class RedGiftController  {

    private RedGiftService redGiftService = null;

    public RedGiftService getRedGiftService() {
        return redGiftService;
    }

    public void setRedGiftService(RedGiftService redGiftService) {
        this.redGiftService = redGiftService;
    }

    private static final String GIRT_LIST_PAGE = "/module/red-gift/red-gift-list.jsp";

    @RequestMapping(value = "/list-gift.do")
    public ModelAndView listGift(GiftEntityPOJO condition, Pager pager) {

        pager.setCapacity(20);
        List<GiftEntityPOJO> giftList = redGiftService.listGiftForManagement(condition, pager);
        boolean hasNextPage = giftList.size() == pager.getCapacity();
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("list", giftList);

        modelAndView.addObject("queryCondition", condition);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("hasNextPage", hasNextPage);
        modelAndView.setViewName(GIRT_LIST_PAGE);
        return modelAndView;
    }

    private static final String SHOW_GIFT_EDIT_PAGE = "/module/red-gift/red-gift-add-or-save.jsp";

    @RequestMapping(value = "/show-gift-edit-page.do")
    public ModelAndView showGiftEditPage(GiftEntityDB edit) {
        ModelAndView modelAndView = new ModelAndView();
        GiftEntityPOJO redGift = redGiftService.loadGiftByIDFromDB(edit.getId());
        if (redGift == null) redGift = new GiftEntityPOJO();
        modelAndView.addObject("edit", redGift);
        modelAndView.setViewName(SHOW_GIFT_EDIT_PAGE);
        return modelAndView;
    }

    private static final String GIFT_LIST_PAGE_DO = "redirect:/red-gift/list-gift.do";

    @RequestMapping(value = "/save-gift.do", method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView saveGift(GiftEntityPOJO edit) {
        ModelAndView modelAndView = new ModelAndView();
        redGiftService.saveOrUpdateGift(edit);
        modelAndView.setViewName(GIFT_LIST_PAGE_DO);
        return modelAndView;
    }

    public void exchangeOrder(Integer srcID,Integer destID)
    {
        GiftEntityPOJO src = redGiftService.loadGiftByIDFromCache(srcID);
        GiftEntityPOJO dest = redGiftService.loadGiftByIDFromCache(destID);
        Integer srcOrderNo = src.getOrderNo();
        Integer destOrderNo = dest.getOrderNo();


    }

    @RequestMapping(value = "/list-gift-json.do")
    public List<GiftEntityPOJO> listGiftForJson(GiftEntityPOJO condition,Pager pager, HttpServletRequest request) {

        if (condition == null) condition = new GiftEntityPOJO();

        Integer formType = condition.getFormType();
        if (formType == null)
        {
            formType = 1;
            pager.setCapacity(15);
//            pager.setCapacity(pager.);
        }

        List<GiftEntityPOJO> giftList = redGiftService.fetchRedGiftContentListByPage(condition,pager);
        giftList.forEach(e -> e.setIcon(new StringBuilder().append(SystemConfigParameter.HTTP_IMAGE_DOMAIN()).append(request.getContextPath()).append(e.getIcon()).toString()));
        return giftList;
    }


    @RequestMapping(value = "/random-gift-json.do")
    public GiftEntityDB randomGiftForJson() {
        return this.redGiftService.fetchRandomRedGift();
    }

    @RequestMapping(value = "/get-hot-news.do",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getHotNews() throws Exception
    {
        return this.redGiftService.getHotNews();
    }

    @RequestMapping(value = "/get-sports-news.do",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getSportsNews() throws Exception
    {
        return this.redGiftService.getSportsNews();
    }

    @RequestMapping(value = "/get-more-news-url.do",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getMoreNewsURL()
    {
        return this.redGiftService.getMoreNewsURL();
    }

    private static final String FEEDBACK_PAGE = "/module/red-gift/feedback/feedback.jsp";
    @RequestMapping(value = "/uninstall-app.do")
    public ModelAndView uninstallApp(FeedbackEntity feedback)
    {
        ModelAndView view = new ModelAndView();
        view.addObject("feedback",feedback);
        view.setViewName(FEEDBACK_PAGE);
        return view;
    }
}
