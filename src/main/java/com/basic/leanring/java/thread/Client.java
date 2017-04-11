package com.basic.leanring.java.thread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author sunzihan
 * @version $Id: Client.java V 0.1 2/26/17 19:12 sunzihan EXP $
 */
public class Client {


    private static final ExecutorService executorService                       = Executors
            .newScheduledThreadPool(100);


    private static final ScheduledExecutorService scheduler                       = Executors
            .newScheduledThreadPool(1);

    public static void main(String[] args) {

       final MultThreadQuestion multThreadQuestion =new MultThreadQuestion();


//        for (int i = 0 ; i < 20000 ; i ++){
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    List<String> ret  = null;
//                    try {
//                        ret = multThreadQuestion.getCheckList();
//                        if (ret == null){
//                            System.out.println("exist parllern question");
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });


            List<String> retcopy = null;
            for (int i = 0 ; i < 2 ; i ++){
                List<String> ret  = null;

                try {
                    ret = multThreadQuestion.getCheckList();
                    if (retcopy != ret){
                        System.out.println("ret != retcopy");
                        retcopy = ret;
                    }else{
                        System.out.println("ret == retctcopy");
                    }
                    System.out.println("inde == " + i + "ret==" +ret);
                    if (ret == null){
                        System.out.println("exist parllern question");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }








//        try {
//            Thread.sleep(2000000);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }







    }

}

