package com.basic.leanring.java.antfeature.featurelifetime.spi;

import com.basic.leanring.java.antfeature.featurelifetime.enums.ProductFlag;
import com.basic.leanring.java.antfeature.featurelifetime.model.DataView;
import com.basic.leanring.java.antfeature.featurelifetime.model.ValidResult;

import java.util.Collection;

/**
 *
 * 校验SPI接口,由各个DV子产品来实现校验逻辑
 * <strong>
 *     <p>
 *        注意服务直供商必须符合java spi 的实现规范 详情 {@link java.util.ServiceLoader}
 *     </p>
 * </strong>
 *
 *
 * @author sunzihan
 * @version $Id: Checker.java V 0.1 5/16/17 11:29 sunzihan EXP $
 */
public interface Checker {

    /**
     * 针对不通的DV产品由不同的产品提供实现
     *
     * @param dvs
     * @return
     */
    public ValidResult check(Collection<? extends DataView> dvs);


    /**
     * 校验目标
     *
     <b>VELOCITY</b>

     <b>HBASE</b>

     <b>SERVICE</b>

     <b>FTG</b>

     <b>OTHER</b>
     *
     *
     * @return
     */
    public ProductFlag  target();
}
