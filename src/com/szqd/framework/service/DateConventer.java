package com.szqd.framework.service;

import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 14-6-9.
 */
public class DateConventer implements Converter<String,Date>
{
    private static Logger logger = Logger.getLogger(DateConventer.class);

    @Override
    public Date convert(String source) {
        if(!StringUtils.hasLength(source)) {
            //①如果source为空 返回null
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dest = dateFormat.parse(source);
            return dest;
        } catch (ParseException e) {
            logger.debug(e);

        } finally {
        }
        return null;
    }
}
