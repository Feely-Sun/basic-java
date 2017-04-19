package com.basic.learning.java.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author sunzihan
 * @version $Id: InnerR.java V 0.1 4/7/17 13:40 sunzihan EXP $
 */
public class InnerR {

    private String name = "";

    class Closer{
        public void inner(){
            final String s = "";
            test(new Cal() {
                @Override
                public void calNum(int num) {
                    System.out.println("num===" + num);
                }
            });
        }
    }

    public void test(Cal cal){
        cal.calNum(4);
    }

    interface Cal{
        void calNum(int num);
    }


    public static void main(String[] args) {

//        Closer cs  =new InnerR().new Closer();
//
//        cs.inner();


        File file =new File("/Users/sunzihan/input/kinglear.txt");
        try {
            PrintWriter p = new PrintWriter(file);


            p.println("dfdsfsdfsd");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}





