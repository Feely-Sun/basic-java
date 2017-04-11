package com.basic.leanring.java.intercepter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;


/**
 * @author sunzihan
 * @version $Id: MethodIntercepter.java V 0.1 2/27/17 11:29 sunzihan EXP $
 */
public class MethodIntercepter  implements MethodInterceptor {

    private String name;


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        java.lang.reflect.Method  method = invocation.getMethod();
        Class<?> clazz = method.getDeclaringClass();

        String classStr = clazz.getName();
        String mtstr = method.toString();

        String res = StringUtils.substringAfter(mtstr,classStr);
        Object obj = invocation.proceed();

        return obj;
    }


//
//
//    @Override
//    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
//
//        Method[] methods =o.getClass().getMethods();
//
//        Map<String,Method> methodMap = new HashMap<>();
//        for (Method m : methods){
//
//            methodMap.put(StringUtils.substringAfter(m.toString(),m.getDeclaringClass().getName()),m);
//            System.out.println(m.getName());
//
//        }
//
//
//
//        return o;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
//        return o;
//    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

