package com.basic.leanring.java.mertrics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

/**
 * @author sunzihan
 * @version $Id: MertricsDemo.java V 0.1 3/21/17 17:24 sunzihan EXP $
 */
public class MertricsDemo  {

    private static final MetricRegistry metrics = new MetricRegistry();


    AtomicInteger ai = new AtomicInteger(1);

    //private static Queue<String> queue = new LinkedBlockingDeque<String>();

//
//    private static final Timer  responses = metrics
//            .timer(MetricRegistry.name(RequestHandler.class, "responses"));
//



    static Meter meter =metrics.meter(MetricRegistry.name(MertricsDemo.class, "meter-job", "size"));

//
//    private static Counter pendingJobs = metrics.counter(MetricRegistry.name(MertricsDemo.class, "pending-job", "size"));

    private static Queue<String> queue = new LinkedList<String>();

//    public static void add(String str) {
//        pendingJobs.inc();
//        queue.offer(str);
//    }
//
//    public String take() {
//        pendingJobs.dec();
//        return queue.poll();
//    }
//

    public static void main(String args[]) throws InterruptedException {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
        
        reporter.start(5,TimeUnit.SECONDS);






//        //测试JMX
//        JmxReporter jmxReporter = JmxReporter.forRegistry(metrics).build();
//        jmxReporter.start();


        //注册到容器中
//        metrics.register(MetricRegistry.name(MertricsDemo.class, "pending-job", "size"),  new Gauge<Integer>() {
//            @Override
//            public Integer getValue() {
//                int res =  queue.size();
//                queue.clear();
//                return res;
//            }
//        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0 ; i < 200; i ++){
//                    queue.add("1");
//                    try {
//                        //停歇800ms加一次
//                        Thread.sleep(800);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }).start();

        while(true){
           request();
            Thread.sleep(100);
        }

       // wait5Seconds();
    }

    static void request() {
        meter.mark(100);
    }

    static void wait5Seconds() {
        try {
            Thread.sleep(500 * 1000);
        } catch (InterruptedException e) {
        }
    }
//    public String handleRequest() throws InterruptedException {
//        final Timer.Context context = responses.time();
//        try {
//            for (int i = 0; i < 200; i++) {
//                Thread.sleep(20);
//            }
//            // etc;
//            return "OK";
//        } finally {
//            context.stop();
//        }
//    }

    class RequestHandler {

    }

}
