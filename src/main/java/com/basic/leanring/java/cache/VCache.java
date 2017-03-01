package com.basic.leanring.java.cache;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @author sunzihan
 * @version $Id: VCache.java V 0.1 2/28/17 21:56 sunzihan EXP $
 */
public class VCache implements InitializingBean {

    private Cache<String, Set<Integer>> cache;

    /** 统计任务队列 */
    private final BlockingQueue<String> syncQueue        = new ArrayBlockingQueue<String>(10000);

    /** 指标计算上下文缓存：eventId => indicatorId => indicatorValue */
    private LoadingCache<String, Set<Integer>> cache2;


    private String                     valueCacheConfig               = CacheConfigBuilder
            .newBuilder()                                                                           //
            .maximumSize(1500)                                                                      //
            .expireAfterAccess(3)                                                                   //
            .timeUnit(TimeUnit.SECONDS)                                                             //
            .build();


    /**
     * 定时任务调度器
     */
    private ScheduledExecutorService  scheduler;

    @Override
    public void afterPropertiesSet() throws Exception {

        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(20, TimeUnit.SECONDS)
                .maximumSize(10)
                .initialCapacity(5)
                .build();

        cache2 = CacheBuilder.from(valueCacheConfig).build(new CacheLoader<String, Set<Integer>>() {
            @Override
            public Set<Integer> load(String key) throws Exception {
                return new HashSet<Integer>();
            }
        });



        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(new SyncTask(), 10, 2,
                TimeUnit.SECONDS);

        System.out.println("init...thread..." + Thread.currentThread().getName() );

        Thread syncQueueTask = new SyncQueueTask();
        syncQueueTask.start();
    }


    public final Cache<String, Set<Integer>> getCache() {
        return cache;
    }


    class SyncTask implements Runnable{
        @Override
        public void run() {
            System.out.println("start to do....current thread=" + Thread.currentThread().getName());
        }
    }


    class SyncQueueTask extends Thread{
        /**
         * 构造函数
         */
        public SyncQueueTask() {
            super("Sync-public-indicatorset-QueueTask");
        }

        /**
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("lister,,");
                    String key = syncQueue.take();
                    System.out.println("listed to");
                    if (cache.getIfPresent(key) == null) {
                        System.out.println("sync from tair....");
                    }
                } catch (InterruptedException e) {
                    System.out.printf("The task was interrupted!,error:%s", e.getMessage());
                } catch (Exception e) {
                    System.out.printf("A error occour when sync,error:%s", e.getMessage());
                }
            }
        }

    }
}
