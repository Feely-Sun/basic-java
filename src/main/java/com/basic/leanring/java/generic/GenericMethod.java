package com.basic.leanring.java.generic;

/**
 * @author sunzihan
 * @version $Id: GenericMethod.java V 0.1 2/23/17 12:37 sunzihan EXP $
 */
public class GenericMethod {

    public  static <IN,OUT> OUT cal(CalFact<IN,OUT> fact,IN v1,IN v2){
      return fact.cal(v1,v2);
    }

}

