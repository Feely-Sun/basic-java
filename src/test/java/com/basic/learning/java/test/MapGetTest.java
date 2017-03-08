package com.basic.learning.java.test;

import com.basic.leanring.java.CalCalte;
import com.basic.leanring.java.DataPointParam;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunzihan
 * @version $Id: MapGetTest.java V 0.1 3/2/17 10:55 sunzihan EXP $
 */
public class MapGetTest extends BaseTest{




    @Test
    public void testMapGet(){

        Map<DataPointParam, Object> dataPointParams = new HashMap<>();

        DataPointParam dp = new DataPointParam();
        dp.setName("cif");
        dp.setDesc("cx");
        dp.setParamType("String");

        List<String> val = new ArrayList<>();
        val.add("userId");
        val.add("1");


        dataPointParams.put(dp,val);


        ///////////////////////////////////

        Map<DataPointParam, Object> dataPointParams2 = new HashMap<>();

        DataPointParam dp3 = new DataPointParam();
        dp3.setName("cif");
        dp3.setDesc("cx");
        dp3.setParamType("String");

        List<String> val3 = new ArrayList<>();
        val3.add("userId");
        val3.add("1");
        dataPointParams2.put(dp3,val3);

        CalCalte calCalte1 = new CalCalte("1","cif",dataPointParams);
        CalCalte calCalte2 = new CalCalte("2","cif",dataPointParams2);

        List<CalCalte> clsit = new ArrayList<>();
        clsit.add(calCalte1);
        clsit.add(calCalte2);

        Map<CalCalte,List<String>> gp = new HashMap<>();

        for (int i = 0 ; i < clsit.size() ; i ++){
            CalCalte cc = clsit.get(i);
            List<String> gpList =  gp.get(cc);

            if (gpList == null){
                gpList = new ArrayList<>();
               gp.put(cc,gpList);
            }

            gpList.add(cc.getNodeId());
        }
    }
}

