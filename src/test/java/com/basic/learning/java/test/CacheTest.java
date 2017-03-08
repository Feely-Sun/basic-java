package com.basic.learning.java.test;

import com.alibaba.geabase.gdbc.util.InternalID;
import com.basic.leanring.java.cache.VCache;
import com.google.common.cache.Cache;
import com.google.common.cache.LoadingCache;
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
        LoadingCache<String, Set<Integer>>  lc = vCache.getLoadingCache();

        Set<Integer> before =  lc.getUnchecked("s");
        System.out.println("before:" + before);

//        lc.invalidateAll();

        Set<Integer> afetr = lc.getUnchecked("s");

        System.out.println("after:" + afetr);

        String convToInternalId = "uid"+"_"+"2088112299244275";
//查询内部id标识
        long internalId = InternalID.getInternalID(convToInternalId);
//Umid hash
        long umid = InternalID.getInternalID("umid_"+"WV11z56c9a1953bb8f20803f65f203312");


        StringBuilder sb = new StringBuilder();
        sb.append("(Nav :START=(Nav :START=");
        sb.append(""+umid);
        sb.append(" :EDGE_TYPE=\"gdb_ctu_kexin_media_geabase#16\"  :FILTER=@bg_status<2 )" );
        sb.append(" :EDGE_TYPE=\"gdb_ctu_user_user#user_user\" :TIMESTAMP=0 :EXPECTED_DST= ");
        sb.append(""+internalId);
        sb.append(" :RETURN=~contact_cti )");

        System.out.println(sb.toString());




//        try {
//            Thread.sleep(500000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }



}

