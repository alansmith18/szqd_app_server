package com.szqd.framework.model;

public enum Scale
{
    SCALE16$9(16.9f),SCALE4$3(4.3f),SCALE5$3(5.3f);
    private float d = 0;

    Scale(float d) {
        this.d = d;
    }

    public float getValue()
    {
        return this.d;
    }

}
