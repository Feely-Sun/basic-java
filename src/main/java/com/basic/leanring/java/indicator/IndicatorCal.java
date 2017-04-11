package com.basic.leanring.java.indicator;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.basic.leanring.java.indicator.context.IndicatorValueContext;
import com.basic.leanring.java.indicator.model.CommonCtuEvent;

/**
 * @author sunzihan
 * @version $Id: IndicatorCal.java V 0.1 3/15/17 16:03 sunzihan EXP $
 */
public  abstract class IndicatorCal<IN,OUT extends IndicatorResult<?>> {

    protected static final Logger logger = LoggerFactory.getLogger(IndicatorCal.class);

    /** CTU 通用事件 */
    protected final CommonCtuEvent event;

    /** 进行计算的指标名称，仅仅针对单指标计算 */
    protected String                        indicatorName;

    /** 指标计算参数 */
    protected final IN                      params;

//    /** 指标 ID 仓储 */
//    protected AllIndicatorIdentities        allIndicatorIdentities;
//
//    /** 指标领域模型仓储 */
//    protected AllIndicators                 allIndicators;
//
//    /** 指标计算服务（远程调用） */
//    protected IndicatorCalculateService     indicatorCalculateService;
//
//    /** 本地数据点 */
//    protected LocalDataPointRepository      localDataPointRepository;
//
//    /** 指标客户端配置资源 */
//    protected IndicatorClientConfigResource indicatorClientConfigResource;

    /**
     * 构造指标计算过程。
     *
     * @param event 统一事件
     * @param params 计算指标需要的参数
     */
    public IndicatorCal(CommonCtuEvent event, IN params) {
        this.event = event;
        this.params = params;
    }

    /**
     * 指标计算初始化。
     *
     * @return 计算过程自身
     */
    public abstract IndicatorCal<IN, OUT> init();

    /**
     * 进行具体的指标计算动作。
     *
     * @return 指标计算结果
     */
    public abstract OUT cal() throws Exception;

    /**
     * 调用远程服务进行指标计算。
     *
     * @return 远程计算结果
     */
    protected abstract OUT calByRemote();

    /**
     * 对指标计算的结果进行处理，比如缓存。
     *
     * @param calResult 指标计算结果
     */
    protected OUT handleResult(OUT calResult) {
        if (event != null) {
            handleInnerIndicatorCache(calResult);
            handleCache(calResult);
        } else {
            logger.warn("Cannot handle indicator result,null event,indicatorName="
                    + StringUtils.defaultIfEmpty(indicatorName, "<multiple>") + ",params="
                    + dumpParams());
        }
        return calResult;
    }

    /**
     * 处理指标的缓存。
     * @param calResult 指标计算结果
     */
    protected abstract void handleCache(OUT calResult);

    /**
     * 处理内嵌指标的缓存。
     * @param calResult 指标计算结果
     */
    private void handleInnerIndicatorCache(OUT calResult) {
        Map<Integer, Object> otherResult = calResult.getOtherResult();
        if (!CollectionUtils.isEmpty(otherResult)) {
            IndicatorValueContext.addSomeNewValues(event.getId(), otherResult);
        }
    }

    protected int getSnapshotPolicy() {
//        int policy = IndicatorSnapshotConstant.SNAPSHOT_POLICY_SAVE;
//        try {
//            Indicator indicator = allIndicators.getIndicatorByName(indicatorName);
//            if (indicator != null) {
//                policy = indicator.getSnapshotPolicy();
//            }
//        } catch (Exception e) {
//            logger.warn("getIndicatorByName,encounter exception,name=" + indicatorName, e);
//        }

        return 1;
    }

    /**
     * dumpParams
     *
     * @return
     */
    private String dumpParams() {
        String dump;
        if (params instanceof Object[]) {
            dump = Arrays.toString((Object[]) params);
        } else {
            dump = String.valueOf(params);
        }
        return dump;
    }

    // ----------------------- Getters and setters
    /**
     * setAllIndicatorIdentities
     *
     * @param allIndicatorIdentities
     */
//    public void setAllIndicatorIdentities(AllIndicatorIdentities allIndicatorIdentities) {
//        this.allIndicatorIdentities = allIndicatorIdentities;
//    }
//
//    /**
//     * setAllIndicators
//     *
//     * @param allIndicators
//     */
//    public void setAllIndicators(AllIndicators allIndicators) {
//        this.allIndicators = allIndicators;
//    }
//
//    /**
//     * setIndicatorCalculateService
//     *
//     * @param indicatorCalculateService
//     */
//    public void setIndicatorCalculateService(IndicatorCalculateService indicatorCalculateService) {
//        this.indicatorCalculateService = indicatorCalculateService;
//    }
//
//    /**
//     * setLocalDataPointRepository
//     *
//     * @param localDataPointRepository
//     */
//    public void setLocalDataPointRepository(LocalDataPointRepository localDataPointRepository) {
//        this.localDataPointRepository = localDataPointRepository;
//    }

    /**
     * getEvent
     *
     * @return event
     */
    public CommonCtuEvent getEvent() {
        return event;
    }

    /**
     * getParams
     *
     * @return 一个指标的计算过程：
     */
    public IN getParams() {
        return params;
    }

    /**
     * setIndicatorClientConfigResource
     *
     * @param indicatorClientConfigResource
     */
//    public void setIndicatorClientConfigResource(IndicatorClientConfigResource indicatorClientConfigResource) {
//        this.indicatorClientConfigResource = indicatorClientConfigResource;
//    }
}

