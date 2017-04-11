package com.basic.leanring.java.indicator;

import java.util.Map;

import com.basic.leanring.java.indicator.model.CommonCtuEvent;

/**
 * @author sunzihan
 * @version $Id: ELBasedTransDataAdapter.java V 0.1 3/15/17 16:13 sunzihan EXP $
 */
public class ELBasedTransDataAdapter implements TransDataAdapter {

    /** 属性访问器 */
    private ELBasedAttributeAccessor accessor;

    /**
     * 根据表达式获取对应的「流入」透传数据适配器。
     *
     * @param expr 透传数据获取表达式
     * @return 「流入」透传数据适配器
     */
    public static ELBasedTransDataAdapter newInstance(String expr) {
        ELBasedTransDataAdapter adapter = new ELBasedTransDataAdapter();
        adapter.accessor = ELBasedAttributeAccessor.newInstance(expr);
        return adapter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getTransport(Object target) {
        Object transport = accessor.getAttributeFrom(target);
        if (transport instanceof CommonCtuEvent) {
            return ((CommonCtuEvent) transport).getExtendData();
        } else if (transport instanceof Map) {
            return (Map<String, Object>) transport;
        } else {
            return null;
        }
    }
}

