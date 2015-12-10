package com.szqd.project.common.service;

import com.szqd.project.common.model.switch_.SwitchEntityDB;

import java.util.List;

/**
 * Created by like on 10/8/15.
 */
public interface SwitchService {

    /**
     * 罗列开关
     * @param switchDB
     * @return
     */
    public List<SwitchEntityDB> listSwitch(SwitchEntityDB switchDB);

    public void createOrReplaceSwitch(SwitchEntityDB switchEntityDB);

    public SwitchEntityDB getSwitchByID(Long id);

}
