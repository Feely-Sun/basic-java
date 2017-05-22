package com.basic.leanring.java.antfeature.featurelifetime.model;

import java.util.List;

/**
 * @author sunzihan
 * @version $Id: ValidResult.java V 0.1 5/8/17 19:47 sunzihan EXP $
 */
public interface ValidResult<T> {
    /**
     * 验证是否通过
     *
     * @return
     */
    public boolean passed();

    /**
     * 下次待验证的元信息
     *
     * @return
     */
    public List<T> faied();


    /**
     * 记录校验失败的
     *
     * @param t
     */
    public void  addFailed(T t);


    /**
     * 标记为
     */
    public void  mark();


}
