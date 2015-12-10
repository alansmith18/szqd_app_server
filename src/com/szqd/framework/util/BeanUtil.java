package com.szqd.framework.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by like on 8/26/15.
 */
public class BeanUtil<BeanType> {
    private static final Logger LOGGER = Logger.getLogger(BeanUtil.class);

    public Object convertMapToBean(Object object, Class<BeanType> beanClass)
    {
        if (object instanceof List)
        {
            return this.convertMapListToBeanList((List) object, beanClass);
        }
        else {
            return this.convertOneMapListToOneBean((Map) object, beanClass);
        }
    }


    public List<BeanType> convertMapListToBeanList(List<Map> mapList, Class<BeanType> beanClass)
    {
        List<BeanType> resultList = new ArrayList<>();
        for (int i = 0; i < mapList.size(); i++) {
            Map map =  mapList.get(i);
            BeanType bean = this.convertOneMapListToOneBean(map, beanClass);
            resultList.add(bean);
        }
        return resultList;
    }

    /**
     * 将map转化为bean
     * @param map
     * @return
     */
    public BeanType convertOneMapListToOneBean(Map map, Class<BeanType> beanClass){
        try
        {
            List removeKey = new ArrayList<>();
            Set keySet = map.keySet();
            keySet.forEach(key->{ if (map.get(key)== null) removeKey.add(key);});
            removeKey.forEach(key -> map.remove(key));
            BeanType bean = beanClass.newInstance();

            BeanUtils.populate(bean,map);
            return bean;
        }
        catch(Exception e) {

            LOGGER.error("com.szqd.framework.util.BeanUtils.convertOneMapListToOneBean(mapVar:util.HashMap): BeanType",e);
            throw new RuntimeException("将map转化为bean出错");

        }

    }


}
