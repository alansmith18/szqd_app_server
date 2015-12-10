package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.MusicEntityDB;
import com.szqd.project.common.model.MusicEntityPOJO;

import java.util.List;

/**
 * Created by like on 5/26/15.
 */
public interface MusicService {

    /**
     * 音乐列表
     * @param music
     * @param pager
     * @return
     */
    public List<MusicEntityPOJO> listMusic(MusicEntityDB music, Pager pager);


    /**
     * 根据id返回
     * @param musicID
     * @return
     */
    public MusicEntityPOJO findMusicByID(Integer musicID);

    /**
     * 保存音乐
     * @param music
     */
    public void saveOrUpdateMusic(MusicEntityDB music);
}
