package com.basic.leanring.java.generic;

/**
 * @author sunzihan
 * @version $Id: CalEng.java V 0.1 2/23/17 12:38 sunzihan EXP $
 */
public class CalEng implements CalFact<Integer,Double>{


    @Override
    public Double cal(Integer n1, Integer n2) {
        return Double.parseDouble(String.valueOf(n1+n2));
    }


    /**
     *
     *  import com.alipay.dataflow.sdk.transforms.*;
     return CombineFns.compose()
     .with(max_1279378041_fn, new Max.MaxIntegerFn(),max_1279378041)

     *
     *
     */
}

