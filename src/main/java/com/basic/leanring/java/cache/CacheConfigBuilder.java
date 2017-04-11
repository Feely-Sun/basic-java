package com.basic.leanring.java.cache;

import java.util.concurrent.TimeUnit;

import com.alibaba.fastjsonfordrm.JSON;
import com.google.common.cache.CacheBuilder;

/**
 * @author sunzihan
 * @version $Id: CacheConfigBuilder.java V 0.1 3/1/17 11:27 sunzihan EXP $
 */
public class CacheConfigBuilder {

    /** 并发级别 See {@link CacheBuilder#concurrencyLevel(int)} */
    private int      concurrencyLevel  = -1;

    /** 初始化容量 See {@link CacheBuilder#initialCapacity(int)} */
    private int      initialCapacity   = -1;

    /** 缓存上限 See {@link CacheBuilder#maximumSize(long)} */
    private long     maximumSize       = -1;

    /** 缓存最大权重 See {@link CacheBuilder#maximumWeight(long)} */
    private long     maximumWeight     = -1;

    /** 缓存数据项过期时间 See {@link CacheBuilder#expireAfterAccess(long, TimeUnit)} */
    private long     expireAfterAccess = -1;

    /** 缓存数据项过期时间（write） See {@link CacheBuilder#expireAfterWrite(long, TimeUnit)} */
    private long     expireAfterWrite  = -1;

    /** 自动刷新数据项的时间 See {@link CacheBuilder#refreshAfterWrite(long, TimeUnit)} */
    private long     refreshAfterWrite = -1;

    /** 时间单位 */
    private TimeUnit timeUnit;

    /** 是否 WeakReference 的 key See {@link CacheBuilder#weakKeys()} */
    private boolean  isWeakKeys;

    /** 是否 SoftReference 的 value See {@link CacheBuilder#softValues()} */
    private boolean  isSoftValues;

    /** 是否 WeakReference 的 value See {@link CacheBuilder#weakValues()} */
    private boolean  isWeakValues;

    /** 是否记录缓存状态 See {@link CacheBuilder#recordStats()} */
    private boolean  isRecordStats;

    /**
     * 创建缓存配置构造器。
     *
     * @return 缓存配置构造器
     */
    public static CacheConfigBuilder newBuilder() {
        return new CacheConfigBuilder();
    }

    /**
     * 构造缓存配置串。
     *
     * @return 缓存配置串
     */
    public String build() {

        StringBuilder sb = new StringBuilder();
        String unit = getUnit();

        if (concurrencyLevel >= 0) {
            sb.append("concurrencyLevel=").append(concurrencyLevel).append(',');
        }

        if (initialCapacity >= 0) {
            sb.append("initialCapacity=").append(initialCapacity).append(',');
        }

        if (maximumSize >= 0) {
            sb.append("maximumSize=").append(maximumSize).append(',');
        }

        if (maximumWeight >= 0) {
            sb.append("maximumWeight=").append(maximumWeight).append(',');
        }

        if (expireAfterAccess >= 0) {
            sb.append("expireAfterAccess=").append(expireAfterAccess).append(unit).append(',');
        }

        if (expireAfterWrite >= 0) {
            sb.append("expireAfterWrite=").append(expireAfterWrite).append(unit).append(',');
        }

        if (refreshAfterWrite >= 0) {
            sb.append("refreshAfterWrite=").append(refreshAfterWrite).append(unit).append(',');
        }

        if (isWeakKeys) {
            sb.append("weakKeys").append(',');
        }

        if (isSoftValues) {
            sb.append("softValues").append(',');
        } else if (isWeakValues) {
            sb.append("weakValues").append(',');
        }

        if (isRecordStats) {
            sb.append("recordStats").append(',');
        }

        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    private String getUnit() {
        String unit = "";
        if (timeUnit != null) {
            switch (timeUnit) {

                case DAYS:
                    unit = "d";
                    break;

                case HOURS:
                    unit = "h";
                    break;

                case MINUTES:
                    unit = "m";
                    break;

                case SECONDS:
                    unit = "s";
                    break;

                default:
                    break;
            }

        }
        return unit;
    }

    /**
     * 并发级别 See {@link CacheBuilder#concurrencyLevel(int)}
     *
     * @param concurrencyLevel 并发级别
     * @return 构造器自身
     */
    public CacheConfigBuilder concurrencyLevel(int concurrencyLevel) {
        this.concurrencyLevel = concurrencyLevel;
        return this;
    }

    /**
     * 初始化容量 See {@link CacheBuilder#initialCapacity(int)}
     *
     * @param initialCapacity 初始化容量
     * @return 构造器自身
     */
    public CacheConfigBuilder initialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        return this;
    }

    /**
     * 缓存上限 See {@link CacheBuilder#maximumSize(long)}
     *
     * @param maximumSize 缓存上限
     * @return 构造器自身
     */
    public CacheConfigBuilder maximumSize(long maximumSize) {
        this.maximumSize = maximumSize;
        return this;
    }

    /**
     * 缓存最大权重 See {@link CacheBuilder#maximumWeight(long)}
     *
     * @param maximumWeight 缓存最大权重
     * @return 构造器自身
     */
    public CacheConfigBuilder maximumWeight(long maximumWeight) {
        this.maximumWeight = maximumWeight;
        return this;
    }

    /**
     * 缓存数据项过期时间 See {@link CacheBuilder#expireAfterAccess(long, TimeUnit)}
     *
     * @param expireAfterAccess 缓存数据项过期时间
     * @return 构造器自身
     */
    public CacheConfigBuilder expireAfterAccess(long expireAfterAccess) {
        this.expireAfterAccess = expireAfterAccess;
        return this;
    }

    /**
     * 缓存数据项过期时间（write） See {@link CacheBuilder#expireAfterWrite(long, TimeUnit)}
     *
     * @param expireAfterWrite 缓存数据项过期时间（write）
     * @return 构造器自身
     */
    public CacheConfigBuilder expireAfterWrite(long expireAfterWrite) {
        this.expireAfterWrite = expireAfterWrite;
        return this;
    }

    /**
     * 自动刷新数据项的时间 See {@link CacheBuilder#refreshAfterWrite(long, TimeUnit)}
     *
     * @param refreshAfterWrite 自动刷新数据项的时间
     * @return 构造器自身
     */
    public CacheConfigBuilder refreshAfterWrite(long refreshAfterWrite) {
        this.refreshAfterWrite = refreshAfterWrite;
        return this;
    }

    /**
     * 是否记录缓存状态 See {@link CacheBuilder#recordStats()}
     *
     * @param isRecordStats 是否记录缓存状态
     * @return 构造器自身
     */
    public CacheConfigBuilder isRecordStats(boolean isRecordStats) {
        this.isRecordStats = isRecordStats;
        return this;
    }

    /**
     * 是否 WeakReference 的 key See {@link CacheBuilder#weakKeys()}
     *
     * @param isWeakKeys 是否 WeakReference 的 key
     * @return 构造器自身
     */
    public CacheConfigBuilder isWeakKeys(boolean isWeakKeys) {
        this.isWeakKeys = isWeakKeys;
        return this;
    }

    /**
     * 是否 WeakReference 的 value See {@link CacheBuilder#weakValues()}
     *
     * @param isWeakValues 是否 WeakReference 的 value
     * @return 构造器自身
     */
    public CacheConfigBuilder isWeakValues(boolean isWeakValues) {
        this.isWeakValues = isWeakValues;
        return this;
    }

    /**
     * 是否 SoftReference 的 value See {@link CacheBuilder#softValues()}
     *
     * @param isSoftValues 是否 SoftReference 的 value
     * @return 构造器自身
     */
    public CacheConfigBuilder isSoftValues(boolean isSoftValues) {
        this.isSoftValues = isSoftValues;
        return this;
    }

    /**
     * 时间单位。
     *
     * @param timeUnit 时间单位
     * @return 构造器自身
     */
    public CacheConfigBuilder timeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    /** Default constructor */
    private CacheConfigBuilder() {
    }

    /**
     * 从 JSON 串获取对应的缓存配置实例。
     *
     * @param json 缓存值配置的 JSON 表示
     * @return 缓存值配置实例
     */
    public static CacheConfigBuilder fromJSON(String json) {
        return JSON.parseObject(json, CacheConfigBuilder.class);
    }


}

