package com.basic.leanring.java.indicator;

import java.io.Serializable;
import java.util.Map;

/**
 * @author sunzihan
 * @version $Id: IndicatorResult.java V 0.1 3/15/17 15:39 sunzihan EXP $
 */
public class IndicatorResult<T> implements Serializable {
    /**  */
    private static final long    serialVersionUID = 1L;

    /**系统调用是否成功*/
    private boolean              success;

    /**计算的结果值*/
    private T                    result;

    /**其他指标结果:如内嵌的指标结果*/
    private Map<Integer, Object> otherResult;

    /**
     * 无参构造
     */
    public IndicatorResult() {
    }

    /**
     * 构造参数
     * @param success 是否成功
     * @param result 执行结果
     */
    public IndicatorResult(boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public IndicatorResult(boolean success, T result, Map<Integer, Object> otherResult) {
        super();
        this.success = success;
        this.result = result;
        this.otherResult = otherResult;
    }

    /**
     * Getter method for property <tt>success</tt>.
     *
     * @return property value of success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Setter method for property <tt>success</tt>.
     *
     * @param success value to be assigned to property success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public T getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * Getter method for property <tt>otherResult</tt>.
     *
     * @return property value of otherResult
     */
    public Map<Integer, Object> getOtherResult() {
        return otherResult;
    }

    /**
     * Setter method for property <tt>otherResult</tt>.
     *
     * @param otherResult value to be assigned to property otherResult
     */
    public void setOtherResult(Map<Integer, Object> otherResult) {
        this.otherResult = otherResult;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("IndicatorResult [success=%s, result=%s, otherResult=%s]", success,
                result, otherResult);
    }


}

