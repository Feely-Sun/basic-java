package com.basic.leanring.java.indicator.context;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.basic.leanring.java.indicator.IndicatorMetrics;
import com.basic.leanring.java.indicator.model.IndicatorSnapshotConstant;
import com.basic.leanring.java.indicator.model.IndicatorValue;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author sunzihan
 * @version $Id: IndicatorValueCacheItem.java V 0.1 3/15/17 15:49 sunzihan EXP $
 */
public class IndicatorValueCacheItem implements Iterable<Map.Entry<Integer, IndicatorValue>>  {

    private static final Logger logger = LoggerFactory.getLogger(IndicatorValueCacheItem.class);

    /** 指标ID => 指标值 */
    private ConcurrentMap<Integer, IndicatorValue> cachedValues = Maps.newConcurrentMap();

    /**预加载失败的指标列表:主要提供条件降权使用*/
    private Set<Integer> failurePreIndicatorSet = new HashSet<>();

    private CalStat remoteCalTimer;

    private CalStat localCalTimer;

    private CalStat calTimer;

    /**
     * 判断缓存条目是否为空。
     *
     * @return 为空返回 {@code true}，否则返回 {@code false}
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(cachedValues);
    }

    /**
     * 使用一个指标值，缓存中该指标值更改为已使用。
     *
     * @param indicatorId 指标 ID
     * @return 更改成功返回 {@code true}，否则返回 {@code false}
     */
    public boolean useValue(Integer indicatorId) {
        if (isIllegalKey(indicatorId)) {
            logger.warn("Cannot use value,illegal indicatorId=" + indicatorId);
            return false;
        }

        IndicatorValue indicatorValue = cachedValues.get(indicatorId);
        if (indicatorValue != null) {
            indicatorValue.toUsed();
        }

        return true;
    }

    /**
     * 使用一批指标值。
     *
     * @param indicatorIds 指标ID集合
     * @return 更改成功返回 {@code true}，否则返回 {@code false}
     */
    public boolean useSomeValues(Set<Integer> indicatorIds) {
        if (CollectionUtils.isEmpty(indicatorIds)) {
            logger.warn("Cannot use empty indicators");
            return false;
        }

        for (Integer id : indicatorIds) {
            if (isIllegalKey(id)) {
                continue;
            }
            IndicatorValue indicatorValue = cachedValues.get(id);
            if (indicatorValue != null) {
                indicatorValue.toUsed();
            }
        }

        return true;
    }

    /**
     * 消费某个事件相关的所有新计算的指标值。
     *
     * @return 使用的指标值
     */
    public Map<Integer, Object> consumeNewValues() {
        Map<Integer, Object> values = Maps.newHashMap();

        for (Map.Entry<Integer, IndicatorValue> entry : this) {
            Integer indicatorId = entry.getKey();
            IndicatorValue indicatorValue = entry.getValue();
            if (isLegalKey(indicatorId)) {
                synchronized (indicatorValue) {
                    if (indicatorValue.isNew()) {
                        values.put(indicatorId, indicatorValue.getValue());
                        indicatorValue.remNew();
                    }
                }
            }
        }

        return values;
    }

    /**
     * 消费新快照中需要存储的指标值
     *
     * @return
     */
    public Map<Integer, Object> consumeSnapshot() {
        Map<Integer, Object> values = Maps.newHashMap();

        for (Map.Entry<Integer, IndicatorValue> entry : this) {
            Integer indicatorId = entry.getKey();
            IndicatorValue indicatorValue = entry.getValue();
            if (isLegalKey(indicatorId)) {
                synchronized (indicatorValue) {
                    if (indicatorValue.isNeedSnapshot()) {
                        values.put(indicatorId, indicatorValue.getValue());
                        indicatorValue.remCompressNew();
                    }
                }
            }
        }

        return values;
    }

    /**
     * 消费某个事件相关所有使用了的指标。
     *
     * @return 所有使用了的指标 ID 集合
     */
    public Set<Integer> consumeUsedIndicators() {
        return processUsedIndicators(true);
    }

    /**
     * 获取某个事件相关所有使用了的指标。
     *
     * @return 所有使用了的指标 ID 集合
     */
    public Set<Integer> getUsedIndicators() {
        return processUsedIndicators(false);
    }

    /**
     * 处理某个事件相关所有使用了的指标
     * @param isRemove 是否移除状态
     * @return
     */
    private Set<Integer> processUsedIndicators(boolean isRemove) {
        Set<Integer> indicators = Sets.newHashSet();

        for (Map.Entry<Integer, IndicatorValue> entry : this) {
            Integer indicatorId = entry.getKey();
            IndicatorValue indicatorValue = entry.getValue();
            if (isLegalKey(indicatorId)) {
                synchronized (indicatorValue) {
                    if (indicatorValue.isUsed()) {
                        indicators.add(indicatorId);
                        if (isRemove) {
                            indicatorValue.remUsed();
                            //为了能让UCT分层统计精确
                            indicatorValue.remPreCal();
                        }
                    }
                }
            }
        }

        return indicators;
    }

    /**
     * 添加新计算的指标值。
     *
     * @param eventId 事件 ID
     * @param indicatorId 指标 ID
     * @param value 新计算的指标值
     * @return 增加成功返回 {@code true} 否则返回 {@code false}
     */
    public boolean addNewValue(String eventId, Integer indicatorId, Object value) {
        return this.addNewValueWithPolicy(eventId, indicatorId, value,
                IndicatorSnapshotConstant.SNAPSHOT_POLICY_SAVE);
    }

    /**
     *
     * @param eventId
     * @param indicatorId
     * @param value
     * @param snapshotPolicy
     * @return
     */
    public boolean addNewValueWithPolicy(String eventId, Integer indicatorId, Object value,
                                         int snapshotPolicy) {
        if (StringUtils.isBlank(eventId) || isIllegalKey(indicatorId)) {
            logger.warn("Cannot add new calculated value into cache,eventId=" + eventId
                    + ",indiatorId=" + indicatorId + ",value=" + value);
            return false;
        }

        // 直接覆盖，认为它一定是新计算的
        cachedValues.putIfAbsent(indicatorId, new IndicatorValue(eventId, indicatorId, value)
                .toNew().setPolicy(snapshotPolicy));

        return true;
    }

    /**
     * 增加一批新计算的指标值。
     *
     * @param eventId 事件 ID
     * @param values 指标值集合
     * @return 增加成功返回 {@code true} 否则返回 {@code false}
     */
    public boolean addSomeNewValues(String eventId, Map<Integer, Object> values) {

        return addSomeNewValuesWithPolicy(eventId, values, new HashMap<Integer, Integer>());
    }

    /**
     * 增加一批新计算的指标值。
     *
     * @param eventId 事件 ID
     * @param values 指标值集合
     * @return 增加成功返回 {@code true} 否则返回 {@code false}
     */
    public boolean addSomeNewValuesWithPolicy(String eventId, Map<Integer, Object> values,
                                              Map<Integer, Integer> policies) {
        if (StringUtils.isBlank(eventId) || CollectionUtils.isEmpty(values)) {
            logger.warn("Cannot add new calculated values into cache,eventId=" + eventId
                    + ",values=" + values);
            return false;
        }

        if (policies == null) {
            policies = new HashMap<Integer, Integer>();
        }

        for (Map.Entry<Integer, Object> entry : values.entrySet()) {
            Integer id = entry.getKey();
            Object v = entry.getValue();

            if (isIllegalKey(id)) {
                continue;
            }

            IndicatorValue newValue = new IndicatorValue(eventId, id, v).toNew().setPolicy(
                    policies.get(id));
            cachedValues.putIfAbsent(id, newValue);
        }

        return true;
    }

    /**
     * 添加预计算的指标值到缓存。
     *
     * @param eventId 事件 ID
     * @param values 预计算的指标值
     * @return 添加成功返回 {@code true}
     */
    public boolean addPreCalValues(String eventId, Map<Integer, Object> values) {
        return addPreCalValuesWithPolicy(eventId, values, new HashMap<Integer, Integer>());
    }

    /**
     * 添加预计算的指标值到缓存。
     *
     * @param eventId 事件 ID
     * @param values 预计算的指标值
     * @return 添加成功返回 {@code true}
     */
    public boolean addPreCalValuesWithPolicy(String eventId, Map<Integer, Object> values,
                                             Map<Integer, Integer> policies) {
        if (StringUtils.isBlank(eventId) || CollectionUtils.isEmpty(values)) {
            logger.warn("Cannot add pre-calculated values into cache,eventId=" + eventId
                    + ",values=" + values);
            return false;
        }

        if (policies == null) {
            policies = new HashMap<Integer, Integer>();
        }

        for (Map.Entry<Integer, Object> entry : values.entrySet()) {
            Integer indicatorId = entry.getKey();
            if (isIllegalKey(indicatorId)) {
                logger.warn("Cannot add pre-calculated value,illegal indicatorId,eventId="
                        + eventId + ",indicatorId=" + indicatorId + ",value="
                        + entry.getValue());
                continue;
            }

            cachedValues.putIfAbsent(indicatorId,
                    new IndicatorValue(eventId, indicatorId, entry.getValue()).toNew().toPreCal()
                            .setPolicy(policies.get(indicatorId)));
        }

        return true;
    }

    /**
     * 添加指标值，状态为默认。
     *
     * @param eventId 事件 ID
     * @param indicatorId 指标 ID
     * @param value 指标值
     * @return 添加成功返回 {@code true} 否则返回 {@code false}
     */
    public boolean addValue(String eventId, Integer indicatorId, Object value) {
        if (StringUtils.isBlank(eventId) || isIllegalKey(indicatorId) || value == null) {
            logger.warn("Cannot add value info cache,eventId=" + eventId + ",indicatorId="
                    + indicatorId + ",value=" + value);
            return false;
        }

        // 不覆盖已有的值
        cachedValues.putIfAbsent(indicatorId, new IndicatorValue(eventId, indicatorId, value));
        return true;
    }

    /**
     * 添加一批透传的指标值到缓存。
     *
     * @param eventId 事件 ID
     * @param values 指标值集合
     * @return 添加成功返回 {@code true} 否则返回 {@code false}
     */
    public boolean addTransInValues(String eventId, Map<Integer, Object> values) {
        if (StringUtils.isBlank(eventId) || CollectionUtils.isEmpty(values)) {
            logger.warn("Cannot add transmit in values into cache,eventId=" + eventId + ",values="
                    + values);
            return false;
        }

        for (Map.Entry<Integer, Object> entry : values.entrySet()) {
            Integer indicatorId = entry.getKey();
            if (isIllegalKey(indicatorId)) {
                logger.warn("Cannot add transmit in value,illegal indicatorId,eventId=" + eventId
                        + ",indicatorId=" + indicatorId + ",value=" + entry.getValue());
                continue;
            }

            cachedValues.putIfAbsent(indicatorId,
                    new IndicatorValue(eventId, indicatorId, entry.getValue()).toTransIn());
        }

        return true;
    }

    /**
     * 添加一批指标值，状态为默认。
     *
     * @param eventId 事件 ID
     * @param values 指标值集合
     * @return 添加成功返回 {@code true} 否则返回 {@code false}
     */
    public boolean addSomeValues(String eventId, Map<Integer, Object> values) {
        if (StringUtils.isBlank(eventId) || CollectionUtils.isEmpty(values)) {
            logger.warn("Cannot add values into cache,eventId=" + eventId + ",values=" + values);
            return false;
        }

        for (Map.Entry<Integer, Object> entry : values.entrySet()) {
            Integer indicatorId = entry.getKey();
            // 不覆盖已有的值
            cachedValues.putIfAbsent(indicatorId,
                    new IndicatorValue(eventId, indicatorId, entry.getValue()));
        }

        return true;
    }

    /**
     * 获取缓存的指标值
     *
     * @param indicatorId 指标 ID
     * @return 缓存中的指标值
     */
    public Object getValue(Integer indicatorId) {
        IndicatorValue indicatorValue = get(indicatorId);
        if (indicatorValue == null) {
            return null;
        }

        IndicatorMetrics.markCacheHit(indicatorValue);
        return indicatorValue.getValue();
    }

    /**
     * 从缓存条目获取用于透传的指标值（<b>不会计入缓存命中统计</b>）。
     *
     * @param indicatorIds 需要获取缓存值的指标 ID 集合
     * @return 指标缓存值
     */
    public Map<Integer, Object> getTransIndicatorValues(Set<Integer> indicatorIds) {
        if (CollectionUtils.isEmpty(indicatorIds)) {
            logger.warn("Cannot get some values from cache for transmit,indicatorIds="
                    + indicatorIds);
            return Collections.emptyMap();
        }

        Map<Integer, Object> cachedValues = Maps.newHashMap();

        for (Integer id : indicatorIds) {
            IndicatorValue indicatorValue = get(id);
            if (indicatorValue != null) {
                cachedValues.put(id, indicatorValue.getValue());
            }
        }
        return cachedValues;
    }

    /**
     * 获取缓存的指标值对象。
     *
     * @param indicatorId 指标 ID
     * @return 指标缓存值对象
     */
    public IndicatorValue get(Integer indicatorId) {
        if (isIllegalKey(indicatorId)) {
            logger.warn("Cannot get value from cache,indicatorId=" + indicatorId);
            return null;
        }

        return this.cachedValues.get(indicatorId);
    }

    /**
     * 检查指标id的值是否已有值
     *
     * @param indicatorId 指标id
     * @return 已有值返回true,否则返回false
     */
    public boolean hasValue(Integer indicatorId) {
        return get(indicatorId) != null;
    }

    /**
     * 检查指标id的值是否已有值
     *
     * @param indicatorId 指标id
     * @return 已有值返回true,否则返回false
     */
    public Object getValueRef(Integer indicatorId) {
        return get(indicatorId) != null;
    }

    /**
     * 获取缓存中的穿行计算指标id列表
     *
     * @return
     */
    public String getCommonIndicatorIds() {
        String ids = "";
        for (Map.Entry<Integer, IndicatorValue> entry : this.cachedValues.entrySet()) {
            if (!entry.getValue().isTransIn() && !entry.getValue().isPreCal()) {
                ids += entry.getValue().getIndicatorId() + ",";
            }
        }
        return ids;
    }

    /**
     * 打印指标行情
     *
     * @return
     */
    public String reportStatistics() {
        int preSize = 0;
        int usedOfPre = 0;
        int used = 0;
        int count = 0;
        for (Map.Entry<Integer, IndicatorValue> entry : this.cachedValues.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }

            //总个数
            count++;

            //指标预计算个数
            if (entry.getValue().isPreCal()) {
                preSize++;
                //指标预计算被使用个数
                if (entry.getValue().isUsed()) {
                    usedOfPre++;
                }
            }

            //总使用指标个数
            if (entry.getValue().isUsed()) {
                used++;
            }
        }

        return usedOfPre + "," + preSize + "|" + used + "," + count;
    }

    /**
     * 打印指标行情
     * 为模型调用定制，模型调用没有拦截器预计算环节，因此打印模型实际使用了哪些指标以及指标明晰列表
     * @return
     */
    public String reportStatistics2() {
        int used = 0;
        int noUsed=0;
        int count = 0;
        List<Integer> usedList=new ArrayList<Integer>();
        List<Integer> noUsedList=new ArrayList<Integer>();

        for (Map.Entry<Integer, IndicatorValue> entry : this.cachedValues.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }

            //总个数
            count++;
            //变量批量计算过程中使用到的指标统计
            if (entry.getValue().isUsed()) {
                usedList.add(entry.getKey());
                used++;
            } //变量批量计算过程中计算了但未使用的指标统计
            else if (entry.getValue().isNew()&&!entry.getValue().isUsed()) {
                noUsedList.add(entry.getKey());
                noUsed++;
            }
        }

        return "totalCout:" +count+ ",usedCount:" + used+",usedDetail:"+usedList.toString()+",noUsedCount:"+noUsed+",noUsedDetail:"+noUsedList.toString();
    }

    /**
     * 获取缓存中的预计算计算指标id列表
     *
     * @return
     */
    public String getPreIndicatorIds() {
        String ids = "";
        for (Map.Entry<Integer, IndicatorValue> entry : this.cachedValues.entrySet()) {
            if (!entry.getValue().isTransIn() && entry.getValue().isPreCal()) {
                ids += entry.getValue().getIndicatorId() + ",";
            }
        }
        return ids;
    }

    /**
     *
     * @param indicatorSet
     */
    public void addFailurePreIndicatorSet(Set<Integer> indicatorSet) {
        if (indicatorSet != null) {
            this.failurePreIndicatorSet.addAll(indicatorSet);
        }
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Map.Entry<Integer, IndicatorValue>> iterator() {
        return this.cachedValues.entrySet().iterator();
    }

    /**
     * isIllegalKey
     *
     * @param key
     * @return
     */
    private boolean isIllegalKey(Integer key) {
        return !isLegalKey(key);
    }

    /**
     * isLegalKey
     *
     * @param key
     * @return
     */
    private boolean isLegalKey(Integer key) {
        return key != null && key > 0;
    }

    /**
     * Getter method for property <tt>failurePreIndicatorSet</tt>.
     *
     * @return property value of failurePreIndicatorSet
     */
    public Set<Integer> getFailurePreIndicatorSet() {
        return failurePreIndicatorSet;
    }

    /**
     * Getter method for property <tt>remoteCalTimer</tt>.
     *
     * @return property value of remoteCalTimer
     */
    public CalStat getRemoteCalTimer() {
        if (this.remoteCalTimer == null) {
            this.remoteCalTimer = new CalStat();
        }
        return remoteCalTimer;
    }

    /**
     * Getter method for property <tt>localCalTimer</tt>.
     *
     * @return property value of localCalTimer
     */
    public CalStat getLocalCalTimer() {
        if (this.localCalTimer == null) {
            this.localCalTimer = new CalStat();
        }

        return localCalTimer;
    }

    /**
     * Getter method for property <tt>localCalTimer</tt>.
     *
     * @return property value of localCalTimer
     */
    public CalStat getCalTimer() {
        if (this.calTimer == null) {
            this.calTimer = new CalStat();
        }

        return calTimer;
    }

    /**
     * resetCalTimer
     */
    public void resetCalTimer() {
        this.calTimer = new CalStat();
    }

    /**
     * resetRemoteCalTimer
     */
    public void resetRemoteCalTimer() {
        this.remoteCalTimer = new CalStat();
    }

    /**
     * resetLocalCalTimer
     */
    public void resetLocalCalTimer() {
        this.localCalTimer = new CalStat();
    }

    /**远程计算统计
     * @param  ts
     * */
    public void incrRemote(long ts) {
        getRemoteCalTimer().incr(ts);
    }

    /**
     * 计算统计
     * @param ts
     * */
    public void incrCal(long ts) {
        getCalTimer().incr(ts);
    }

    /**
     * 本地计算统计
     * @param ts
     * */
    public void incrLocal(long ts) {
        getLocalCalTimer().incr(ts);
    }

    /**
     *
     */
    public static class CalStat {
        AtomicInteger num  = new AtomicInteger(0);
        AtomicLong cost = new AtomicLong(0);

        /**
         * incr
         *
         * @param ts
         */
        void incr(long ts) {
            this.num.incrementAndGet();
            this.cost.addAndGet(ts);
        }

        /**
         * detail
         * @return
         */
        public String detail() {
            return num.get() + "," + cost.get();

        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "CalStat [num=" + num.get() + ", cost=" + cost.get() + "]";
        }
    }


}

