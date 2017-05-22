package com.basic.leanring.java.antfeature.featurelifetime.manager;

import com.basic.leanring.java.antfeature.featurelifetime.model.ValidResult;


/**
 * 元信息校验
 *
 * @author sunzihan
 * @version $Id: MetaDataVaildManager.java V 0.1 5/8/17 19:38 sunzihan EXP $
 */
public interface MetaDataVaildManager {

    /**
     * @return
     */
    public ValidResult data2Vaild(Object  ...initDat);
}
