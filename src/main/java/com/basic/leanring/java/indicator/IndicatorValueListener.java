package com.basic.leanring.java.indicator;

/**
 * @author sunzihan
 * @version $Id: IndicatorValueListener.java V 0.1 3/15/17 15:47 sunzihan EXP $
 */
public interface IndicatorValueListener<T extends IndicatorResult<?>> {

    /**
     * 响应获取指标计算值事件。
     *
     * @param eventId 事件 ID
     * @param indicatorResult 指标计算结果
     */
    public void onGetValue(String eventId, T indicatorResult);
}
