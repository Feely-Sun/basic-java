package com.basic.leanring.java.mertrics;

import java.util.concurrent.TimeUnit;

import static java.lang.Math.exp;

/**
 * @author sunzihan
 * @version $Id: B.java V 0.1 3/22/17 20:50 sunzihan EXP $
 */
public class B {

    private static final int INTERVAL = 5;
    private static final double SECONDS_PER_MINUTE = 60.0;
    private static final int ONE_MINUTE = 1;
    private static final int FIVE_MINUTES = 5;
    private static final int FIFTEEN_MINUTES = 15;
    private static final double M1_ALPHA =exp(-5 / 60.0 / 1);
    private static final double M5_ALPHA = 1 - exp(-INTERVAL / SECONDS_PER_MINUTE / FIVE_MINUTES);
    private static final double M15_ALPHA = 1 - exp(-INTERVAL / SECONDS_PER_MINUTE / FIFTEEN_MINUTES);

    public static void main(String[] args) {


       int i= (6 - 4) % 2;

        System.out.println(-INTERVAL / SECONDS_PER_MINUTE / ONE_MINUTE);



        System.out.println(M1_ALPHA);



    }
}

