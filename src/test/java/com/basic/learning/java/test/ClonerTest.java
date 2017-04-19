package com.basic.learning.java.test;

import com.alibaba.common.lang.enumeration.IntegerEnum;
import com.basic.leanring.java.indicator.model.CommonCtuEvent;
import com.basic.leanring.java.util.Cloner;
import com.basic.leanring.java.util.enums.BigIntegerEnum;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author sunzihan
 * @version $Id: ClonerTest.java V 0.1 3/15/17 17:35 sunzihan EXP $
 */
public class ClonerTest extends BaseTest {

    @Test
    public void testCopy() throws Exception {
        try{
            CommonCtuEvent ctuEvent = new CommonCtuEvent();
            Object copy = Cloner.copy(ctuEvent);


            Assert.assertEquals(ctuEvent, copy);
        }catch(Exception ex){
            Assert.fail();
        }
    }

    @Test
    public void testArrayNormal(){
        String source=new String("Source");
        byte[] writeObject = Cloner.writeObject(source);

        Object copy =Cloner.readObject(writeObject);
        Assert.assertEquals(source, copy);
    }


    @Test
    public void testreadError(){
        Object readObject = Cloner.readObject(null);
        Assert.assertNull(readObject);
    }


    @Test
    public void bigIntegerEumn(){


       com.basic.leanring.java.util.Enum  bi = TestEnum.EVENT;

        System.out.println(bi);

    }




}

