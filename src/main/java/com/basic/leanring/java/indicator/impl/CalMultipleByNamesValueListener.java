package com.basic.leanring.java.indicator.impl;

import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.basic.leanring.java.indicator.IndicatorValueListener;
import com.basic.leanring.java.indicator.context.IndicatorValueContext;
import com.basic.leanring.java.indicator.model.MultiResult;
import com.google.common.collect.Sets;

/**
 * @author sunzihan
 * @version $Id: CalMultipleByNamesValueListener.java V 0.1 3/15/17 16:09 sunzihan EXP $
 */
public class CalMultipleByNamesValueListener implements IndicatorValueListener<MultiResult<String>> {
    private static final Logger logger = LoggerFactory
            .getLogger(CalMultipleByNamesValueListener.class);


    @Override
    public void onGetValue(String eventId, MultiResult<String> indicatorResult) {
        Map<String, Object> indicatorValues = indicatorResult.getResult();
        if (CollectionUtils.isEmpty(indicatorValues)) {
            logger.warn("Cannot process empty indicatorValues, eventId=" + eventId);
            return;
        }

        Map<String, Integer> nameToId = indicatorResult.getName2Ids();
        if (CollectionUtils.isEmpty(nameToId)) {
            return;
        }

        Set<Integer> ids = Sets.newHashSet();
        for (Map.Entry<String, Object> entry : indicatorValues.entrySet()) {
            String name = entry.getKey();
            Integer indicatorId = nameToId.get(name);
            if (indicatorId != null && indicatorId > 0) {
                ids.add(indicatorId);
            }
        }
        IndicatorValueContext.useSomeValues(eventId, ids);
    }

    /**
     * 获取监听器实例
     *
     * @return 监听器实例
     */
    public static CalMultipleByNamesValueListener getInstance() {
        return Singleton.singleton;
    }

    /** 单例 */
    private static final class Singleton {
        private static final CalMultipleByNamesValueListener singleton = new CalMultipleByNamesValueListener();
    }
}

