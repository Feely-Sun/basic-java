package com.basic.leanring.java.thread;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author sunzihan
 * @version $Id: MapDemo.java V 0.1 3/24/17 11:50 sunzihan EXP $
 */
public class MapDemo {



    static  Map<String,String> tmp = new HashMap<>();


    static Executor executor = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {


        for (int i = 0; i < 100 ; i ++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                   tmp.put(Thread.currentThread().getName(),"a");
                }
            });
        }

        for (int i = 0; i < 100 ; i ++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(tmp.get(Thread.currentThread().getName()));
                }
            });
        }




    }



}

