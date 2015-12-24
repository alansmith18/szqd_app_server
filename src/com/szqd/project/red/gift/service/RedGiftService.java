package com.szqd.project.red.gift.service;

import com.szqd.framework.model.Pager;
import com.szqd.project.red.gift.model.GiftEntityPOJO;

import java.util.List;

/**
 * Created by like on 9/10/15.
 */
public interface RedGiftService
{

    /**
     * 随机红包
     * @return
     */
    public GiftEntityPOJO fetchRandomRedGift();

    /**
     * 获取某一页的红包内容列表
     * @param pager
     * @return
     */
    public List<GiftEntityPOJO> fetchRedGiftContentListByPage(GiftEntityPOJO condition,Pager pager);

    /**
     * 通过条件获取到红包的分页数据
     *
     * @param condition
     * @param pager
     * @return
     */
    public List<GiftEntityPOJO> listGiftForManagement(GiftEntityPOJO condition, Pager pager);

    /**
     * 保存或更新红包
     * @param gift
     */
    public void saveOrUpdateGift(GiftEntityPOJO gift);

    /**
     * 通过ID查询数据库中的红包
     *
     * @param id
     * @return
     */
    public GiftEntityPOJO loadGiftByIDFromDB(Long id);

    /**
     * 通过ID查询缓存中的红包
     *
     * @param id
     * @return
     */
    public GiftEntityPOJO loadGiftByIDFromCache(long id);

    /**
     * 获取热门新闻
     * @return
     * @throws Exception
     */
    public Object getHotNews() throws Exception;

    /**
     * 获取体育新闻
     * @return
     * @throws Exception
     */
    public Object getSportsNews() throws Exception;

    /**
     * 获取更多新闻
     * @return
     */
    public String getMoreNewsURL();



}
