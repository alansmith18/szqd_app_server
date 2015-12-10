package com.szqd.project.mobile.lock.common;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by mac on 14-5-29.
 */
public class NullAwareBeanUtilsBean extends BeanUtilsBean
{


    @Override
    public void copyProperty(Object destBean, String name, Object value) throws IllegalAccessException, InvocationTargetException
    {
        if(value==null)return;

        try {
            super.copyProperty(destBean, name, value);
        } catch (Exception e)
        {
//            System.out.println("属性:"+name+" value:"+value);
//            e.printStackTrace();
        }  finally {
        }
    }
}
