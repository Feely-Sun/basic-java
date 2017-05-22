package com.basic.leanring.java.antfeature.featurelifetime.manager.impl;


import com.basic.leanring.java.antfeature.featurelifetime.manager.MetaDataVaildManager;
import com.basic.leanring.java.antfeature.featurelifetime.model.*;

/**
 * @author sunzihan
 * @version $Id: MetaDataVaildManagerImpl.java V 0.1 5/8/17 19:56 sunzihan EXP $
 */
public class MetaDataVaildManagerImpl implements MetaDataVaildManager {


    /**
     * @param allData
     *
     * @return
     */
    @Override
    public ValidResult data2Vaild(Object ...allData) {
        return data2Vaild(0,new IValidResult(),allData);
    }


    /**
     *
     * @param index
     * @param result
     * @param datas
     * @return
     */
    private ValidResult data2Vaild(int index,ValidResult result,Object ...datas){

        if (datas.length == 0){
            return new IValidResult(true);
        }

        if (datas.length -1 == index){
            return result;
        }

        Object meteaData = datas[index];

        if(meteaData instanceof DataView){
            //
            DataView dv = (DataView) meteaData;
            //获取参数

            //DAO验证

            if (true){
                result.mark();
            }else{
                result.addFailed(dv);
            }

            return data2Vaild(++index,result,datas);

        }

        if(meteaData instanceof Function){

        }
        return null;

    }


}

