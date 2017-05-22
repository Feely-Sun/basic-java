package com.basic.learning.java.test;

import com.basic.leanring.java.antfeature.featurelifetime.spi.Checker;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author sunzihan
 * @version $Id: SpiTest.java V 0.1 5/16/17 12:19 sunzihan EXP $
 */
public class SpiTest {


    public static void main(String[] args) {

        ServiceLoader<Checker> checkers = ServiceLoader.load(Checker.class);

        Checker checker = null;

        Iterator<Checker> iterator   = checkers.iterator();



        if (iterator.hasNext()){
            checker = iterator.next();
            //boolean success = checker.check();

           // System.out.println(success);

        }



    }

}

