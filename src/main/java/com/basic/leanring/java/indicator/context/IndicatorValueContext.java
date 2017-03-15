package com.basic.leanring.java.indicator.context;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.basic.leanring.java.indicator.model.CommonCtuEvent;
import com.basic.leanring.java.indicator.model.IndicatorSnapshotConstant;
import com.basic.leanring.java.indicator.model.IndicatorValue;
import com.basic.leanring.java.indicator.model.SingleCalResult;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author sunzihan
 * @version $Id: IndicatorValueContext.java V 0.1 3/15/17 15:48 sunzihan EXP $
 */
public class IndicatorValueContext implements Observer, InitializingBean {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(IndicatorValueContext.class);

    /**
     * 从缓存获取单个指标计算结果。
     *
     * @return 指标计算结果
     */
    public static SingleCalResult getCachedSingleResult(CommonCtuEvent event,
                                                        IndicatorValueCacheItem cachedItem,
                                                        Integer indicatorId) {
        if (event == null) {
            return null;
        }

        String eventId = event.getId();
        if (indicatorId == null || indicatorId < 0 || StringUtils.isBlank(eventId)) {
            return null;
        }

        IndicatorValue valueRef = getValueRef(eventId, cachedItem, indicatorId);
        if (valueRef != null) {
            SingleCalResult sr = new SingleCalResult();
            sr.setIndicatorId(indicatorId);
            sr.setSuccess(true);
            sr.setResult(valueRef.getValue());
            return sr;
        }

        return null;
    }

    /**
     * 向上下文中设置本次业务中「新计算」的指标值。
     *
     * @param eventId 事件 ID
     * @param value 指标值
     */
    public static void addNewValue(String eventId, Integer indicatorId, Object value) {
        //默认快照策略为存储
        addNewValueWithPolicy(eventId,indicatorId,value, IndicatorSnapshotConstant.SNAPSHOT_POLICY_SAVE);
    }

    /**
     *
     * @param eventId
     * @param indicatorId
     * @param value
     * @param snapshotPolicy
     */
    public static void addNewValueWithPolicy(String eventId, Integer indicatorId, Object value,int snapshotPolicy) {
        if (StringUtils.isBlank(eventId) || indicatorId == null || indicatorId < 0) {
            logger.warn("Cannot add new calculated value into cache,eventId=" + eventId
                    + ",indicatorId=" + indicatorId + ",value=" + value);
            return;
        }

        IndicatorValueCacheItem cachedItem = getInstance().cachedItem(eventId);
        cachedItem.addNewValueWithPolicy(eventId, indicatorId, value, snapshotPolicy);

        if (logger.isDebugEnabled()) {
            logger.debug("Add new calculated value into value cache,eventId=" + eventId
                    + ",indicatorId=" + indicatorId + ",value=" + value);
        }
    }

    /**
     * 向上下文中设置一批「新计算」的指标值。
     *
     * @param eventId 事件 ID
     * @param values 一批新「新计算」的指标值
     */
    public static void addSomeNewValues(String eventId, Map<Integer, Object> values) {
        addSomeNewValuesWithPolicy(eventId,values,new HashMap<Integer, Integer>());
    }

    /**
     *
     * @param eventId
     * @param values
     */
    public static void addSomeNewValuesWithPolicy(String eventId, Map<Integer, Object> values,Map<Integer,Integer>policies) {
        if (StringUtils.isBlank(eventId) || CollectionUtils.isEmpty(values)) {
            logger.warn("Cannot add some new calculated values into cache,eventId=" + eventId
                    + ",values=" + values);
            return;
        }

        IndicatorValueCacheItem cachedItem = getInstance().cachedItem(eventId);
        cachedItem.addSomeNewValuesWithPolicy(eventId, values,policies);

        if (logger.isDebugEnabled()) {
            logger.debug("Add some new calculated values into cache,eventId=" + eventId
                    + ",values=" + values);
        }
    }

    /**
     * 向上下问中设置预计算的指标值。
     *
     * @param eventId 事件 ID
     * @param values 预计算的指标值
     */
    public static void addPreCalValues(String eventId, Map<Integer, Object> values) {
        addPreCalValuesWithPolicy(eventId,values,new HashMap<Integer, Integer>());
    }

    /**
     *
     * @param eventId
     * @param values
     */
    public static void addPreCalValuesWithPolicy(String eventId, Map<Integer, Object> values,Map<Integer,Integer>policies) {
        if (StringUtils.isBlank(eventId) || CollectionUtils.isEmpty(values)) {
            logger.warn("Cannot add pre-calculated values into cache,eventId=" + eventId
                    + ",values=" + values);
            return;
        }

        IndicatorValueCacheItem cachedItem = getInstance().cachedItem(eventId);
        cachedItem.addPreCalValuesWithPolicy(eventId, values, policies);

        if (logger.isDebugEnabled()) {
            logger.debug("Add pre-calculated values into cache,eventId=" + eventId + ",values="
                    + values);
        }
    }

    /**
     * 消费「新计算」的指标值，被消费的指标会移除「新计算」这种类型的元数据。
     *
     * @param eventId 事件ID
     * @return 该事件相关的，元类型为「新计算」的指标值集合
     */
    public static Map<Integer, Object> consumeNewValues(String eventId) {
        if (StringUtils.isBlank(eventId)) {
            logger.warn("Cannot consume new values,blank eventId");
            return Collections.emptyMap();
        }

        IndicatorValueCacheItem cachedItem = getInstance().cachedItem(eventId);
        Map<Integer, Object> values = cachedItem.consumeNewValues();

        if (logger.isDebugEnabled()) {
            logger.debug("Consume new values,values=" + values);
        }

        return values;
    }

    /**
     * 获取指定事件需要存储的快照指标
     *
     * @param eventId
     * @return
     */
    public static Map<Integer,Object> consumeSnapshot(String eventId){

        if (StringUtils.isBlank(eventId)) {
            logger.warn("Cannot consume snapshot values,blank eventId");
            return Collections.emptyMap();
        }

        IndicatorValueCacheItem cachedItem = getInstance().cachedItem(eventId);
        Map<Integer, Object> values = cachedItem.consumeSnapshot();

        if (logger.isDebugEnabled()) {
            logger.debug("Consume snapshot values,values=" + values);
        }

        return values;
    }

    /**
     * 向上下文中设置本次业务相关的指标值（透传）。
     *
     * @param eventId 事件 ID
     * @param indicatorId 指标的 ID
     * @param value 指标值
     */
    public static void addValue(String eventId, Integer indicatorId, Object value) {
        if (StringUtils.isBlank(eventId) || indicatorId == null || indicatorId < 0 || value == null) {
            logger.warn("Cannot add value into cache,eventId=" + eventId + ",indicatorId="
                    + indicatorId + ",value=" + value);
            return;
        }

        IndicatorValueCacheItem cachedItem = getInstance().cachedItem(eventId);
        cachedItem.addValue(eventId, indicatorId, value);

        if (logger.isDebugEnabled()) {
            logger.debug("Add indicator value into cache,eventId=" + eventId + ",indicatorId="
                    + indicatorId + ",value=" + value);
        }
    }

    /**
     * 向上下文中设置本次业务相关的透传指标值。
     *
     * @param eventId  事件 ID
     * @param values 指标值集合
     */
    public static void addTransInValues(String eventId, Map<Integer, Object> values) {
        if (StringUtils.isBlank(eventId) || CollectionUtils.isEmpty(values)) {
            logger.warn("Cannot add transmit in values into cache,eventId=" + eventId + ",values="
                    + values);
            return;
        }

        IndicatorValueCacheItem cacheItem = getInstance().cachedItem(eventId);
        cacheItem.addTransInValues(eventId, values);

        if (logger.isDebugEnabled()) {
            logger.debug("Add transmit in values into cache,evenId=" + eventId + ",values="
                    + values);
        }
    }

    /**
     * 向上下文中添加一批指标值。
     *
     * @param eventId 事件 ID
     * @param values 指标值集合
     */
    public static void addSomeValues(String eventId, Map<Integer, Object> values) {
        if (StringUtils.isBlank(eventId) || CollectionUtils.isEmpty(values)) {
            logger.warn("Cannot add some values into cache,eventId=" + eventId + ",values="
                    + values);
            return;
        }

        IndicatorValueCacheItem cachedItem = getInstance().cachedItem(eventId);
        cachedItem.addSomeValues(eventId, values);

        if (logger.isDebugEnabled()) {
            logger.debug("Add some values into cache,evenId=" + eventId + ",values=" + values);
        }
    }

    /**
     * 设置某个指标计算值的元类型为「使用」。
     *
     * @param eventId 事件 ID
     * @param indicatorId 指标的 ID
     */
    public static void useValue(String eventId, Integer indicatorId) {
        if (StringUtils.isBlank(eventId) || indicatorId == null || indicatorId < 0) {
            logger.warn("Cannot use value,eventId=" + eventId + ",indicatorId=" + indicatorId);
            return;
        }

        IndicatorValueCacheItem cachedItem = getInstance().cachedItem(eventId);
        cachedItem.useValue(indicatorId);

        if (logger.isDebugEnabled()) {
            logger.debug("Use value for eventId=" + eventId + ",indicatorId=" + indicatorId);
        }
    }

    /**
     * 设置一批指标的元类型为「使用」。
     *
     * @param eventId 事件ID
     * @param indicatorIds 指标ID集合
     */
    public static void useSomeValues(String eventId, Set<Integer> indicatorIds) {
        if (StringUtils.isBlank(eventId) || CollectionUtils.isEmpty(indicatorIds)) {
            logger.warn("Cannot use some values,eventId=" + eventId + ",indicatorIds="
                    + indicatorIds);
            return;
        }

        IndicatorValueCacheItem cachedItem = getInstance().cachedItem(eventId);
        cachedItem.useSomeValues(indicatorIds);

        if (logger.isDebugEnabled()) {
            logger
                    .debug("Use some values for eventId=" + eventId + ",indicatorIds=" + indicatorIds);
        }
    }

    /**
     * 消费「已使用」的指标，调用后会移除当前事件相关指标值「已使用」状态。
     *
     * @param eventId 事件 ID
     * @return 使用到的指标 ID 集合
     */
    public static Set<Integer> consumeUsedIndicators(String eventId) {
        if (StringUtils.isBlank(eventId)) {
            logger.warn("Cannot consume used indicators for blank eventId");
            return Collections.emptySet();
        }

        IndicatorValueCacheItem cachedItem = getInstance().cachedItem(eventId);
        Set<Integer> indicators = cachedItem.consumeUsedIndicators();
        if (logger.isDebugEnabled()) {
            logger.debug("Consume used values,eventId=" + eventId + ",indicators=" + indicators);
        }

        return indicators;
    }

    /**
     * 在消费前记录指标的整体状态
     *
     * @param eventId 事件 ID
     * @return 使用到的指标 ID 集合
     */
    public static String reportBeforeConsume(String eventId) {
        if (StringUtils.isBlank(eventId)) {
            logger.warn("Cannot consume used indicators for blank eventId");
            return "";
        }

        return getInstance().cachedItem(eventId).reportStatistics();
    }

    /**
     * 在消费前记录指标的整体状态
     *
     * @param eventId 事件 ID
     * @return 使用到的指标 ID 集合
     */
    public static String reportUsedDetail(String eventId) {
        if (StringUtils.isBlank(eventId)) {
            logger.warn("Cannot consume used indicators for blank eventId");
            return "";
        }

        return getInstance().cachedItem(eventId).reportStatistics2();
    }

    /**
     * 获取「已使用」的指标。
     *
     * @param eventId 事件 ID
     * @return 使用到的指标 ID 集合
     */
    public static Set<Integer> getUsedIndicators(String eventId) {
        if (StringUtils.isBlank(eventId)) {
            logger.warn("Cannot get used indicators for blank eventId");
            return Collections.emptySet();
        }

        IndicatorValueCacheItem cachedItem = getInstance().cachedItem(eventId);
        if (cachedItem == null) {
            return Collections.emptySet();
        }

        Set<Integer> indicators = cachedItem.getUsedIndicators();
        if (logger.isDebugEnabled()) {
            logger.debug("Get used values,eventId=" + eventId + ",indicators=" + indicators);
        }
        return indicators;
    }

    /**
     * 移除缓存中的指标计算值。
     *
     * @param eventId 事件 ID
     */
    public static void removeValues(String eventId) {
        if (logger.isDebugEnabled()) {
            logger.debug("Remove cached values,eventId=" + eventId);
        }
        getInstance().removeCacheValues(eventId);
    }

    /**
     * 从上下文获取本次业务的某个指标值。
     *
     * @param eventId 事件 ID
     * @param indicatorId 指标的 ID
     * @return 指标值
     */
    public static Object getValue(String eventId, IndicatorValueCacheItem cachedItem,
                                  Integer indicatorId) {
        if (StringUtils.isBlank(eventId) || indicatorId == null || indicatorId < 0) {
            logger.warn("Cannot get value from cache,eventId=" + eventId + ",indicatorId="
                    + indicatorId);
            return null;
        }

        if (cachedItem == null) {
            cachedItem = getInstance().cachedItem(eventId);
        }

        return cachedItem.getValue(indicatorId);
    }

    /**
     * 从上下文获取本次业务的某个指标值。
     *
     * @param eventId 事件 ID
     * @param indicatorId 指标的 ID
     * @return 指标值
     */
    public static IndicatorValue getValueRef(String eventId, IndicatorValueCacheItem cachedItem,
                                             Integer indicatorId) {
        if (StringUtils.isBlank(eventId) || indicatorId == null || indicatorId < 0) {
            logger.warn("Cannot get value from cache,eventId=" + eventId + ",indicatorId="
                    + indicatorId);
            return null;
        }

        if (cachedItem == null) {
            cachedItem = getInstance().cachedItem(eventId);
        }

        return cachedItem.get(indicatorId);
    }

    /**
     * 判断上下文获取本次业务的某个指标值。
     *
     * @param eventId 事件 ID
     * @param indicatorId 指标的 ID
     * @return 是否存在
     */
    public static boolean hasValue(String eventId, IndicatorValueCacheItem cachedItem,
                                   Integer indicatorId) {
        if (StringUtils.isBlank(eventId) || indicatorId == null || indicatorId < 0) {
            logger.warn("Cannot get value from cache,eventId=" + eventId + ",indicatorId="
                    + indicatorId);
            return false;
        }

        if (cachedItem == null) {
            cachedItem = getInstance().cachedItem(eventId);
        }

        if (cachedItem == null) {
            return false;
        }

        return cachedItem.hasValue(indicatorId);
    }

    /**
     * 从上下文获取本次业务的某个指标值。
     *
     * @param eventId 事件 ID
     * @return 指标值
     */
    public static IndicatorValueCacheItem getCachedItem(String eventId) {
        if (StringUtils.isBlank(eventId)) {
            logger.warn("Cannot get CachedItem from cache,eventId=" + eventId);
            return null;
        }

        return getInstance().cachedItem(eventId);
    }

    /**
     * 从上下文获取一批本次业务的指标值。
     *
     * @param eventId 事件 ID
     * @param indicatorIds 需要获取的指标 ID 集合
     * @return 指标值集合
     */
    public static Map<Integer, Object> getTransIndicatorValues(String eventId,
                                                               Set<Integer> indicatorIds) {
        if (StringUtils.isBlank(eventId) || CollectionUtils.isEmpty(indicatorIds)) {
            logger.warn("Cannot get some values from cache,eventId=" + eventId + ",indicatorIds="
                    + indicatorIds);
            return Collections.emptyMap();
        }

        IndicatorValueCacheItem cachedItem = getInstance().cachedItem(eventId);
        Map<Integer, Object> cachedValues = cachedItem.getTransIndicatorValues(indicatorIds);

        if (logger.isDebugEnabled()) {
            if (!CollectionUtils.isEmpty(cachedValues)) {
                logger.debug(String.format(
                        "Some indicator cache hit,eventId=%s,indicatorIds=%s,cachedValues=%s", eventId,
                        indicatorIds, cachedValues));
            } else {
                logger.debug(String.format(
                        "Some indicator cache missing,eventId=%s,indicatorIds=%s", eventId,
                        indicatorIds));
            }
        }

        return cachedValues;
    }

    /**
     * 是否指定的所有指标id的值都已存在
     *
     * @param eventId 事件ID
     * @param indicatorIds 需要检查的指标 ID 集合
     * @return 所有指标id值都已存在返回ture,否则返回false
     */
    public static boolean existValues(String eventId, Set<Integer> indicatorIds) {
        if (CollectionUtils.isEmpty(indicatorIds)) {
            return true;
        }

        if (StringUtils.isBlank(eventId)) {
            logger.warn("Cannot get CachedItem from cache,eventId=" + eventId);
            return false;
        }

        IndicatorValueCacheItem cacheItem = getInstance().cachedItem(eventId);
        if (cacheItem == null) {
            return false;
        }

        for (Integer id : indicatorIds) {
            if (!cacheItem.hasValue(id)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 获取单例，通过 Spring 的 factory-method 机制注入依赖的 bean。
     * 这样可以对外暴露静态的工具型方法，并享受 Spring 的依赖注入。
     *
     * @return 上下文单例
     */
    public static IndicatorValueContext getInstance() {
        return Singletone.SINGLETON;
    }

    // -------------------------------------------------------------- Instance fields, methods

    /** 指标计算上下文缓存：eventId => indicatorId => indicatorValue */
    private LoadingCache<String, IndicatorValueCacheItem> cache;

    /** 基于 ThreadLocal 的指标缓存 */
    private static final ThreadLocal<Map<String, IndicatorValueCacheItem>> threadLocalIndicators;
    static {
        threadLocalIndicators = new ThreadLocal<Map<String, IndicatorValueCacheItem>>() {
            protected Map<String, IndicatorValueCacheItem> initialValue() {
                return Maps.newConcurrentMap();
            }

        };
    }

//    /** 指标客户端缓存资源 */
//    private IndicatorClientConfigResource                                  indicatorClientConfigResource;

    /** 单例 */
    private static final class Singletone {
        private static final IndicatorValueContext SINGLETON = new IndicatorValueContext();
    }

    /**
     *
     * @param o
     * @param arg
     */
    @Override
    public synchronized void update(Observable o, Object arg) {
//        if (arg == DrmAction.CHANGE_CACHE_CONFIG_INDICATOR_VALUE) {
//            initCache();
//        }
    }

    /**
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            initCache();
        } finally {
            //indicatorClientConfigResource.addObserver(this);
        }
    }

    /**
     * 根据键直接获取缓存数据项。
     *
     * @param key 缓存数据项的键
     * @return 缓存数据项
     */
    public IndicatorValueCacheItem cachedItem(String key) {
        try {

//            if (indicatorClientConfigResource.switchToNewCache(CacheUseCase.INDICATOR_VALUE)) {
//                return threadLocalCacheItem(key);
//            }
            return cache.get(key);

        } catch (Exception e) {
            logger.error("Get indicator value cache failed,key=" + key, e);
            return new IndicatorValueCacheItem();
        }
    }

    /**
     * 移除 key 对应的缓存值。
     *
     * @param key
     */
    public void removeCacheValues(String key) {
//        if (indicatorClientConfigResource.switchToNewCache(CacheUseCase.INDICATOR_VALUE)) {
//            threadLocalIndicators.remove();
//        } else {
//            cache.invalidate(key);
//        }
    }

    private IndicatorValueCacheItem threadLocalCacheItem(String key) {
        Map<String, IndicatorValueCacheItem> kv = threadLocalIndicators.get();
        if (kv == null) {
            kv = Maps.newConcurrentMap();
            threadLocalIndicators.set(kv);
        }

        IndicatorValueCacheItem cacheItem = kv.get(key);
        if (cacheItem == null) {
            cacheItem = new IndicatorValueCacheItem();
            kv.put(key, cacheItem);
        }
        return cacheItem;
    }

    /**
     * 初始化缓存
     */
    private void initCache() {
        String config = "";
        //String config = indicatorClientConfigResource.getValueCacheConfig();

        cache = CacheBuilder.from(config).build(new CacheLoader<String, IndicatorValueCacheItem>() {
            @Override
            public IndicatorValueCacheItem load(String key) throws Exception {
                return new IndicatorValueCacheItem();
            }
        });
    }

//    /**
//     *
//     * @param indicatorClientConfigResource
//     */
//    public void setIndicatorClientConfigResource(IndicatorClientConfigResource indicatorClientConfigResource) {
//        this.indicatorClientConfigResource = indicatorClientConfigResource;
//    }




}

