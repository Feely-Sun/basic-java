package com.basic.leanring.java.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import org.springframework.beans.factory.InitializingBean;

import com.google.common.cache.*;

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

    /** 用于批量缓存同步 */
    private final static ThreadLocal<List<Set<Integer>>> BATCH_SYNC       = new ThreadLocal<List<Set<Integer>>>();


    /** 事件处理线程池 */
    private ExecutorService   execute =Executors.newFixedThreadPool(10);

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


        CacheBuilder<String, Set<Integer>> cahceBuilder = CacheBuilder.newBuilder()
                .maximumSize(10)
                .removalListener(new RemovalListener<String,  Set<Integer>>() {
                    @Override
                    public void onRemoval(RemovalNotification<String,  Set<Integer>> rn) {
                        // 判断是否为批量处理  @see {SyncTask.run}
                        System.out.println("trigger rev listening.....current Thread.." + Thread.currentThread().getName());
                        List< Set<Integer>> list = BATCH_SYNC.get();
                        System.out.println("list......+" + list);
                        if (list != null) {
                            list.add(rn.getValue());
                            return;
                        }
                        execute.execute(new SyncTask(rn.getValue()));
                    }
                });
        cache2 = cahceBuilder.build(new CacheLoader<String,  Set<Integer>>() {
            @Override
            public  Set<Integer> load(String key) throws Exception {
                //当指定KEY在缓存中不存在时的加载方法，自动根据KEY构造新缓存对象并返回，利用缓存服务本身的并发机制规避并发问题
                Set<Integer> info = new HashSet<Integer>();
                System.out.println("load cache22....");
                info.add(12);
                return info;
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

    public final  LoadingCache<String, Set<Integer>> getLoadingCache() {
        return cache2;
    }


    class SyncTask implements Runnable{

        public Set<Integer> sets = null;


        public SyncTask(Set<Integer> sets){
            this.sets = sets;
        }

        public SyncTask(){}


        @Override
        public void run() {
            System.out.println("start to do....current thread=" + Thread.currentThread().getName());
            List<Set<Integer>> list = new ArrayList<>();

            Set<Integer> sets = new HashSet<>();
            sets.add(1);
            sets.add(2);
            sets.add(3);

            list.add(sets);

            BATCH_SYNC.set(list);

            System.out.println("invailed cache.....");
            cache2.invalidateAll();

            System.out.println("current Thread...." + Thread.currentThread().getName());


        }
    }


    public static void main(String[] args) {

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
