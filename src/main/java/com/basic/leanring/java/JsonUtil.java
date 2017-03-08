package com.basic.leanring.java;




import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunzihan
 * @version $Id: JsonUtil.java V 0.1 3/1/17 19:53 sunzihan EXP $
 */
public class JsonUtil {

    /**
     * object对象转换为json格式的字符串
     *
     * @param obj
     * @return
     */
    public static String objectToString(Object obj) {
        return JSONObject.fromObject(obj).toString();
    }

    /**
     * json格式字符串转换为Object对象
     *
     * @param jsonString
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToObject(String jsonString, Class<T> pojoCalss) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        return (T) (JSONObject.toBean(jsonObject, pojoCalss));

    }

    /**
     * list对象转换为json格式的字符串
     * @param list
     * @return String
     */
    public static String listToJson(List<?> list) {
        return JSONArray.fromObject(list).toString();
    }

    /**
     * json格式字符串转换为List对象
     *
     * @param jsonString
     * @param classz
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonToList(String jsonString, Class<T> classz) {
        List<T> arrayList = new ArrayList<T>();
        JSONArray jsonArray = JSONArray.fromObject(jsonString);

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            arrayList.add((T) JSONObject.toBean(jsonObject, classz));
        }
        return arrayList;
    }

    /**
     * 转化为List
     *
     * @param jsonString
     * @return
     */
    public static List<Integer> jsonToIntegerList(String jsonString) {
        List<Integer> arrayList = new ArrayList<Integer>();
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        for (int i = 0; i < jsonArray.size(); i++) {
            arrayList.add(jsonArray.getInt(i));
        }
        return arrayList;
    }
}

