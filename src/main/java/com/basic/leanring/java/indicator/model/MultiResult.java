package com.basic.leanring.java.indicator.model;

import com.basic.leanring.java.indicator.IndicatorResult;

import java.util.Map;

/**
 *  * <ul>
 * 批量计算结果,类型为Map<Integer,Object>
 * <li>key=指标ID(Integer)
 * <li>value=指标结果(Object)
 * </ul>
 * <p>
 *
 * <ul>
 * <font color=red>注意:{@link #toString()} 格式化输出</font>
 * <li>1.是否成功
 * <li>2.指标ID与名称映射
 * <li>3.失败列表
 * </ul>
 *
 *
 * @author sunzihan
 * @version $Id: MultiResult.java V 0.1 3/15/17 15:41 sunzihan EXP $
 */
public class MultiResult<K> extends IndicatorResult<Map<K, Object>> {

    /**  */
    private static final long serialVersionUID = 1L;

    /**
     * 指标协议与指标ID的映射:可为null
     */
    private Map<String, Integer> protocol2Id;

    /**
     * 指标名称与ID的映射,只存在与标准或者标准衍生指标
     */
    private Map<String, Integer> name2Ids;

    /**
     * 无参构造
     */
    public MultiResult() {
        super();
    }

    /**
     * @param success
     * @param result
     */
    public MultiResult(boolean success, Map<K, Object> result) {
        super(success, result);
    }

    public MultiResult(boolean success, Map<K, Object> result, Map<String, Integer> protocol2Id,
                       Map<Integer, Object> otherResult) {
        super(success, result, otherResult);
        this.protocol2Id = protocol2Id;
    }

    /**
     * Getter method for property <tt>protocol2Id</tt>.
     *
     * @return property value of protocol2Id
     */
    public Map<String, Integer> getProtocol2Id() {
        return protocol2Id;
    }

    /**
     * Setter method for property <tt>protocol2Id</tt>.
     *
     * @param protocol2Id value to be assigned to property protocol2Id
     */
    public void setProtocol2Id(Map<String, Integer> protocol2Id) {
        this.protocol2Id = protocol2Id;
    }

    /**
     * Getter method for property <tt>name2Ids</tt>.
     *
     * @return property value of name2Ids
     */
    public Map<String, Integer> getName2Ids() {
        return name2Ids;
    }

    /**
     * Setter method for property <tt>name2Ids</tt>.
     *
     * @param name2Ids value to be assigned to property name2Ids
     */
    public void setName2Ids(Map<String, Integer> name2Ids) {
        this.name2Ids = name2Ids;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MultiResult [protocol2Id=").append(protocol2Id).append(", name2Ids=")
                .append(name2Ids).append(", success()=").append(isSuccess()).append(", result=")
                .append(getResult()).append(", otherResult=").append(getOtherResult()).append("]");
        return builder.toString();
    }

}