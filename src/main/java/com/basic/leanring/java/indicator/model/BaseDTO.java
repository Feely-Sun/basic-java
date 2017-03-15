package com.basic.leanring.java.indicator.model;

import com.basic.leanring.java.util.InetAddressUtils;

/**
 * @author sunzihan
 * @version $Id: BaseDTO.java V 0.1 3/15/17 15:44 sunzihan EXP $
 */
public abstract class BaseDTO {

    /** 调用来源 */
    private String callFrom;

    /** 调用IP */
    private String callIP;

    /**
     * 默认构造函数
     */
    public BaseDTO() {
        callFrom = InetAddressUtils.getServerHostName();
        callIP = InetAddressUtils.getServerIp();
    }

    /**
     * Getter method for property <tt>callFrom</tt>.
     *
     * @return property value of callFrom
     */
    public String getCallFrom() {
        return callFrom;
    }

    /**
     * Setter method for property <tt>callFrom</tt>.
     *
     * @param callFrom value to be assigned to property callFrom
     */
    public void setCallFrom(String callFrom) {
        this.callFrom = callFrom;
    }

    /**
     * Getter method for property <tt>callIP</tt>.
     *
     * @return property value of callIP
     */
    public String getCallIP() {
        return callIP;
    }

    /**
     * Setter method for property <tt>callIP</tt>.
     *
     * @param callIP value to be assigned to property callIP
     */
    public void setCallIP(String callIP) {
        this.callIP = callIP;
    }
}

