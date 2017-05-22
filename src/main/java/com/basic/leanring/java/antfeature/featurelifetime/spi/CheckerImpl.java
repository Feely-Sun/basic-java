package com.basic.leanring.java.antfeature.featurelifetime.spi;

import com.basic.leanring.java.antfeature.featurelifetime.enums.ProductFlag;
import com.basic.leanring.java.antfeature.featurelifetime.model.DataView;
import com.basic.leanring.java.antfeature.featurelifetime.model.ValidResult;

import java.util.Collection;

/**
 * @author sunzihan
 * @version $Id: CheckerImpl.java V 0.1 5/16/17 13:19 sunzihan EXP $
 */
public class CheckerImpl implements Checker {

    private final ProductFlag flag;

    public CheckerImpl(ProductFlag flag){
        this.flag = flag;
    }


    @Override
    public ValidResult check(Collection<? extends DataView> dvs) {
        return null;
    }








    @Override
    public ProductFlag target() {
        return flag;
    }
}

