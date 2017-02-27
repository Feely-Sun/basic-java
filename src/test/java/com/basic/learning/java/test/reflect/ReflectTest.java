package com.basic.learning.java.test.reflect;

import com.basic.leanring.java.fix.SpringTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author sunzihan
 * @version $Id: ReflectTest.java V 0.1 2/27/17 11:47 sunzihan EXP $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
public class ReflectTest  {

    @Autowired
    private SpringTest springTest;


    @Test
    public void springTest(){
        springTest.printf();
    }








}

