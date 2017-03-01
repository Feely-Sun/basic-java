package com.basic.learning.java.test.reflect;

import com.basic.leanring.java.fix.SpringTest;
import com.basic.leanring.java.tiercomponent.AnalyComponent;
import com.basic.leanring.java.tiercomponent.TierComponent;
import com.basic.learning.java.test.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author sunzihan
 * @version $Id: ReflectTest.java V 0.1 2/27/17 11:47 sunzihan EXP $
 */

public class ReflectTest extends BaseTest {

    @Autowired
    private SpringTest springTest;


    @Autowired
    private TierComponent fastAnalyzeTComponent;

    @Autowired
    private TierComponent deepAnalyzeTComponent;


    @Test
    public void springTest(){
        springTest.printf();
    }


    @Test
    public void intercepterTest(){

        System.out.println(fastAnalyzeTComponent == deepAnalyzeTComponent);

        for (int i =0 ; i < 2 ; i++){
            fastAnalyzeTComponent.execute("A");

        }


        deepAnalyzeTComponent.execute("B");




    }








}

