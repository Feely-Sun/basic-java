package com.basic.leanring.java;

import com.alibaba.common.lang.StringUtil;

import java.io.UnsupportedEncodingException;

/**
 * @author sunzihan
 * @version $Id: Util.java V 0.1 3/6/17 15:32 sunzihan EXP $
 */
public class MurmurHash {


    /**
     *  Hash指定的data成一个int类型的hash值
     *
     * @param data
     * @param length
     * @param seed
     * @return
     */
    public static int hash32(byte[] data, int length, int seed) {
        int m = 0x5bd1e995;
        int r = 24;

        int h = seed ^ length;

        int len_4 = length >> 2;

        for (int i = 0; i < len_4; i++) {
            int i_4 = i << 2;
            int k = data[i_4 + 3];
            k = k << 8;
            k = k | (data[i_4 + 2] & 0xff);
            k = k << 8;
            k = k | (data[i_4 + 1] & 0xff);
            k = k << 8;
            k = k | (data[i_4 + 0] & 0xff);
            k *= m;
            k ^= k >>> r;
            k *= m;
            h *= m;
            h ^= k;
        }

        // avoid calculating modulo
        int len_m = len_4 << 2;
        int left = length - len_m;

        if (left != 0) {
            if (left >= 3) {
                h ^= (int) data[length - 3] << 16;
            }
            if (left >= 2) {
                h ^= (int) data[length - 2] << 8;
            }
            if (left >= 1) {
                h ^= (int) data[length - 1];
            }

            h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;

        return h;
    }

    /**
     * 取hash的后几位值
     *
     * @param sourceStr -源字符串
     * @param suffixLength  -取几位的长度
     * @return
     */
    public static String getHashCodeSuffix(String sourceStr, int suffixLength) {
        byte[] bytes = sourceStr.getBytes();
        int baseValue = (int) Math.pow(10, Math.abs(suffixLength));
        int abs = Math.abs(MurmurHash.hash32(bytes, bytes.length, 0)) % baseValue;
        return StringUtil.substring(String.valueOf(abs + baseValue), 1);
    }

    /**
     * 取hash的后几位值
     *
     * @param sourceStr
     *            -源字符串, 采用gbk编码方式编码
     * @param suffixLength
     *            -取几位的长度
     * @return
     */
    public static String getHashCodeSuffixAsGBK(String sourceStr,
                                                int suffixLength) {
        byte[] bytes = null;
        try {
            bytes = sourceStr.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            bytes = sourceStr.getBytes();
        }
        int baseValue = (int) Math.pow(10, Math.abs(suffixLength));
        int abs = Math.abs(MurmurHash.hash32(bytes, bytes.length, 0))
                % baseValue;
        return StringUtil.substring(String.valueOf(abs + baseValue), 1);
    }

}

