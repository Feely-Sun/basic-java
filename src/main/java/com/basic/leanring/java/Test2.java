package com.basic.leanring.java;

/**
 * @author sunzihan
 * @version $Id: Test2.java V 0.1 5/4/17 17:58 sunzihan EXP $
 */
public class Test2 {


    private TestBean testBean;


    public TestBean getTestBean() {
        return testBean;
    }

    public void setTestBean(TestBean testBean) {
        this.testBean = testBean;
    }



    public void test2(){
        testBean.test();
    }
}

