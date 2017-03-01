package com.basic.learning.java.test;

import com.basic.leanring.java.cache.VCache;
import com.google.common.cache.Cache;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author sunzihan
 * @version $Id: CacheTest.java V 0.1 2/28/17 22:00 sunzihan EXP $
 */
public class CacheTest extends BaseTest {


    @Autowired
    private VCache vCache;


    @Test
    public void testCache() throws ExecutionException {
        final Cache<String, Set<Integer>> cache =   vCache.getCache();

        Set<Integer> sets= new HashSet<>();
        sets.add(1);
        sets.add(2);
        cache.put("key",sets);

        Set<Integer> res =cache.get("key", new Callable<Set<Integer>>() {
            @Override
            public Set<Integer> call() throws Exception {
                Set<Integer> obj = cache.getIfPresent("key");
                if (obj != null){
                    return obj;
                }else{
                    return new HashSet<Integer>();
                }
            }
        });

        System.out.println(res);


//        try {
//            Thread.sleep(500000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }



}

