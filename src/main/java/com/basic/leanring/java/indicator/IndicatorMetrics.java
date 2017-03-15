package com.basic.leanring.java.indicator;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.basic.leanring.java.indicator.model.IndicatorValue;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.springframework.beans.factory.InitializingBean;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

/**
 * @author sunzihan
 * @version $Id: IndicatorMetrics.java V 0.1 3/15/17 15:54 sunzihan EXP $
 */
public class IndicatorMetrics implements InitializingBean, Observer {

    private static final Logger logger                           = LoggerFactory
            .getLogger(IndicatorMetrics.class);

    // ---------------------------------------------------------------------- 各个统计指标的名称
    public static final String          CAL_TIMER_NAME                   = "cal_timer";
    public static final String          IO_CAL_TIMER_NAME                = "io_cal_timer";
    public static final String          CAL_METER_NAME                   = "cal_meter";
    public static final String          CACHE_HIT_BY_PRECAL_METER_NAME   = "cache_hit_meter_by_precal";
    public static final String          CACHE_HIT_BY_TRANS_IN_METER_NAME = "cache_hit_meter_by_trans_in";

    public static final String          TRANS_OUT_STAT_METER_NAME        = "trans_out_stat_meter";
    public static final String          TRANS_OUT_METER_NAME             = "trans_out_meter";
    public static final String          TRANS_IN_METER_NAME              = "trans_in_meter";

    public static final String          PRE_CAL_METER_NAME               = "pre_cal_meter";

    /** 度量纬度注册机构 */
    private static final MetricRegistry METRICS                          = new MetricRegistry();

    /** 指标计算测量器 */
    private static final Meter CAL_METER;

    /** 指标计算计时器 */
    private static final Timer CAL_TIMER;

    /** IO指标计算计时器 */
    private static final Timer          IO_CAL_TIMER;

    /** 预计算带来的缓存命中测量器 */
    private static final Meter          CACHE_HIT_BY_PRECAL_METER;

    /** 透传带来的缓存命中测量器 */
    private static final Meter          CACHE_HIT_BY_TRANS_IN_METER;

    /** 指标透传流出的指标计数*/
    private static final Meter          TRANS_OUT_STAT_METER;

    /**指标透传流出写入缓存的指标计数**/
    private static final Meter          TRANS_OUT_METER;

    /**指标透传流入写入缓存的指标计数**/
    private static final Meter          TRANS_IN_METER;

    /** 指标预计计数器 */
    private static final Meter          PRE_CAL_METER;

    static {
        CAL_TIMER = METRICS.timer(CAL_TIMER_NAME);
        IO_CAL_TIMER = METRICS.timer(IO_CAL_TIMER_NAME);
        CAL_METER = METRICS.meter(CAL_METER_NAME);
        CACHE_HIT_BY_PRECAL_METER = METRICS.meter(CACHE_HIT_BY_PRECAL_METER_NAME);
        CACHE_HIT_BY_TRANS_IN_METER = METRICS.meter(CACHE_HIT_BY_TRANS_IN_METER_NAME);
        TRANS_OUT_STAT_METER = METRICS.meter(TRANS_OUT_STAT_METER_NAME);
        TRANS_OUT_METER = METRICS.meter(TRANS_OUT_METER_NAME);
        TRANS_IN_METER = METRICS.meter(TRANS_IN_METER_NAME);
        PRE_CAL_METER = METRICS.meter(PRE_CAL_METER_NAME);
    }

    /**
     * 带有计时器的指标计算。
     *
     * @param cal 指标计算过程
     * @return 指标计算结果
     * @throws Exception
     */
    public static <T extends IndicatorResult<?>> T doCalWithTimer(IndicatorCal<?, T> cal)
            throws Exception {
        if (isStatistics()) {
            Timer.Context ctx = CAL_TIMER.time();
            try {
                T result = cal.init().cal();
                return result;
            } finally {
                ctx.stop();
            }
        } else {
            return cal.init().cal();
        }
    }

    /**
     * io操作
     *
     * @param action
     * @return
     * @throws Exception
     */
    public static <T extends IndicatorResult<?>> T doIOWithTimer(IOAction<T> action)
            throws Exception {
        if (isStatistics()) {
            Timer.Context ctx = IO_CAL_TIMER.time();
            try {
                T result = action.action();
                return result;
            } finally {
                ctx.stop();
            }
        } else {
            return action.action();
        }
    }

    /**
     * 根据统计值 需要透传 的指标数
     * @param n
     */
    public static void markTransOutStat(long n) {
        if (isStatistics()) {
            TRANS_OUT_STAT_METER.mark(n);
        }
    }

    /**
     * 透传流出 的指标数
     * @param n
     */
    public static void markTransOut(long n) {
        if (isStatistics()) {
            TRANS_OUT_METER.mark(n);
        }
    }

    /**
     * 透传流入的指标数
     * @param n
     */
    public static void markTransIn(long n) {
        if (isStatistics()) {
            TRANS_IN_METER.mark(n);
        }
    }

    /**
     * 发生缓存命中，能够区分透传带来的缓存命中以及预计算带来的缓存命中。
     *
     * @param indicatorValue 指标计算值
     */
    public static void markCacheHit(IndicatorValue indicatorValue) {
        if (isStatistics()) {
            if (indicatorValue.isPreCal()) {
                markCacheHitByPreCal();
            } else if (indicatorValue.isTransIn()) {
                markCacheHitByTransIn();
            }
        }
    }

    /**
     * 发生一次指标计算。
     */
    public static void markCal() {
        if (isStatistics()) {
            CAL_METER.mark();
        }
    }

    /**
     * 发生 n 次指标计算。
     *
     * @param n 指标计算次数
     */
    public static void markCal(long n) {
        if (isStatistics()) {
            CAL_METER.mark(n);
        }
    }

    /**
     * 发生一次预计算带来的缓存命中。
     */
    public static void markCacheHitByPreCal() {
        if (isStatistics()) {
            CACHE_HIT_BY_PRECAL_METER.mark();
        }
    }

    /**
     * 发生一次透传带来的缓存命中。
     */
    public static void markCacheHitByTransIn() {
        if (isStatistics()) {
            CACHE_HIT_BY_TRANS_IN_METER.mark();
        }
    }

    /**
     * 预计算了 n 个指标。
     *
     * @param n 预计算指标的个数
     */
    public static void markPreCal(long n) {
        if (isStatistics()) {
            PRE_CAL_METER.mark(n);
        }
    }

    /**
     * 判断是否开启了统计。
     *
     * @return {@code boolean}
     */
    public static boolean isStatistics() {
        IndicatorMetrics metrics = getInstance();
        return true;
    }

    /**
     * 获取到单例。
     *
     * @return 指标计算度量。
     */
    public static IndicatorMetrics getInstance() {
        return Singleton.SINGLETON;
    }

    /** 单例 */
    private static final class Singleton {
        private static final IndicatorMetrics SINGLETON = new IndicatorMetrics();
    }

//    /** 指标客户端配置资源 */
//    private IndicatorClientConfigResource indicatorClientConfigResource;

    /** 统计报告器 */
    private IndicatorReporter             indicatorReporter;

    /** 统计周期 */
    private int                           period = 30;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {

            indicatorReporter = IndicatorReporter.forRegistry("indicator-metrics", METRICS).build();

//            if (indicatorClientConfigResource.statistics()) {
//                indicatorReporter.start(period, TimeUnit.SECONDS);
//            }
//
//            indicatorClientConfigResource.addObserver(this);
//
//            if (logger.isInfoEnabled()) {
//                logger.info("Init indicator metrics finished,statistics="
//                        + indicatorClientConfigResource.statistics());
//            }

        } catch (Exception e) {
            logger.error("Init indicator metrics failed", e);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        synchronized (this) {
//            if (arg == DrmAction.CHANGE_STATISTICS_STATE) {
//
//                // DRM开启 + 未启动
//                if (indicatorClientConfigResource.statistics() && !indicatorReporter.isStarted()) {
//                    indicatorReporter.start(period, TimeUnit.SECONDS);
//
//                    // DRM关闭 + 已启动
//                } else if (!indicatorClientConfigResource.statistics()
//                        && indicatorReporter.isStarted()) {
//                    indicatorReporter.stop();
//                }
//            }

            System.out.println("upudate.....");
        }
    }

    // ------------------- Getters and setters

//    public void setIndicatorClientConfigResource(IndicatorClientConfigResource indicatorClientConfigResource) {
//        this.indicatorClientConfigResource = indicatorClientConfigResource;
//    }

    public void setPeriod(int period) {
        this.period = period;
    }
}

