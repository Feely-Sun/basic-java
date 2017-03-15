package com.basic.leanring.java.indicator;

/**
 * @author sunzihan
 * @version $Id: IOAction.java V 0.1 3/15/17 16:12 sunzihan EXP $
 */
public interface IOAction<OUT> {
    /**
     * 执行
     *
     * @return
     */
    public OUT action() throws Exception;

}
