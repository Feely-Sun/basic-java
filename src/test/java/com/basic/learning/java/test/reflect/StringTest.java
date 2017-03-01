package com.basic.learning.java.test.reflect;

import com.basic.leanring.java.tiercomponent.TierComponent;
import com.basic.learning.java.test.BaseTest;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author sunzihan
 * @version $Id: StringTest.java V 0.1 2/27/17 19:58 sunzihan EXP $
 */
public class StringTest extends BaseTest {



    @Autowired
    private TierComponent tierComponent;

    @Test
    public void test1() throws NoSuchMethodException {



//        int step = 5;
//
//
//        int call = 0;
//
//
//        Restult rs = null;
//
//        while (call == 0 || (rs != null && rs.getErroCode() == -4 )) {
//            if (call >= 3) {
//                // give up
//                System.out.printf("geabase query give up try.");
//                break;
//            }
//             rs = new Resp().query(10+step);
//            call++;
//            step += 5;
//        }


        try{
            Restult rs =null;
            if (rs == null){
                throw new NullPointerException("rs is Null");
            }
        }catch (Exception ex){
            System.out.println("dddddd" +ex.getMessage());
        }



    }

    class Resp {


        public Restult query (int timeout){

            if (timeout < 5){
                Restult rs =  new Restult();
                rs.setErroCode(-4);
                return rs;
            }


            return null;
        }


    }


    class Restult {


        private String res;

        private int erroCode ;


        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }

        public int getErroCode() {
            return erroCode;
        }

        public void setErroCode(int erroCode) {
            this.erroCode = erroCode;
        }
    }

}

