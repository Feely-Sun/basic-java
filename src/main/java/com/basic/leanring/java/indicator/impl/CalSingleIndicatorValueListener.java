package com.basic.leanring.java.indicator.impl;

import com.basic.leanring.java.indicator.IndicatorValueListener;
import com.basic.leanring.java.indicator.context.IndicatorValueContext;
import com.basic.leanring.java.indicator.model.SingleCalResult;

/**
 * @author sunzihan
 * @version $Id: CalSingleIndicatorValueListener.java V 0.1 3/15/17 16:07 sunzihan EXP $
 */
public class CalSingleIndicatorValueListener implements IndicatorValueListener<SingleCalResult> {
    @Override
    public void onGetValue(String eventId, SingleCalResult indicatorResult) {
        // 从 IndicatorValueRef 获取值，则是业务节点使用该指标
        int indicatorId = indicatorResult.getIndicatorId();

        if (indicatorId != -1) {
            IndicatorValueContext.useValue(eventId, indicatorId);
        }
    }

    /**
     * 获取监听器单例。
     *
     * @return 监听器单例
     */
    public static IndicatorValueListener<SingleCalResult> getInstance() {
        return Singleton.singleton;
    }

    private static final class Singleton {
        private static final CalSingleIndicatorValueListener singleton = new CalSingleIndicatorValueListener();
    }

}

