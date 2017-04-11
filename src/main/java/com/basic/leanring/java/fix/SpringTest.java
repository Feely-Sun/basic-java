package com.basic.leanring.java.fix;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunzihan
 * @version $Id: SpringTest.java V 0.1 2/27/17 13:21 sunzihan EXP $
 */
public class SpringTest {


    public void printf(){

       String  str = "abc";
        Map<String,String> strMap = new HashMap<>();


        strMap.put("1","abc");

        String s = strMap.get("1");


        System.out.println(s != str);





    }

    public static void main(String[] args) {

        new SpringTest().printf();

    }


}

