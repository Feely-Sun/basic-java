package com.basic.leanring.java.indicator;

/**
 * @author sunzihan
 * @version $Id: MetaInfo.java V 0.1 3/15/17 15:36 sunzihan EXP $
 */
public interface MetaInfo {

    /**
     * 元信息是否可用。
     *
     * @return 元信息如果可用，那么返回 {@code true} 否则返回 {@code false}
     */
    public boolean isAvailable();

}
