package com.basic.leanring.java.indicator;

import java.util.Map;

/**
 * @author sunzihan
 * @version $Id: TransDataAdapter.java V 0.1 3/15/17 16:13 sunzihan EXP $
 */
public interface TransDataAdapter {

    /**
            * 从目标对象中获取透传数据载体。
            *
            * @param target 包含透传数据载体的对象
    * @return 透传载体
    */
    public Map<String, Object> getTransport(Object target);

}
