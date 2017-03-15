package com.basic.leanring.java.indicator.impl;

import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.basic.leanring.java.indicator.IndicatorValueListener;
import com.basic.leanring.java.indicator.context.IndicatorValueContext;
import com.basic.leanring.java.indicator.model.MultiResult;

/**
 * @author sunzihan
 * @version $Id: CalMultipleByIdsValueListener.java V 0.1 3/15/17 16:06 sunzihan EXP $
 */
public class CalMultipleByIdsValueListener implements IndicatorValueListener<MultiResult<Integer>> {

    private static final Logger logger = LoggerFactory
            .getLogger(CalMultipleByIdsValueListener.class);


    @Override
    public void onGetValue(String eventId, MultiResult<Integer> indicatorResult) {
        Map<Integer, Object> indicatorValues = indicatorResult.getResult();
        if (CollectionUtils.isEmpty(indicatorValues)) {
            logger.warn("Cannot process empty indicator values,eventId=" + eventId);
            return;
        }

        // 只要获取批量计算的结果就认为使用了所有计算得到的指标值
        IndicatorValueContext.useSomeValues(eventId, indicatorValues.keySet());
    }

    /**
     * 获取监听器单例。
     *
     * @return 监听器单例
     */
    public static CalMultipleByIdsValueListener getInstance() {
        return Singleton.singleton;
    }

    /** 单例 */
    private static final class Singleton {
        private static final CalMultipleByIdsValueListener singleton = new CalMultipleByIdsValueListener();
    }
}

