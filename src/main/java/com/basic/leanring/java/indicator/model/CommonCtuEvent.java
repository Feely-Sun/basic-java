package com.basic.leanring.java.indicator.model;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.common.lang.StringUtil;

/**
 * @author sunzihan
 * @version $Id: CommonCtuEvent.java V 0.1 3/15/17 15:44 sunzihan EXP $
 */
public class CommonCtuEvent extends BaseDTO {

    /** 序列化ID */
    private static final long serialVersionUID = 900130025654071277L;
    /**
     * 事件UUID
     */
    private String id;

    /**
     * 事件配置uuid
     */
    private String eventUuid;

    /**
     * 触发事件的UserId
     */
    private String userId;

    /**
     * 触发事件的AccountNo
     */
    private String accountNo;

    /**
     * 触发事件的OperatorId
     */
    private String operatorId;

    /**
     * 触发事件的外部Id
     */
    private String externalId;

    /**
     * 触发事件的客户端IP，由控件提供
     */
    private String clientIp;

    /**
     * 触发事件的客户端Mac地址，由控件提供
     */
    private String clientMac;

    /**
     * 触发事件的客户端ID，有控件提供
     */
    private String clientId;

    /**
     * 事件发生的ServerId
     */
    private String serverId;

    /**
     * 事件发生所在的ModuleId
     */
    private String moduleId;

    /**
     * 事件发生时间
     */
    private Date gmtOccur;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 事件的主目标
     */
    private String eventMainTarget;

    /** 事件主目标的类型，如银行卡或账户 */
    private String eventMainTargetType;

    /**
     * 事件辅助目标
     */
    private String eventSuppTarget;

    /** 事件辅助目标的类型，如银行卡或账户 */
    private String eventSuppTargetType;

    /** 会话ID */
    private String sessionId;

    /** 事件所属的时间分区 */
    private String partitionKey;

    /** 客户端PCID Mac */
    private String clientPCIDMac;

    /** 客户端PCID Hwid */
    private String clientPCIDHwid;

    /** 客户端PCID Guid */
    private String clientPCIDGuid;

    /** 存放事件的扩展属性 */
    private Map<String, String> properties = new HashMap<String, String>();

    /** 用户客户端表主键,用于PCID与事件表的关联 **/
    private long   userClientId;
    /**
     * 用户客户端PCID
     */
    private String pcid;

    // -----ctu umid项目start-----
    /** UMID的硬ID版本 */
    private String umidHardVersion;

    /** UMID中SoftId中的系统信息 */
    private String umidSysInfo;

    /** UMID中SoftId中的硬件信息 */
    private String umidHardInfo;

    /** UMID中SoftId中的软件信息 */
    private String umidSoftInfo;

    /** UMID中SoftId中的RID */
    private String umidRID;

    /** UMID的软ID版本 */
    private String umidSoftVersion;

    /** 因子风险ID列表 */
    private List<String> factorRiskList = new ArrayList<String>();

    /** 扩展信息 */
    private Map<String, Object> extendData = new ConcurrentHashMap<String, Object>();

    /**
     * 基础信息集合，如isStore, isProfile等....
     * 具体数据类型:@see com.alipay.ctu.service.event.model.enums.EventConfigInfoKey
     * 获取示例：boolean isStore = getBaseInfoData(EventConfigInfoKey.IS_STORE);
     *
     */
    private Map<String, Object> baseInfoData = new ConcurrentHashMap<String, Object>();

    /** 子订单信息 */
    private List<CommonCtuEvent> subEvents = new ArrayList<CommonCtuEvent>();

    /**
     * 添加因子风险
     *
     * @param factorRisk
     */
    public void addFactorRisk(String factorRisk) {
        if (StringUtil.isBlank(factorRisk) || factorRiskList.contains(factorRisk)) {
            return;
        }
        factorRiskList.add(factorRisk);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("EVENT");
        sb.append("-");
        sb.append(this.id);
        sb.append("-");
        sb.append(this.gmtOccur);

        return sb.toString();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CommonCtuEvent)) {
            return false;
        }

        CommonCtuEvent other = (CommonCtuEvent) o;
        if (this.getId() == null || other.getId() == null) {
            return false;
        }

        return StringUtil.equals(this.getId(), other.getId());
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return 0;
        } else {
            return getId().hashCode();
        }
    }

    /**
     * set single property
     * @param name
     * @param value
     */
    public void setProperty(String name, String value) {
        if (StringUtil.trimToNull(name) == null || value == null || StringUtil.equalsIgnoreCase("null", value)) {
            return;
        }
        properties.put(name, value);
    }

    /**
     * get single property
     * @param name
     * @return
     */
    public String getProperty(String name) {
        return properties.get(name);
    }

    /**
     * set all property
     * @param properties
     */
    public void setProperties(Map<String, String> properties) {
        if (properties == null) {
            return;
        }
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            setProperty(entry.getKey(), entry.getValue());
        }
    }

    /**
     * get all property
     * @return
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    // ========== getters and setters ==========

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter method for property <tt>accountNo</tt>.
     *
     * @return property value of accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * Setter method for property <tt>accountNo</tt>.
     *
     * @param accountNo
     *            value to be assigned to property accountNo
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * Getter method for property <tt>eventMainTargetType</tt>.
     *
     * @return property value of eventMainTargetType
     */
    public String getEventMainTargetType() {
        return eventMainTargetType;
    }

    /**
     * Setter method for property <tt>eventMainTargetType</tt>.
     *
     * @param eventMainTargetType
     *            value to be assigned to property eventMainTargetType
     */
    public void setEventMainTargetType(String eventMainTargetType) {
        this.eventMainTargetType = eventMainTargetType;
    }

    /**
     * Getter method for property <tt>eventSuppTargetType</tt>.
     *
     * @return property value of eventSuppTargetType
     */
    public String getEventSuppTargetType() {
        return eventSuppTargetType;
    }

    /**
     * Setter method for property <tt>eventSuppTargetType</tt>.
     *
     * @param eventSuppTargetType
     *            value to be assigned to property eventSuppTargetType
     */
    public void setEventSuppTargetType(String eventSuppTargetType) {
        this.eventSuppTargetType = eventSuppTargetType;
    }

    /**
     * Getter method for property <tt>sessionId</tt>.
     *
     * @return property value of sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Setter method for property <tt>sessionId</tt>.
     *
     * @param sessionId
     *            value to be assigned to property sessionId
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Getter method for property <tt>partitionKey</tt>.
     *
     * @return property value of partitionKey
     */
    public String getPartitionKey() {
        return partitionKey;
    }

    /**
     * Setter method for property <tt>partitionKey</tt>.
     *
     * @param partitionKey
     *            value to be assigned to property partitionKey
     */
    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    /**
     * Getter method for property <tt>clientPCIDMac</tt>.
     *
     * @return property value of clientPCIDMac
     */
    public String getClientPCIDMac() {
        return clientPCIDMac;
    }

    /**
     * Setter method for property <tt>clientPCIDMac</tt>.
     *
     * @param clientPCIDMac
     *            value to be assigned to property clientPCIDMac
     */
    public void setClientPCIDMac(String clientPCIDMac) {
        this.clientPCIDMac = clientPCIDMac;
    }

    /**
     * Getter method for property <tt>clientPCIDHwid</tt>.
     *
     * @return property value of clientPCIDHwid
     */
    public String getClientPCIDHwid() {
        return clientPCIDHwid;
    }

    /**
     * Setter method for property <tt>clientPCIDHwid</tt>.
     *
     * @param clientPCIDHwid
     *            value to be assigned to property clientPCIDHwid
     */
    public void setClientPCIDHwid(String clientPCIDHwid) {
        this.clientPCIDHwid = clientPCIDHwid;
    }

    /**
     * Getter method for property <tt>clientPCIDGuid</tt>.
     *
     * @return property value of clientPCIDGuid
     */
    public String getClientPCIDGuid() {
        return clientPCIDGuid;
    }

    /**
     * Setter method for property <tt>clientPCIDGuid</tt>.
     *
     * @param clientPCIDGuid
     *            value to be assigned to property clientPCIDGuid
     */
    public void setClientPCIDGuid(String clientPCIDGuid) {
        this.clientPCIDGuid = clientPCIDGuid;
    }

    /**
     * Getter method for property <tt>userClientId</tt>.
     *
     * @return property value of userClientId
     */
    public long getUserClientId() {
        return userClientId;
    }

    /**
     * Setter method for property <tt>userClientId</tt>.
     *
     * @param userClientId
     *            value to be assigned to property userClientId
     */
    public void setUserClientId(long userClientId) {
        this.userClientId = userClientId;
    }

    /**
     * Getter method for property <tt>pcid</tt>.
     *
     * @return property value of pcid
     */
    public String getPcid() {
        return pcid;
    }

    /**
     * Setter method for property <tt>pcid</tt>.
     *
     * @param pcid
     *            value to be assigned to property pcid
     */
    public void setPcid(String pcid) {
        this.pcid = pcid;
    }

    /**
     * Getter method for property <tt>umidHardVersion</tt>.
     *
     * @return property value of umidHardVersion
     */
    public String getUmidHardVersion() {
        return umidHardVersion;
    }

    /**
     * Setter method for property <tt>umidHardVersion</tt>.
     *
     * @param umidHardVersion
     *            value to be assigned to property umidHardVersion
     */
    public void setUmidHardVersion(String umidHardVersion) {
        this.umidHardVersion = umidHardVersion;
    }

    /**
     * Getter method for property <tt>umidSysInfo</tt>.
     *
     * @return property value of umidSysInfo
     */
    public String getUmidSysInfo() {
        return umidSysInfo;
    }

    /**
     * Setter method for property <tt>umidSysInfo</tt>.
     *
     * @param umidSysInfo
     *            value to be assigned to property umidSysInfo
     */
    public void setUmidSysInfo(String umidSysInfo) {
        this.umidSysInfo = umidSysInfo;
    }

    /**
     * Getter method for property <tt>umidHardInfo</tt>.
     *
     * @return property value of umidHardInfo
     */
    public String getUmidHardInfo() {
        return umidHardInfo;
    }

    /**
     * Setter method for property <tt>umidHardInfo</tt>.
     *
     * @param umidHardInfo
     *            value to be assigned to property umidHardInfo
     */
    public void setUmidHardInfo(String umidHardInfo) {
        this.umidHardInfo = umidHardInfo;
    }

    /**
     * Getter method for property <tt>umidSoftInfo</tt>.
     *
     * @return property value of umidSoftInfo
     */
    public String getUmidSoftInfo() {
        return umidSoftInfo;
    }

    /**
     * Setter method for property <tt>umidSoftInfo</tt>.
     *
     * @param umidSoftInfo
     *            value to be assigned to property umidSoftInfo
     */
    public void setUmidSoftInfo(String umidSoftInfo) {
        this.umidSoftInfo = umidSoftInfo;
    }

    /**
     * Getter method for property <tt>umidRID</tt>.
     *
     * @return property value of umidRID
     */
    public String getUmidRID() {
        return umidRID;
    }

    /**
     * Setter method for property <tt>umidRID</tt>.
     *
     * @param umidRID
     *            value to be assigned to property umidRID
     */
    public void setUmidRID(String umidRID) {
        this.umidRID = umidRID;
    }

    /**
     * Getter method for property <tt>umidSoftVersion</tt>.
     *
     * @return property value of umidSoftVersion
     */
    public String getUmidSoftVersion() {
        return umidSoftVersion;
    }

    /**
     * Setter method for property <tt>umidSoftVersion</tt>.
     *
     * @param umidSoftVersion
     *            value to be assigned to property umidSoftVersion
     */
    public void setUmidSoftVersion(String umidSoftVersion) {
        this.umidSoftVersion = umidSoftVersion;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id
     *            value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>userId</tt>.
     *
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     *
     * @param userId
     *            value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>operatorId</tt>.
     *
     * @return property value of operatorId
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * Setter method for property <tt>operatorId</tt>.
     *
     * @param operatorId
     *            value to be assigned to property operatorId
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * Getter method for property <tt>externalId</tt>.
     *
     * @return property value of externalId
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Setter method for property <tt>externalId</tt>.
     *
     * @param externalId
     *            value to be assigned to property externalId
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     * Getter method for property <tt>clientIp</tt>.
     *
     * @return property value of clientIp
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * Setter method for property <tt>clientIp</tt>.
     *
     * @param clientIp
     *            value to be assigned to property clientIp
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * Getter method for property <tt>clientMac</tt>.
     *
     * @return property value of clientMac
     */
    public String getClientMac() {
        return clientMac;
    }

    /**
     * Setter method for property <tt>clientMac</tt>.
     *
     * @param clientMac
     *            value to be assigned to property clientMac
     */
    public void setClientMac(String clientMac) {
        this.clientMac = clientMac;
    }

    /**
     * Getter method for property <tt>clientId</tt>.
     *
     * @return property value of clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Setter method for property <tt>clientId</tt>.
     *
     * @param clientId
     *            value to be assigned to property clientId
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Getter method for property <tt>serverId</tt>.
     *
     * @return property value of serverId
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * Setter method for property <tt>serverId</tt>.
     *
     * @param serverId
     *            value to be assigned to property serverId
     */
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    /**
     * Getter method for property <tt>moduleId</tt>.
     *
     * @return property value of moduleId
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * Setter method for property <tt>moduleId</tt>.
     *
     * @param moduleId
     *            value to be assigned to property moduleId
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * Getter method for property <tt>gmtOccur</tt>.
     *
     * @return property value of gmtOccur
     */
    public Date getGmtOccur() {
        return gmtOccur;
    }

    /**
     * Setter method for property <tt>gmtOccur</tt>.
     *
     * @param gmtOccur
     *            value to be assigned to property gmtOccur
     */
    public void setGmtOccur(Date gmtOccur) {
        this.gmtOccur = gmtOccur;
    }

    /**
     * Getter method for property <tt>eventType</tt>.
     *
     * @return property value of eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Setter method for property <tt>eventType</tt>.
     *
     * @param eventType
     *            value to be assigned to property eventType
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * Getter method for property <tt>eventName</tt>.
     *
     * @return property value of eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Setter method for property <tt>eventName</tt>.
     *
     * @param eventName
     *            value to be assigned to property eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Getter method for property <tt>eventMainTarget</tt>.
     *
     * @return property value of eventMainTarget
     */
    public String getEventMainTarget() {
        return eventMainTarget;
    }

    /**
     * Setter method for property <tt>eventMainTarget</tt>.
     *
     * @param eventTargetMain
     *            value to be assigned to property eventMainTarget
     */
    public void setEventMainTarget(String eventTargetMain) {
        this.eventMainTarget = eventTargetMain;
    }

    /**
     * Getter method for property <tt>eventSuppTarget</tt>.
     *
     * @return property value of eventSuppTarget
     */
    public String getEventSuppTarget() {
        return eventSuppTarget;
    }

    /**
     * Setter method for property <tt>eventSuppTarget</tt>.
     *
     * @param eventTargetSupp
     *            value to be assigned to property eventSuppTarget
     */
    public void setEventSuppTarget(String eventTargetSupp) {
        this.eventSuppTarget = eventTargetSupp;
    }

    /**
     * Getter method for property <tt>factorRiskList</tt>.
     *
     * @return property value of factorRiskList
     */
    public List<String> getFactorRiskList() {
        return factorRiskList;
    }

    /**
     * Setter method for property <tt>factorRiskList</tt>.
     *
     * @param factorRiskList
     *            value to be assigned to property factorRiskList
     */
    public void setFactorRiskList(List<String> factorRiskList) {
        this.factorRiskList = factorRiskList;
    }

    /**
     * Getter method for property <tt>extendData</tt>.
     *
     * @return property value of extendData
     */
    public Map<String, Object> getExtendData() {
        return extendData;
    }

    /**
     * Setter method for property <tt>extendData</tt>.
     *
     * @param extendData
     *            value to be assigned to property extendData
     */
    public void setExtendData(Map<String, Object> extendData) {
        this.extendData = extendData;
    }

    /**
     * @return the eventUuid
     */
    public String getEventUuid() {
        return eventUuid;
    }

    /**
     * @param eventUuid
     *            the eventUuid to set
     */
    public void setEventUuid(String eventUuid) {
        this.eventUuid = eventUuid;
    }

    /**
     * toFullString
     *
     * @return
     */
    public String toFullString() {
        return this.getId() + "," + this.getUserId() + "," + this.getProperties();
    }

    /**
     * @return baseInfoData
     */
    public Map<String, Object> getBaseInfoData() {
        return baseInfoData;
    }

    /**
     * Getter method for property <tt>baseInfoData</tt>.
     * @param key
     * @return baseInfoData.value
     */
    public Object getBaseInfo(String key) {
        return baseInfoData.get(key);
    }

    /**
     * Getter method for property <tt>baseInfoData</tt>.
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getBaseInfoData(String key) {
        T obj = (T) baseInfoData.get(key);
        return obj;
    }

    /**
     * Setter method for property <tt>baseInfoData</tt>.
     *
     * @param baseInfoData
     */
    public void setBaseInfoData(Map<String, Object> baseInfoData) {
        this.baseInfoData = baseInfoData;
    }

    /**
     * Add Base Info Data
     *
     * @param key
     * @param value
     */
    public void addBaseInfo(String key, Object value) {
        this.baseInfoData.put(key, value);
    }

    /**
     * Getter method for property <tt>subEvents</tt>.
     *
     * @return property value of subEvents
     */
    public List<CommonCtuEvent> getSubEvents() {
        return subEvents;
    }

    /**
     * Setter method for property <tt>subEvents</tt>.
     *
     * @param subEvents
     */
    public void setSubEvents(List<CommonCtuEvent> subEvents) {
        this.subEvents = subEvents;
    }

    /**
     * Add Sub Event
     *
     * @param subEvent
     */
    public void addSubEvent(CommonCtuEvent subEvent) {
        this.subEvents.add(subEvent);
    }

    /**
     * Add event property
     *
     * @param key
     * @param value
     */
    public void setEventProperty(String key, String value) {
        if (StringUtil.isNotBlank(key) && StringUtil.isNotBlank(value) && !StringUtil.equalsIgnoreCase(value, "null")) {
            this.properties.put(key, value);
        }
    }

    /**
     * Getter method for property <tt>properties</tt>.
     * @param key
     * @return
     */
    public String getEventProperty(String key) {
        return this.properties.get(key);
    }

    /**
     * 获取主事件ID
     * @return
     */
    public String getPid() {
        if (StringUtil.lastIndexOf(this.id, "_") > -1) {
            return StringUtil.substringBeforeLast(this.id, "_");
        }
        return this.id;
    }
}

