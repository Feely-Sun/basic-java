package com.basic.leanring.java.indicator.model;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.alipay.common.tracer.context.AbstractLogContext;
import com.google.common.base.Objects;

/**
 * @author sunzihan
 * @version $Id: IndicatorValue.java V 0.1 3/15/17 15:50 sunzihan EXP $
 */
public class IndicatorValue {
    private static final Logger logger   = LoggerFactory.getLogger(IndicatorValue.class);

    /**
     * 指标值所在域
     */
    public static final int NO_DOMAIN  = 0; // 0000 0000

    /**
     * 既不是新计算的指标，也不是使用到的指标
     */
    public static final int DEFAULT      = 1 << 0; // 0000 0001

    /**
     * 新计算的指标：
     * <ul>
     * <li>该业务节点预计算的指标</li>
     * <li>该业务节点调用指标计算服务产生的指标（非缓存、透传得到）</li>
     * </ul>
     */
    public static final int NEW          = 1 << 1; // 0000 0010

    /**
     * 使用到的指标：
     * <ul>
     * <li>该业务节点需要调用指标计算服务获取值的相关指标（可能在缓存中、透传指标中、预计算得到的指标中）</li>
     * </ul>
     */
    public static final int USED         = 1 << 2; // 0000 0100

    /** 是预计算的指标 */
    public static final int PRE_CAL      = 1 << 3; // 0000 1000

    /** 是透传的指标 */
    public static final int TRANS_IN     = 1 << 4; // 0001 0000

    /** 标识新快照的指标 */
    public static final int COMPRESS_NEW = 1 << 5; //0010 0000

    public static final int POLICY       = 1 << 7;  //1000 0000

    /** 指标 ID */
    private final int       indicatorId;

    /** 指标的值 */
    private final Object    value;

    /** 关联的事件 ID */
    private final String    eventId;

    /** 指标值元类型：被该业务节点使用的指标值、该业务节点新计算的指标值等等 */
    private volatile int    metaType     = DEFAULT;

    /**指标值所在域元类型*/
    private volatile int    metaDomain = NO_DOMAIN;


    /**
     * Constructor using fields.
     * @param eventId 事件 ID
     * @param indicatorId 指标的标识符
     * @param value 指标的计算值
     */
    public IndicatorValue(String eventId, int indicatorId, Object value) {
        this.eventId = eventId;
        this.indicatorId = indicatorId;
        this.value = value;
    }

    /**
     * 将该指标计算值设置为新计算的值。
     *
     * @return 指标计算值自身
     */
    public IndicatorValue toNew() {
        this.addMetaType(NEW);
        this.addMetaType(COMPRESS_NEW);
        return this;
    }

    /**
     * 移除「新计算」元类型。
     *
     * @return 指标值自身
     */
    public IndicatorValue remNew() {
        this.remMetaType(NEW);
        return this;
    }

    /**
     * 判断该指标值是否为新计算的指标值。
     *
     * @return 如果该指标值是新计算的指标值，那么返回 {@code true} 否则返回 {@code false}
     */
    public boolean isNew() {
        return this.isMetaTypeOf(NEW);
    }

    /**
     * 将该指标计算值设置为被使用的值。
     *
     * @return 指标计算值自身
     */
    public IndicatorValue toUsed() {

        this.addMetaType(USED);
        //如果上下文为null则走老逻辑，即老CTU无需考虑多域并发问题
        if (isTmode()==false) {
            if(logger.isDebugEnabled()){
                logger.debug("indicatorId:" + indicatorId + ",to used,domain is null");
            }
            return this;
        }
        //拿出offset
        int offset = getDomain();
        //盗用域0001  综合域0010
        int domain=1 << offset;
        //增加域信息
        addMetaDomain(domain);
        if(logger.isDebugEnabled()){
            logger.debug("indicatorId:" + indicatorId + ",to used,domain is not null,metaDomain:"
                    + this.metaDomain);
        }
        return this;
    }

    /**
     * 从上下文中获取域信息（前置有判断，两个分支不可能同时为空）
     *
     * @return
     */
    private static int getDomain() {
//        if (IndicatorDomainContext.get().getDomain() != null) {
//            return IndicatorDomainContext.get().getDomain();
//        } else {
//            AbstractLogContext ctx = AbstractLogContext.get();
//            return Integer.parseInt(ctx.getPenetrateAttribute(DomainEnum.domainKey));
//        }

        return 1;
    }

    /**
     * 移除「已使用」元类型。
     *
     * @return 指标值自身
     */
    public IndicatorValue remUsed() {
        //如果上下文为null则走老逻辑，即老CTU无需考虑多域并发问题
        if (isTmode()==false) {
            if(logger.isDebugEnabled()){
                logger.debug("indicatorId:" + indicatorId + ",remUsed,domain is null");
            }
            this.remMetaType(USED);
            return this;
        }

        //如果线程上下文的域不是当前域 则直接返回
        if(!isRightDomain()){
            if(logger.isDebugEnabled()){
                logger.debug("indicatorId:"+indicatorId+",remUsed,is not right domain");
            }
            return this;
        }

        //如果线程上下文的域是当前域 则先移除当前域信息
        remMetaDomain(getDomain());

        //只有当全部域信息都被清除 才最后清除used信息
        if(this.metaDomain==NO_DOMAIN){
            if(logger.isDebugEnabled()){
                logger.debug("indicatorId:"+indicatorId+",remUsed,NO_DOMAIN");
            }
            this.remMetaType(USED);
            return this;
        }
        if(logger.isDebugEnabled()){
            logger.debug("indicatorId:"+indicatorId+",remUsed,is not NO_DOMAIN,no remUsed");
        }
        return this;
    }

    /**
     * 判断该指标值是否是被使用的指标值。
     *
     * @return 如果是被使用的指标值，那么返回 {@code true} 否则返回 {@code false}
     */
    public boolean isUsed() {
        //如果上下文为null则走老逻辑，即老CTU无需考虑多域并发问题
        if (isTmode()==false) {
            if(logger.isDebugEnabled()){
                logger.debug("indicatorId:" + indicatorId + ",isUsed,domain is null");
            }
            return this.isMetaTypeOf(USED);
        }
        //如果上下文不为null并且indicatorValue属于当前域 则进一步判定是否used
        if(isRightDomain()){
            if(logger.isDebugEnabled()){
                logger.debug("indicatorId:" + indicatorId
                        + ",isUsed,domain is not null,and in now metaDomain:"
                        + this.metaDomain);
            }
            return this.isMetaTypeOf(USED);
        }
        //不属于当前域 则直接return false
        if(logger.isDebugEnabled()){
            logger.debug("indicatorId:" + indicatorId
                    + ",isUsed,domain is not null,and not in now metaDomain:"
                    + this.metaDomain);
        }
        return false;
    }

    /**
     * 将该指标计算值设置为预计算得到的值。
     *
     * @return 指标值自身
     */
    public IndicatorValue toPreCal() {
        this.addMetaType(PRE_CAL);
        return this;
    }

    /**
     * 移除「预计算」元类型。
     *
     * @return 指标值自身
     */
    public IndicatorValue remPreCal() {
        this.remMetaType(PRE_CAL);
        return this;
    }

    /**
     * 判断该指值是否为预计算得到的。
     *
     * @return 如果是预计算得到的指标值返回 {@code true}，否则返回 {@code false}
     */
    public boolean isPreCal() {
        return this.isMetaTypeOf(PRE_CAL);
    }

    /**
     * 将该指标计算值设置为透传得到的值。
     *
     * @return 指标值自身
     */
    public IndicatorValue toTransIn() {
        this.addMetaType(TRANS_IN);
        return this;
    }

    /**
     * 移除「透传」元类型。
     *
     * @return 指标值自身
     */
    public IndicatorValue remTransIn() {
        this.remMetaType(TRANS_IN);
        return this;
    }

    /**
     * 判断该指标值是否为透传得到的。
     *
     * @return 如果是透传得到的指标值则返回 {@code true}，否则返回 {@code false}
     */
    public boolean isTransIn() {
        return this.isMetaTypeOf(TRANS_IN);
    }

    /**
     * 将该指标设置为新快照体系中的新计算指标
     * 此标志在切换期使用 后续可以和new标志合并
     *
     * @return 指标计算值自身
     */
    public IndicatorValue remCompressNew() {
        this.remMetaType(COMPRESS_NEW);
        return this;
    }



    /**
     * 判断该指标是否需要快照
     *  新指标 && 未保存 && 快照策略为存储
     * @return
     */
    public boolean isNeedSnapshot(){
        return isMetaTypeOf(COMPRESS_NEW) &&  isMetaTypeOf(POLICY);
    }

    /**
     * 设置指标的快照策略  如果未设置策略或策略为null 默认为存储
     * @param policy 策略值 1 存储 0 不存储
     * @return
     */
    public IndicatorValue setPolicy(Integer policy) {

        if (policy != null && policy == IndicatorSnapshotConstant.SNAPSHOT_POLICY_UNSAVE) {
            this.remMetaType(POLICY);
            return this;
        }

        this.addMetaType(POLICY);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.indicatorId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof IndicatorValue)) {
            return false;
        }
        IndicatorValue other = (IndicatorValue) obj;

        return Objects.equal(this.indicatorId, other.indicatorId);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IndicatorValue [indicatorId=");
        builder.append(indicatorId);
        builder.append(", value=");
        builder.append(value);
        builder.append(", eventId=");
        builder.append(eventId);
        builder.append(", metaType=");
        builder.append(metaType);
        builder.append(", metaDomain=");
        builder.append(metaDomain);
        builder.append("]");
        return builder.toString();
    }

    /**
     * 是否处于T分层结构内
     * @return
     */
    private boolean isTmode(){
        AbstractLogContext ctx = AbstractLogContext.get();
//        if (IndicatorDomainContext.get().getDomain() == null&&
//                (ctx == null || ctx.getPenetrateAttribute(DomainEnum.domainKey) == null)){
//            return false;
//        }else{
//            return true;
//        }

        return false;
    }

    /**
     * 从线程上下文拿出当前域，判定当前indicatorValue是否属于当前域
     * @return
     */
    private boolean isRightDomain(){
        int offset = getDomain();

        int domain=1 << offset;

        //代表当前线程上下文里的域与值中的域符合
        if((this.metaDomain & domain)!=0){
            return true;
        }
        return false;
    }

    /**
     * 设置该指标值的元类型。
     *
     * @param type 要设置的元类型
     */
    private void addMetaType(int type) {
        this.metaType = this.metaType | type;
    }

    /**
     * 设置该指标值的元类型。
     *
     */
    private void addMetaDomain(int offset) {
        this.metaDomain = this.metaDomain | offset;
    }



    /**
     * 移除该指标值的元类型。
     *
     * @param type 要移除的元类型
     */
    private void remMetaType(int type) {
        synchronized (this) {
            if (isMetaTypeOf(type)) {
                this.metaType = this.metaType ^ type;
            }
        }
    }

    /**
     * 移除该指标值的元类型。
     *
     */
    private void remMetaDomain(int offset) {
        int domain=1<<offset;
        synchronized (this) {
            if (isMetaDomainOf(domain)) {
                this.metaDomain = this.metaDomain ^ domain;
            }
        }
    }

    /**
     * 判断该指标值上下文元类型是否为期望的某种类型。
     *
     * @param type 指标值上下文元类型
     * @return 如果是期望的元类型，那么返回 {@code true}，否则返回 {@code fals}
     */
    private boolean isMetaTypeOf(int type) {
        return (type & ~this.metaType) == 0;
    }

    /**
     * 判断该指标值上下文元类型是否为期望的某种类型。

     * @return 如果是期望的元类型，那么返回 {@code true}，否则返回 {@code fals}
     */
    private boolean isMetaDomainOf(int domain) {
        return (domain & ~this.metaDomain) == 0;
    }

    // ----------------------- Getters and setters

    public int getIndicatorId() {
        return indicatorId;
    }

    public Object getValue() {
        return value;
    }

    public int getMetaType() {
        return metaType;
    }

    public String getEventId() {
        return eventId;
    }

    /**
     * @return the metaDomain
     */
    public int getMetaDomain() {
        return metaDomain;
    }

    /**
     * 获取当前线程的domainKey
     * <li>线程ID+固定key</li>
     *
     * @return
     */
    public static String getCurrentDomainKey() {
        StringBuffer strBuf = new StringBuffer();
       // strBuf.append(DomainEnum.domainKey);
        strBuf.append(Thread.currentThread().getId());
        return strBuf.toString();
    }



}

