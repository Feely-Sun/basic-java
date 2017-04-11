package com.basic.leanring.java.indicator.model;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.basic.leanring.java.indicator.IndicatorResult;
import com.basic.leanring.java.indicator.IndicatorValueListener;
import com.basic.leanring.java.indicator.impl.CalMultipleByIdsValueListener;
import com.basic.leanring.java.indicator.impl.CalMultipleByNamesValueListener;
import com.basic.leanring.java.indicator.impl.CalSingleIndicatorValueListener;

/**
 * @author sunzihan
 * @version $Id: IndicatorResultRef.java V 0.1 3/15/17 15:42 sunzihan EXP $
 */
public class IndicatorResultRef<T extends IndicatorResult<?>> {
    private static final Logger logger = LoggerFactory
            .getLogger(IndicatorResultRef.class);

    /**
     * 通用事件
     */
    private final CommonCtuEvent event;

    /**
     * 指标计算结果
     */
    private final T indicatorResult;

    /**
     * 指标计算监听器
     */
    private final IndicatorValueListener<T> listener;

    /**
     * 构建单指标计算结果引用。
     *
     * @param event           通用事件
     * @param indicatorResult 单指标运算结果
     * @return 单指标运算结果引用
     */
    public static IndicatorResultRef<SingleCalResult> refOfSingle(CommonCtuEvent event,
                                                                  SingleCalResult indicatorResult) {
        return new IndicatorResultRef<SingleCalResult>(event, indicatorResult,
                CalSingleIndicatorValueListener.getInstance());
    }

    /**
     * 构建多指标计算结果引用。
     *
     * @param event           通用事件
     * @param indicatorResult 多指标运算结果
     * @return 多指标运算结果引用
     */
    public static IndicatorResultRef<MultiResult<Integer>> refOfMultipleByIds(CommonCtuEvent event,
                                                                              MultiResult<Integer> indicatorResult) {
        return new IndicatorResultRef<MultiResult<Integer>>(event, indicatorResult,
                CalMultipleByIdsValueListener.getInstance());
    }

    /**
     * 构建多指标计算结果引用。
     *
     * @param event           通用事件
     * @param indicatorResult 多指标运算结果
     * @return 多指标运算结果引用
     */
    public static IndicatorResultRef<MultiResult<Integer>> refOfPreByIds(CommonCtuEvent event,
                                                                         MultiResult<Integer> indicatorResult) {
        return new IndicatorResultRef<MultiResult<Integer>>(event, indicatorResult,
                new FakeIndicatorValueListener<Integer>());
    }

    /**
     * 构建多指标计算结果引用。
     *
     * @param event           通用事件
     * @param indicatorResult 多指标运算结果
     * @return 多指标运算结果引用
     */
    public static IndicatorResultRef<MultiResult<String>> refOfPreByNames(CommonCtuEvent event,
                                                                          MultiResult<String> indicatorResult) {
        return new IndicatorResultRef<MultiResult<String>>(event, indicatorResult,
                new FakeIndicatorValueListener<String>());
    }

    /**
     * 构建多指标计算结果引用。
     *
     * @param event           事件 ID
     * @param indicatorResult 多指标运算结果
     * @return 多指标运算结果引用
     */
    public static IndicatorResultRef<MultiResult<String>> refOfMultipleByNames(CommonCtuEvent event,
                                                                               MultiResult<String> indicatorResult) {
        return new IndicatorResultRef<MultiResult<String>>(event, indicatorResult,
                CalMultipleByNamesValueListener.getInstance());
    }

    /**
     * 构建指标计算值引用。
     *
     * @param event           事件ID
     * @param indicatorResult 指标计算结果
     */
    public IndicatorResultRef(CommonCtuEvent event, T indicatorResult,
                              IndicatorValueListener<T> listener) {
        if (indicatorResult == null) {
            throw new IllegalArgumentException("indicatorResult cannot be null, event=" + event);
        }

        this.event = event;
        this.indicatorResult = indicatorResult;
        this.listener = listener;
    }

    /**
     * 获取指标计算的结果对象。
     *
     * @return 指标计算的结果对象
     */
    public T get() {
        if (event != null) {
            this.listener.onGetValue(event.getId(), indicatorResult);
        } else {
            logger.warn("Cannot fire [get] event on indicatorResultRef,null CommonCtuEvent,result="
                    + indicatorResult);
        }
        return this.indicatorResult;
    }

    /**
     * 获取指标计算值。
     *
     * @return 指标计算值
     */
    public Object getValue() {
        IndicatorResult<?> result = this.get();
        return result.getResult();
    }

    @Override
    public String toString() {
        return indicatorResult.toString();
    }

    /**
     * fake listener
     *
     * @author alex
     * @version $Id: IndicatorResultRef.java, v 0.1 2015年8月27日 下午2:25:16 alex Exp $
     */
    static class FakeIndicatorValueListener<T> implements IndicatorValueListener<MultiResult<T>> {

        @Override
        public void onGetValue(String eventId, MultiResult<T> indicatorResult) {
        }

    }
}
