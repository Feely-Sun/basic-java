package com.basic.leanring.java.indicator;

import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.codahale.metrics.*;

/**
 * @author sunzihan
 * @version $Id: IndicatorReporter.java V 0.1 3/15/17 15:55 sunzihan EXP $
 */
public class IndicatorReporter  extends ScheduledReporter{

    private static final Logger logger                 = LoggerFactory
            .getLogger(IndicatorReporter.class);

    /** 度量器日志 */
    private static final Logger METRIC_LOGGER          = LoggerFactory
            .getLogger("SDCLIENT-INDICATOR-METRICS");

    /** 缓存效益日志 */
    private static final Logger CACHE_HIT_RATIO_LOGGER = LoggerFactory
            .getLogger("SDCLIENT-INDICATOR-CACHE-HIT-RATIO");

    /** 打印统计报表是否启动 */
    private AtomicBoolean isStarted              = new AtomicBoolean(false);

    /** 统计线程是否启动，保证任务只启动一次 */
    private AtomicBoolean       isThreadStarted        = new AtomicBoolean(false);

    private IndicatorReporter(MetricRegistry registry, String name, MetricFilter filter,
                              TimeUnit rateUnit, TimeUnit durationUnit) {
        super(registry, name, filter, rateUnit, durationUnit);
    }

    /**
     * 构造 {@link IndicatorReporter} 的构造器。
     *
     * @param name reporter 的名称
     * @param registry 注册机构
     * @return 构造器实例
     */
    public static Builder forRegistry(String name, MetricRegistry registry) {
        return new Builder(name, registry);
    }

    /**
     * 报告者构建器。
     */
    public static class Builder {
        private final MetricRegistry registry;
        private final String         name;
        private TimeUnit             rateUnit;
        private TimeUnit             durationUnit;
        private MetricFilter         filter;

        private Builder(String name, MetricRegistry registry) {
            this.name = name;
            this.registry = registry;
            this.rateUnit = TimeUnit.SECONDS;
            this.durationUnit = TimeUnit.MILLISECONDS;
            this.filter = MetricFilter.ALL;
        }

        /**
         * 转换频率统计的时间单位。
         *
         * @param rateUnit 时间单位
         * @return {@code this}
         */
        public Builder convertRatesTo(TimeUnit rateUnit) {
            this.rateUnit = rateUnit;
            return this;
        }

        /**
         * 转换周期统计的时间单位。
         *
         * @param durationUnit 时间单位
         * @return {@code this}
         */
        public Builder convertDurationsTo(TimeUnit durationUnit) {
            this.durationUnit = durationUnit;
            return this;
        }

        /**
         * 只报告过滤出来的度量值。
         *
         * @param filter 过滤器 {@link MetricFilter}
         * @return {@code this}
         */
        public Builder filter(MetricFilter filter) {
            this.filter = filter;
            return this;
        }

        /**
         * build
         *
         * @return
         */
        public IndicatorReporter build() {
            return new IndicatorReporter(registry, name, filter, rateUnit, durationUnit);
        }
    }

    /**
     * @see com.codahale.metrics.ScheduledReporter#report(java.util.SortedMap, java.util.SortedMap, java.util.SortedMap, java.util.SortedMap, java.util.SortedMap)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void report(SortedMap<String, Gauge> gauges, SortedMap<String, Counter> counters,
                       SortedMap<String, Histogram> histograms, SortedMap<String, Meter> meters,
                       SortedMap<String, Timer> timers) {

        if (!isStarted()) {
            return;
        }

        logCacheHitRatio(meters);

        for (Map.Entry<String, Gauge> entry : gauges.entrySet()) {
            logGauge(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Counter> entry : counters.entrySet()) {
            logCounter(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Histogram> entry : histograms.entrySet()) {
            logHistogram(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Meter> entry : meters.entrySet()) {
            logMeter(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Timer> entry : timers.entrySet()) {
            logTimer(entry.getKey(), entry.getValue());
        }
    }

    private void logCacheHitRatio(SortedMap<String, Meter> meters) {
        if (CACHE_HIT_RATIO_LOGGER.isInfoEnabled()) {

            Meter calMeter = meters.get(IndicatorMetrics.CAL_METER_NAME);
            Meter cacheHitByPreCalMeter = meters
                    .get(IndicatorMetrics.CACHE_HIT_BY_PRECAL_METER_NAME);
            Meter cacheHitByTransInMeter = meters
                    .get(IndicatorMetrics.CACHE_HIT_BY_TRANS_IN_METER_NAME);

            long calCount = calMeter.getCount();

            CACHE_HIT_RATIO_LOGGER.info(String.format("pre_cal=%.4f",
                    calPercentage(cacheHitByPreCalMeter.getCount(), calCount)));

            CACHE_HIT_RATIO_LOGGER.info(String.format("trans_in=%.4f",
                    calPercentage(cacheHitByTransInMeter.getCount(), calCount)));

        }

    }

    private double calPercentage(long numerator, long denominator) {
        if (denominator == 0) {
            return 0.0;
        } else {
            return (double) numerator / denominator;
        }
    }

    private void logTimer(String name, Timer timer) {
        final Snapshot snapshot = timer.getSnapshot();
        if (METRIC_LOGGER.isInfoEnabled()) {
            METRIC_LOGGER.info(String
                    .format("TIMER=%s, count=%s, min=%s, max=%s, mean=%s, stddev=%s, median=%s, "
                                    + "p75=%s, p95=%s, p98=%s, p99=%s, p999=%s, mean_rate=%s, m1=%s, m5=%s, "
                                    + "m15=%s",
                            name, timer.getCount(), convertDuration(snapshot.getMin()),
                            convertDuration(snapshot.getMax()), convertDuration(snapshot.getMean()),
                            convertDuration(snapshot.getStdDev()), convertDuration(snapshot.getMedian()),
                            convertDuration(snapshot.get75thPercentile()),
                            convertDuration(snapshot.get95thPercentile()),
                            convertDuration(snapshot.get98thPercentile()),
                            convertDuration(snapshot.get99thPercentile()),
                            convertDuration(snapshot.get999thPercentile()),
                            convertRate(timer.getMeanRate()), convertRate(timer.getOneMinuteRate()),
                            convertRate(timer.getFiveMinuteRate()),
                            convertRate(timer.getFifteenMinuteRate())));
        }
    }

    private void logMeter(String name, Meter meter) {
        if (METRIC_LOGGER.isInfoEnabled()) {
            METRIC_LOGGER.info(String.format(
                    "METER=%s, count=%s, mean_rate=%s, m1=%s, m5=%s, m15=%s", name, meter.getCount(),
                    convertRate(meter.getMeanRate()), convertRate(meter.getOneMinuteRate()),
                    convertRate(meter.getFiveMinuteRate()), convertRate(meter.getFifteenMinuteRate())));
        }
    }

    private void logHistogram(String name, Histogram histogram) {
        final Snapshot snapshot = histogram.getSnapshot();
        if (METRIC_LOGGER.isInfoEnabled()) {
            METRIC_LOGGER
                    .info(String.format("HISTOGRAM=%s, count=%s, min=%s, max=%s, mean=%s, stddev=%s, "
                                    + "median=%s, p75=%s, p95=%s, p98=%s, p99=%s, p999=%s",
                            name, histogram.getCount(), snapshot.getMin(), snapshot.getMax(),
                            snapshot.getMean(), snapshot.getStdDev(), snapshot.getMedian(),
                            snapshot.get75thPercentile(), snapshot.get95thPercentile(),
                            snapshot.get98thPercentile(), snapshot.get99thPercentile(),
                            snapshot.get999thPercentile()));
        }
    }

    private void logCounter(String name, Counter counter) {
        if (METRIC_LOGGER.isInfoEnabled()) {
            METRIC_LOGGER.info(String.format("COUNTER=%s, count=%s", name, counter.getCount()));

        }
    }

    private void logGauge(String name, Gauge<?> gauge) {
        if (METRIC_LOGGER.isInfoEnabled()) {
            METRIC_LOGGER.info(String.format("GAUGE=%s, value=%s", name, gauge.getValue()));
        }
    }

    /**
     * @see com.codahale.metrics.ScheduledReporter#start(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public void start(long period, TimeUnit unit) {
        if (!isThreadStarted.get()) {
            super.start(period, unit);
            isThreadStarted.compareAndSet(false, true);
        }

        isStarted.compareAndSet(false, true);
        if (logger.isInfoEnabled()) {
            logger.info("Start indicator metrics reporter...");
        }
    }

    /**
     * @see com.codahale.metrics.ScheduledReporter#stop()
     */
    @Override
    public void stop() {
        isStarted.compareAndSet(true, false);
        if (logger.isInfoEnabled()) {
            logger.info("Stop indicator metrics reporter...");
        }
    }

    /**
     * 判断是否启动。
     *
     * @return 启动返回 {@code true}
     */
    public boolean isStarted() {
        return this.isStarted.get();
    }


}

