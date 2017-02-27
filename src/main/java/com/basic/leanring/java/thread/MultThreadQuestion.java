package com.basic.leanring.java.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sunzihan
 * @version $Id: MultThreadQuestion.java V 0.1 2/26/17 19:03 sunzihan EXP $
 */
public class MultThreadQuestion {

    private final Lock lock  = new ReentrantLock();


    private List<String>  checkList = null;


    private final ScheduledExecutorService scheduler                       = Executors
            .newScheduledThreadPool(1);

    private int count =0;

    public List<String> getCheckList() throws InterruptedException {
        lock.lock();
        try {
            count++;
            if (checkList != null){
               // System.out.println("cout == " +count);
                return checkList;
            }

            checkList = new ArrayList<>();
            checkList.add("a");
            checkList.add("b");
            checkList.add("c");
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.lock();
                        System.out.println("checkList == null-------- ");
                        checkList = null;
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            },30, TimeUnit.MILLISECONDS);
        }catch (Exception e){
             e.printStackTrace();
        }finally {
            //final 是线程安全的
            //释放
            lock.unlock();
            if (count > 1){
                Thread.sleep(20000);
            }
            Thread.sleep(500);
        }

        return checkList;






    }





}

