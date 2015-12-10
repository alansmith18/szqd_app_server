package com.szqd.project.mobile.lock.model;

import java.sql.Timestamp;

/**
 * Created by like on 3/19/15.
 */
public class AppAnalysisEntity extends AbstractAppAnalysisEntity
{
    public static final String ENTITY_NAME = "AppAnalysisEntity";


    public Integer getHowLongUseCategoryByHowLongUse()
    {
        //毫秒转化为秒
        Long howLongUse = this.getHowLongUse()/1000l;
        if (howLongUse < 4l)
        {
            return HowLongUseEnum.S1$S3.getId();
        }
        else if (howLongUse <= 10 && howLongUse >= 4 )
        {
            return HowLongUseEnum.S4$S10.getId();
        }
        else if (howLongUse <= 30  && howLongUse > 10 )
        {
            return HowLongUseEnum.S11$S30.getId();
        }
        else if (howLongUse <= 60  && howLongUse > 30  )
        {
            return HowLongUseEnum.S31$S60.getId();
        }
        else if (howLongUse <= 180  && howLongUse > 60  )
        {
            return HowLongUseEnum.M1$M3.getId();
        }
        else if (howLongUse <= 600  && howLongUse > 180  )
        {
            return HowLongUseEnum.M3$M10.getId();
        }
        else if (howLongUse <= 1800 && howLongUse > 600 )
        {
            return HowLongUseEnum.M10$M30.getId();
        }
        else if (howLongUse > 1800 )
        {
            return HowLongUseEnum.M30OVER.getId();
        }
        else {
            return null;
        }
    }

}
