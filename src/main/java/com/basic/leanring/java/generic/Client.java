package com.basic.leanring.java.generic;


import java.util.ArrayList;
import java.util.List;

/**
 * @author sunzihan
 * @version $Id: Client.java V 0.1 2/23/17 12:08 sunzihan EXP $
 */
public class Client {

    public static void main(String[] args) {
        CalFact cal = new CalEng();

        Object o = GenericMethod.<Double,Double>cal(cal,1.0,3.0);

        System.out.println(o);





















    }

}

