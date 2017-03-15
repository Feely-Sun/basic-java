package com.basic.leanring.java.indicator.impl;

import com.basic.leanring.java.indicator.MetaInfo;

/**
 * @author sunzihan
 * @version $Id: IndicatorClientMeta.java V 0.1 3/15/17 15:37 sunzihan EXP $
 */
public class IndicatorClientMeta implements MetaInfo {

    /** 是否进行指标计算 - 方法级别*/
    private boolean methodIsCal;

    @Override
    public boolean isAvailable() {
        return methodIsCal;
    }

    /**
     * 判断是否进行指标计算。
     *
     * @return 需要返回 {@code true} 否则返回 {@code false}
     */
    public boolean isUsingCal() {
        return methodIsCal;
    }

    // ----------------------- Getters and setters

    public boolean isMethodIsCal() {
        return methodIsCal;
    }

    public void setMethodIsCal(boolean methodIsCal) {
        this.methodIsCal = methodIsCal;
    }
}

