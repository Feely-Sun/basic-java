package com.basic.leanring.java.indicator.impl;

import com.basic.leanring.java.indicator.MetaInfo;
import com.basic.leanring.java.indicator.TransDataAdapter;
import com.basic.leanring.java.util.Cloner;
import com.google.common.collect.Maps;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @author sunzihan
 * @version $Id: IndicatorTransMeta.java V 0.1 3/15/17 15:38 sunzihan EXP $
 */
public class IndicatorTransMeta implements MetaInfo {

    // ----------------------------------------------------  Transmission

    /** 透传节点名称服务 - 方法级别 */
//    private TransNodeNameService methodTransNodeNameService;

    /** 透传节点名称 - 方法级别 */
    private String               methodTransNodeName;

    // ----------------------------------------------------  In

    /** 「流入」透传数据适配器 - 方法级别 */
    private TransDataAdapter methodTransInDataAdapter;

    /** 包含「流入」透传数据的参数索引 */
    private int                  idxOfIn  = -1;

    /** 「流入」透传数据适配器 - 参数级别 */
    private TransDataAdapter     argTransInDataAdapter;

    // ----------------------------------------------------  Out

    /** 「流出」透传数据适配器 - 方法级别 */
    private TransDataAdapter     methodTransOutDataAdapter;

    /** 包含「流出」透传数据的参数索引 */
    private int                  idxOfOut = -1;

    /** 「流出」透传数据适配器 - 参数级别 */
    private TransDataAdapter     argTransOutDataAdapter;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isUpIn()) {
            sb.append("UP_IN").append("|");
        }

        if (isUpOut()) {
            sb.append("UP_OUT").append("|");
        }

        if (isDownIn()) {
            sb.append("DOWN_IN").append("|");
        }

        if (isDownOut()) {
            sb.append("DOWN_OUT").append("|");
        }

        int i = sb.lastIndexOf("|");
        if (i != -1) {
            sb.deleteCharAt(i);
        }

        return String.valueOf(sb.toString());
    }


    @Override
    public boolean isAvailable() {
        boolean isInOutSetup = (argTransInDataAdapter != null && idxOfIn >= 0)
                || (argTransOutDataAdapter != null && idxOfOut >= 0)
                || (methodTransOutDataAdapter != null)
                || (methodTransInDataAdapter != null);

        boolean isTransmissionSetup = StringUtils.isNotBlank(methodTransNodeName);

        if (isInOutSetup) {
            return isTransmissionSetup;
        } else {
            return isTransmissionSetup;
        }
    }

    /**
     * 获取透传节点的名称。
     *
     * @param invocation See {@link MethodInvocation}
     * @return 透传节点名称
     */
    public String getTransNodeName(MethodInvocation invocation) {
//        if (StringUtils.isNotBlank(methodTransNodeName)) {
//            return methodTransNodeName;
//        } else if (methodTransNodeNameService != null) {
//            return methodTransNodeNameService.getTransNodeName(invocation);
//        }
        return "";
    }

    /**
     * 判断是否向上透传。
     *
     * @return 如果是向上透传，那么返回 {@code true}，否则返回 {@code false}
     */
    public boolean isTransUp() {
        return isUpIn() || isUpOut();
    }

    /**
     * 判断是否向下透传。
     *
     * @return 如果是向下透传，那么返回 {@code true}，否则返回 {@code false}
     */
    public boolean isTransDown() {
        return isDownIn() || isDownOut();
    }

    /**
     * 判断是否需要进行透传处理。
     *
     * @return 如果需要进行透传处理，那么返回 {@code true}，否则返回 {@code false}
     */
    public boolean isNeedTrans() {
        return isDownIn() || isDownOut() || isUpIn() || isUpOut();
    }

    /**
     * 判断本次方法调用是否需要处理上游节点「向下流入」到本业务节点的透传数据。
     *
     * @return 如果需要处理「向下流入」的透传数据，那么返回 {@link true} 否则返回 {@link false}
     */
    public boolean isDownIn() {
        return idxOfIn != -1 && argTransInDataAdapter != null;
    }

    /**
     * 判断本次方法调用是否需要处理本节点「向下流出」到下游节点的透传数据。
     *
     * @return 如果需要处理「向下流出」到下游节点的透传数据
     */
    public boolean isDownOut() {
        return idxOfOut != -1 && argTransOutDataAdapter != null;
    }

    /**
     * 判断本次方法调用是否需要处理下游节点「向上流入」到本节点的透传数据。
     *
     * @return 如果需要处理「向上流入」的透传数据，那么返回 {@code true} 否则返回 {@code false}
     */
    public boolean isUpIn() {
        return methodTransInDataAdapter != null;
    }

    /**
     * 判断本次方法调用是否需要处理本节点「向上流出」到上游节点的透传数据。
     *
     * @return 如果需要处理「向上流出」的透传数据，那么返回 {@link true} 否则返回 {@code false}
     */
    public boolean isUpOut() {
        return methodTransOutDataAdapter != null;
    }

    /**
     * 获取「向下」透传的载体，向下透传在方法执行执行之前，针对方法参数列表进行处理。
     *
     * @param args 方法调用参数
     * @return 透传数据载体
     */
    public Map<String, Object> getDownTransport(Object[] args) {
        if (isDownIn()) {
            return getTransportFromArgs(argTransInDataAdapter, idxOfIn, args);
        } else if (isDownOut()) {
            return getTransportFromArgs(argTransOutDataAdapter, idxOfOut, args);
        } else {
            return Maps.newHashMap();
        }
    }

    /**
     * 获取「向上」透传的载体，向上透传在方法执行之后，针对方法返回值进行处理。
     *
     * @param ret 方法调用返回值
     * @return 透传数据载体
     */
    public Map<String, Object> getUpTransport(Object ret) {
        if (isUpIn()) {
            return getTransportFromRet(methodTransInDataAdapter, ret);
        } else if (isUpOut()) {
            return getTransportFromRet(methodTransOutDataAdapter, ret);
        } else {
            return Maps.newHashMap();
        }
    }

    /**
     * 从方法调用参数列表获取透传载体。
     *
     * @param argAdapter
     * @param idx
     * @param args
     * @return
     */
    private Map<String, Object> getTransportFromArgs(TransDataAdapter argAdapter, int idx,
                                                     Object[] args) {
        if (idx < 0 || args == null || args.length <= 0 || argAdapter == null) {
            return null;
        }

        Object target = getTargetFromArgs(idx, args);
        Map<String, Object> transport = argAdapter.getTransport(target);
        return transport;
    }

    private Object getTargetFromArgs(int idx, Object[] args) {
        if (isDownOut()) {
            /*
             * 向下流出透传，为了防止并发调用分域透传造成数据污染
             * 这里对透传目标对象进行拷贝。
             */
            Object clone = Cloner.copy(args[idx]);
            args[idx] = clone;
            return clone;
        } else {
            return args[idx];
        }
    }

    /**
     * 从方法返回值获取透传载体。
     *
     * @param methodAdapter
     * @param ret
     * @return
     */
    private Map<String, Object> getTransportFromRet(TransDataAdapter methodAdapter, Object ret) {
        if (ret == null || methodAdapter == null) {
            return null;
        }
        Map<String, Object> transport = methodAdapter.getTransport(ret);
        return transport;
    }

    // ----------------------- Getters and setters
    public String getMethodTransNodeName() {
        return methodTransNodeName;
    }

    public void setMethodTransNodeName(String methodTransNodeName) {
        this.methodTransNodeName = methodTransNodeName;
    }

    public TransDataAdapter getMethodTransInDataAdapter() {
        return methodTransInDataAdapter;
    }

    public void setMethodTransInDataAdapter(TransDataAdapter methodTransInDataAdapter) {
        this.methodTransInDataAdapter = methodTransInDataAdapter;
    }

    public int getIdxOfIn() {
        return idxOfIn;
    }

    public void setIdxOfIn(int idxOfIn) {
        this.idxOfIn = idxOfIn;
    }

    public TransDataAdapter getArgTransInDataAdapter() {
        return argTransInDataAdapter;
    }

    public void setArgTransInDataAdapter(TransDataAdapter argTransInDataAdapter) {
        this.argTransInDataAdapter = argTransInDataAdapter;
    }

    public TransDataAdapter getMethodTransOutDataAdapter() {
        return methodTransOutDataAdapter;
    }

    public void setMethodTransOutDataAdapter(TransDataAdapter methodTransOutDataAdapter) {
        this.methodTransOutDataAdapter = methodTransOutDataAdapter;
    }

    public int getIdxOfOut() {
        return idxOfOut;
    }

    public void setIdxOfOut(int idxOfOut) {
        this.idxOfOut = idxOfOut;
    }

    public TransDataAdapter getArgTransOutDataAdapter() {
        return argTransOutDataAdapter;
    }

    public void setArgTransOutDataAdapter(TransDataAdapter argTransOutDataAdapter) {
        this.argTransOutDataAdapter = argTransOutDataAdapter;
    }


}

