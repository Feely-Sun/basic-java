package com.basic.leanring.java.antfeature.featurelifetime.model;

/**
 * @author sunzihan
 * @version $Id: FeatureRefer.java V 0.1 5/15/17 10:40 sunzihan EXP $
 */
public class FeatureRefer {

    //特征唯一ID
    private long   featureUniqueId;

    //特征名字
    private String featureName;

    //特征描述
    private String featureDesc;
    /**
     * 引用类型:
     *
     * 策略,Velocity,特征
     */
    private String referrerType;

    // 引用名字
    private String referrerName;

    //修改者
    private String modifier;

}

