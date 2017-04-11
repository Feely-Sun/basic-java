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


    @Test
    public void query() {

        StringBuilder sb = new StringBuilder();
        sb.append("(Nav :START=(Nav :START=");
        sb.append(""+"xxdfsdfdsfdsfds");
        sb.append(" :EDGE_TYPE=\"gdb_ctu_kexin_media_geabase#16\"  :FILTER=@bg_status<2 )" );
        sb.append(" :EDGE_TYPE=\"gdb_ctu_user_user#user_user\" :TIMESTAMP=0 :EXPECTED_DST= ");
        sb.append(""+"1233445234234324");
        sb.append(" :RETURN=~contact_cti)");

        System.out.println(sb.toString());

    }
}

