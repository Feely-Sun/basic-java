package com.basic.learning.java.test;

import com.alibaba.common.lang.StringUtil;
import com.basic.leanring.java.MurmurHash;
import org.junit.Test;

/**
 * @author sunzihan
 * @version $Id: AA.java V 0.1 3/6/17 15:31 sunzihan EXP $
 */
public class AA  extends BaseTest{


    String eventId ="0c65e6dc835e808751ea0cdeddb3c796";

    @Test
    public void testHash(){
        // murmurhash
        int rehash = Math.abs(MurmurHash.hash32(eventId.getBytes(), eventId.length(), 0));

        // 如果位数小于 prefixSize,则靠左以0补齐
        String hashValue = StringUtil.alignRight(String.valueOf(rehash), 5, "0");

        // 获取前置
        int hashValueSize = hashValue.length();
        String prefix = StringUtil.substring(hashValue, hashValueSize - 5, hashValueSize);

        // 生成rowkey
        String key = prefix + "_" + eventId;

        System.out.println(key);
    }
}

