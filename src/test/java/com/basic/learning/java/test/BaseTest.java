package com.basic.learning.java.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author sunzihan
 * @version $Id: BaseTest.java V 0.1 2/27/17 19:58 sunzihan EXP $
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
public abstract class BaseTest {




}

