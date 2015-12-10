package com.szqd.project.common.model.push;

/**
 * Created by like on 7/8/15.
 */
public enum MessageTypeEnum {

    MANUALLY(0,"人工",0,0l), OS_SECURITY(1,"系统安全",20,100l),SOCIALLY(2,"通讯社交",30,60l), VIDEO(3,"影视视听",3,30l), READERS(4,"新闻阅读",20,30l), LIFE_STYLE(5,"生活休闲",10,100l)
    ,THEME(6,"主题壁纸",20,30l),OFFICE(7,"办公商务",10,60l),PHOTOGRAPHY(8,"摄影摄像",12,30l),SHOPPING(9,"购物优惠",20,30l),MAP_TRAVELLING(10,"地图旅游",15,20l),EDUCATION(11,"教育学习",5,30l)
    ,FINANCIAL(12,"金融理财",5,8l),HEALTH(13,"健康医疗",5,10l),GAME_ROLE_PLAY(14,"角色扮演",5,30l),GAME_CASUAL_PUZZLE(15,"休闲益智",5,30l),
    GAME_ACTION_ADVENTURE(16,"动作冒险",5,30l),GAME_ONLINE_GAME(17,"网络游戏",5,30l),GAME_ATHLETIC(18,"体育竞技",5,30l),
    GAME_FLIGHT_SHOOTTING(19,"飞行射击",5,30l),GAME_BUSINESS_STRATEGE(20,"经营策略",5,30l),GAME_CHESS_WORLD(21,"棋牌天地",5,30l),
    GAME_CHILDREN_GAME(22,"儿童游戏",5,30l);

    private Integer id = null;
    private String name = null;
    private Integer interestCount = null;
    private Long interestTime = null;//以秒为单位

    MessageTypeEnum(Integer id, String name, Integer interestCount, Long interestTime) {
        this.id = id;
        this.name = name;
        this.interestCount = interestCount;
        this.interestTime = interestTime*60l*1000l;//将分转化为毫秒
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getInterestCount() {
        return interestCount;
    }

    public Long getInterestTime() {
        return interestTime;
    }

    public static MessageTypeEnum getNameByID(Integer id)
    {
        if (OS_SECURITY.getId().equals(id))
        {
            return OS_SECURITY;
        }
        else if (READERS.getId().equals(id))
        {
            return READERS;
        }
        else if (LIFE_STYLE.getId().equals(id))
        {
            return LIFE_STYLE;
        }
        else if (VIDEO.getId().equals(id))
        {
            return VIDEO;
        }
        else if (SOCIALLY.getId().equals(id))
        {
            return SOCIALLY;
        }
        else if (MANUALLY.getId().equals(id))
        {
            return MANUALLY;
        }
        else if (THEME.getId().equals(id))
        {
            return THEME;
        }
        else if (OFFICE.getId().equals(id))
        {
            return OFFICE;
        }
        else if (PHOTOGRAPHY.getId().equals(id))
        {
            return PHOTOGRAPHY;
        }
        else if (SHOPPING.getId().equals(id))
        {
            return SHOPPING;
        }
        else if (MAP_TRAVELLING.getId().equals(id))
        {
            return MAP_TRAVELLING;
        }
        else if (EDUCATION.getId().equals(id))
        {
            return EDUCATION;
        }
        else if (FINANCIAL.getId().equals(id))
        {
            return FINANCIAL;
        }
        else if (HEALTH.getId().equals(id))
        {
            return HEALTH;
        }
        else if (GAME_ROLE_PLAY.getId().equals(id))
        {
            return GAME_ROLE_PLAY;
        }
        else if (GAME_CASUAL_PUZZLE.getId().equals(id))
        {
            return GAME_CASUAL_PUZZLE;
        }
        else if (GAME_ACTION_ADVENTURE.getId().equals(id))
        {
            return GAME_ACTION_ADVENTURE;
        }
        else if (GAME_ONLINE_GAME.getId().equals(id))
        {
            return GAME_ONLINE_GAME;
        }
        else if (GAME_ATHLETIC.getId().equals(id))
        {
            return GAME_ATHLETIC;
        }
        else if (GAME_FLIGHT_SHOOTTING.getId().equals(id))
        {
            return GAME_FLIGHT_SHOOTTING;
        }
        else if (GAME_BUSINESS_STRATEGE.getId().equals(id))
        {
            return GAME_BUSINESS_STRATEGE;
        }
        else if (GAME_CHESS_WORLD.getId().equals(id))
        {
            return GAME_CHESS_WORLD;
        }
        else if (GAME_CHILDREN_GAME.getId().equals(id))
        {
            return GAME_CHILDREN_GAME;
        }

        return null;
    }
}
