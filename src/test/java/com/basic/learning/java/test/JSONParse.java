package com.basic.learning.java.test;

import com.basic.leanring.java.DataPointParam;
import com.basic.leanring.java.JsonUtil;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * @author sunzihan
 * @version $Id: JSONParse.java V 0.1 3/1/17 15:43 sunzihan EXP $
 */
public class JSONParse extends BaseTest {

    @Test
    public void testJsonParse() {

        String protocol = "[\"uct_ftg_query_rt\",\"{uct_ftg_base_rt_first_dimension_uid}\",\"{uct_ftg_base_rt_indicator}\",\"{uct_ftg_base_rt_second_dimension_uid}\",\"{uct_ftg_base_rt_start_time}\",\"{uct_ftg_base_rt_strategy_uid}\",\"{uct_ftg_base_rt_time_window}\"]";


        String prams = "[{\"desc\":\"收货地址\",\"name\":\"identityInfoCompareToDeliveryAddress_deliveryAddress\",\"paramType\":\"String\"},{\"desc\":\"类型\",\"name\":\"identityInfoCompareToDeliveryAddress_type\",\"paramType\":\"String\"}]";

        /** 解析字符串,得到一定顺序的列表 */
        JSONArray values = JSONArray.parseArray(protocol);


        List<DataPointParam> list = JsonUtil.jsonToList(prams, DataPointParam.class);

        System.out.println(list);



    }

}
