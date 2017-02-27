package com.basic.leanring.java.generic;

/**
 * @author sunzihan
 * @version $Id: CalFact.java V 0.1 2/23/17 12:39 sunzihan EXP $
 */
public interface CalFact<IN,OUT> {

    public  OUT cal(IN n1,IN n2);
}
