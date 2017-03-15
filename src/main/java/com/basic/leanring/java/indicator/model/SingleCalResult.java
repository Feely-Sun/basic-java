package com.basic.leanring.java.indicator.model;

import com.basic.leanring.java.indicator.IndicatorResult;

import java.util.Map;

/**
 * @author sunzihan
 * @version $Id: SingleCalResult.java V 0.1 3/15/17 15:40 sunzihan EXP $
 */
public class SingleCalResult extends IndicatorResult<Object> {

    /**  */
    private static final long serialVersionUID = 1L;

    /**当前结果所属的指标的唯一性标识:default=-1*/
    private int               indicatorId      = -1;

    /**当前结果所属协议*/
    private String            protocol;

    /**指标名称*/
    private String            name;

    /**
     * 无参构造
     */
    public SingleCalResult() {
        super();
    }

    /**
     * @param success
     * @param result
     */
    public SingleCalResult(boolean success, int indicatorId, String protocol, Object result) {
        this(success, result, indicatorId, protocol, null);
    }

    public SingleCalResult(boolean success, Object result, int indicatorId, String protocol,
                           Map<Integer, Object> otherResult) {
        super(success, result, otherResult);
        this.protocol = protocol;
        this.indicatorId = indicatorId;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param success
     * @param result
     */
    public SingleCalResult(boolean success, Object result) {
        super(success, result);
    }

    /**
     * @param indicatorId
     */
    public SingleCalResult(int indicatorId, boolean success, Object result) {
        super(success, result);
        this.indicatorId = indicatorId;
    }

    /**
     * Getter method for property <tt>indicatorId</tt>.
     *
     * @return property value of indicatorId
     */
    public int getIndicatorId() {
        return indicatorId;
    }

    /**
     * Setter method for property <tt>indicatorId</tt>.
     *
     * @param indicatorId value to be assigned to property indicatorId
     */
    public void setIndicatorId(int indicatorId) {
        this.indicatorId = indicatorId;
    }

    /**
     * Getter method for property <tt>protocol</tt>.
     *
     * @return property value of protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Setter method for property <tt>protocol</tt>.
     *
     * @param protocol value to be assigned to property protocol
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SingleCalResult [indicatorId=").append(indicatorId).append(", protocol=")
                .append(protocol).append(", success=").append(isSuccess()).append(", result=")
                .append(getResult()).append(", otherResult=").append(getOtherResult()).append("]");
        return builder.toString();
    }

}

