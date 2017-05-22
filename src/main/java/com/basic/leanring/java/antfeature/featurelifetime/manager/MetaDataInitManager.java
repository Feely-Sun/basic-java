package com.basic.leanring.java.antfeature.featurelifetime.manager;

import java.util.List;

/**
 * 初始化元数据管理
 *
 * @author sunzihan
 * @version $Id: MetaDataInitManager.java V 0.1 5/8/17 16:05 sunzihan EXP $
 */
public interface MetaDataInitManager {

    /**
     * 查询可用状态的所有数据视图
     *
     * @return
     */
    public List<String> queryAllUsedabledDataView();


    /**
     * 查询可用状态下的所有UDF
     *
     * @return
     */
    public List<String> queryAllUseabedFuncs();


    /**
     * 查询可用状态下的所有的特征
     *
     * @return
     */
    public List<String> queryAllUsedFeature();

}
