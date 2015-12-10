package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.push.NoticeFromClientPojo;
import com.szqd.project.common.model.push.PushMessageDB;
import com.szqd.project.common.model.push.PushMessagePojo;

import java.util.List;

/**
 * Created by like on 7/8/15.
 */
public interface PushMessageService {

    /**
     * 获取多个推送消息
     * @param userID
     * @param projectID
     * @param interestJson
     * @return
     */
    public List<PushMessageDB> fetchManyPushMessage(String userID,Integer projectID,String interestJson);

    /**
     * 获取推送消息
     * @param userID
     * @param projectID
     * @param interestJson
     * @return
     */
    public PushMessageDB fetchPushMessage(String userID,Integer projectID,String interestJson);

    /**
     * 通过id获取推送消息
     * @param id
     * @return
     */
    public PushMessagePojo fetchPushMessageByID(Integer id);

    /**
     * 添加推送消息
     * @param pushMessage
     */
    public void savePushMessage(PushMessageDB pushMessage);


    /**
     * 减少所有的推送排序序号
     */
    public void reducePushOrder();

    /**
     * 减少指定id号得推送的前面得推送的全部排序序号
     */
    public void reducePushOrderBeforeByID(Integer id);

    /**
     * 置顶推送消息
     * @param id
     */
    public void topPushMessageByID(Integer id);

    /**
     * 通过兴趣查找兴趣和人工推送的推送消息索引
     * @param appCategoryIDList
     * @return
     */
    public List<Long> queryPushMessageIDListByAppCategoryIDList(List<Integer> appCategoryIDList, Integer projectID);


    /**
     * 根据分页罗列推送消息列表
     * @param queryCondition
     * @param pager
     * @return
     */
    public List<PushMessagePojo> listPushMessage(PushMessagePojo queryCondition,Pager pager);

    /**
     * 通过兴趣查找兴趣和人工推送
     * @param appCategoryIDList
     * @return
     */
//    public List<Long> queryPushMessageIndexByAppCategoryID(List<Integer> appCategoryIDList, Integer projectID);

    /**
     * 根据用户id,获取用户推送消息的key
     * @param userID
     * @return
     */
    public String getUserPushMessageStackKey(String userID,Integer projectID);

    /**
     * 生成用户的推送堆栈
     * @param userID
     * @param userPushMessageList
     */
    public void generateUserPushMessageStack(String userID,Integer projectID,List<Long> userPushMessageList);

    /**
     * 通过用户id获取推送消息ID
     * @param userID
     * @return
     */
    public Long getUserPushMessageFromUserStackByUserID(String userID, Integer projectID);

    /**
     * 通过json获取兴趣的类别id
     * @param interestJson
     * @return
     */
    public List<Integer> getInterestFromJson(String interestJson);

    public PushMessageDB changePushURLToMyServerURL(PushMessageDB pushMessageDB);

    /**
     * 从redis,通过推送id获取推送消息
     * @param pushMessageID
     * @return
     */
    public PushMessageDB getPushMessageFromRedisByID(Long pushMessageID);

    /**
     * 更新今天的推送数量
     * @param pushID
     */
    public void upsetPushCountForToday(Long pushID);

    public void testUserData(String userID,String descType);

    /**
     * 预测明天的推送数量
     * @return
     */
    public Long forecastPushCountForTomorrow();

    /**
     * 接受用户安装的app信息
     * @param json
     */
    public void receiveInstallApp(String json);

    /**
     * 保存客户端的通知
     * @param json
     */
    public void saveNoticeFromClientJson(String json);

    /**
     * 查询客户端的通知
     * @param pager
     * @param condition
     * @return
     */
    public List<NoticeFromClientPojo> queryClientNoticeByCondition(Pager pager,NoticeFromClientPojo condition);

    /**
     * 根据app名称查询app分类
     * @param appName
     * @return
     */
    public Integer queryCategoryByAppName(String appName);

    public void testSave(Object push,String key);

    public Object testGet(String key);

}
