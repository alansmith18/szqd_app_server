package com.szqd.framework.util;

import java.util.List;

/**
 * Created by like on 8/13/15.
 */
public class MongoDbUtils {
    public static String changeListToMongodbConditionString(List list)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (list != null && !list.isEmpty())
        {
            for (int i = 0; i < list.size(); i++) {
                Object o =  list.get(i);
                if (i > 0)
                {
                    sb.append(",");
                }
                sb.append(o);
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
