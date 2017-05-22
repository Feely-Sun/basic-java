package com.basic.learning.java.test;

import com.basic.leanring.java.Test2;
import com.basic.leanring.java.TestBean;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sunzihan
 * @version $Id: TT.java V 0.1 5/4/17 17:59 sunzihan EXP $
 */
public class TT extends BaseTest {



    @Autowired
    private Test2 test2;


    @Test
    public void test(){


        test2.test2();
    }


}

