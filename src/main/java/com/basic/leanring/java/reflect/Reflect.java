package com.basic.leanring.java.reflect;
import com.alibaba.common.lang.StringUtil;

import java.util.*;

/**
 * @author sunzihan
 * @version $Id: Reflect.java V 0.1 2/27/17 11:28 sunzihan EXP $
 */
public class Reflect {


    public static void main(String[] args) {



        String str = "n1:10#n2:20#n3:40";

        final String type = "ASE";

        String[] res = str.split("#");
        int i = res .length;
        Map<String,Double> tree = new HashMap<String,Double>();
        for (int j = 0 ;j < i ; j ++){
            String[] s = res[j].split(":");
            double value = Double.parseDouble(s[1]);
            tree.put(s[0],value);
        }
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(tree.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                if (type.equalsIgnoreCase("DESC")){
                    return  o2.getValue().compareTo( o1.getValue());
                }else {
                    return  o1.getValue().compareTo( o2.getValue());
                }
            }
        });
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String,Double> entry : list){
            if (sb.length() > 0){
                sb.append(",");
            }
            sb.append(entry.getKey()+":"+entry.getValue());
        }


        System.out.println(sb.toString());

    }
}

