package com.basic.learning.java.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * @author sunzihan
 * @version $Id: Md5.java V 0.1 3/6/17 21:47 sunzihan EXP $
 */
public class Md5 extends BaseTest{

    @Test
    public void testMd5(){

       String res =  DigestUtils.md5Hex("##20##30#100");

        System.out.println(res.length());

    }
}

