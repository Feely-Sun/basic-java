package com.basic.leanring.java.antfeature.featurelifetime.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author sunzihan
 * @version $Id: Feature.java V 0.1 5/8/17 16:26 sunzihan EXP $
 */
public class Feature implements Serializable {
    /** Ser No */
    private static final long serialVersionUID = 1L;

    /** 特征ID */
    private long              id;

    /** 特征一标识 */
    private long              uniqueId;

    /** 特征名称 */
    private String            featureName;

    /** 特征描述 */
    private String            featureDesc;

    /** 特征的返回值类型 */
    private String            returnType;

    /** 特征的默认返回值 */
    private String            defaultReturnValue;

    /** 特征输入参数类型 */
    private String            inputType;

    /** 特征的脚本内容 */
    private String            variableScript;

    /** 经语法解析后的变量脚本 */
    private String            parsedFeatureScript;

    /** 特征的的状态 */
    private String            state;

    /** 特征的子状态 */
    private String            childState;

    /** 特征备注 */
    private String            memo;

    /** 特征的创建者 */
    private String            creator;

    /** 特征的最后修改人 */
    private String            modifier;

    /** 特征的维护人 */
    private String            owner;

    /** 特征的的审核人 */
    private String            reviewer;

    /** 创建时间 */
    private Date              gmtCreate;

    /** 最后修改时间 */
    private Date              gmtModified;

    /** 特征的使用者 */
     private List<FeatureRefer> featureReferrers;

    /** 特征直接依赖的特征名 */
    private List<String>      featureDependencyPaths;

    /** 用户标签 */
    private List<String>      userLabels;

    /*** 特征一级域 */
    private long              domain;

    /*** 特征二级域 */
    private long              subDomain;

    /** 特征一级域名 */
    private String            domainName;

    /** 特征二级域名 */
    private String            subDomainName;

    /** 域标识 */
    private String            domainCode;

    /** 特征版本号 */
    private long              version;

    /**
     * 是否为共享特征 db_column: share
     */
    private int               share;

    /** 下线结束时间 **/
    private Date              offLineTime;

    /** 上线生效时间 */
    private Date              onLineTime;
}
