package com.basic.leanring.java.reflect;

/**
 * @author sunzihan
 * @version $Id: Reflect.java V 0.1 2/27/17 11:28 sunzihan EXP $
 */
public class Reflect {


    public static void main(String[] args) {


         long sum = 0;
         for (long i= 0 ; i < 1000000000 ; i ++){
             sum+=i;
         }

        System.out.println(sum);

    }


}

