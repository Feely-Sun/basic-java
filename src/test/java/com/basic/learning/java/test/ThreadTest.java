package com.basic.learning.java.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sunzihan
 * @version $Id: ThreadTest.java V 0.1 3/6/17 17:05 sunzihan EXP $
 */
public class ThreadTest {

    /** 用于批量缓存同步 */
    private final static ThreadLocal<List<Set<Integer>>> BATCH_SYNC       = new ThreadLocal<List<Set<Integer>>>();


    public static void main(String[] args) {



        final List<Set<Integer>> list = new ArrayList<>();

        Set<Integer> sets = new HashSet<>();
        sets.add(1);
        sets.add(2);
        sets.add(3);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("init");
                BATCH_SYNC.set(list);
            }
        }).start();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main:" + BATCH_SYNC.get());
    }
}

