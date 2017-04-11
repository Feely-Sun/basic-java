package com.basic.leanring.java.indicator;

import java.util.Map;

import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

/**
 * @author sunzihan
 * @version $Id: ELBasedAttributeAccessor.java V 0.1 3/15/17 16:14 sunzihan EXP $
 */
public class ELBasedAttributeAccessor {


    /** 变量的名称 */
    private String     varName;

    /** 编译后的表达式 */
    private Expression expression;

    /**
     * 工厂方法。
     *
     * @param expr 属性访问表达式
     * @return 基于表达式的属性访问器
     */
    public static ELBasedAttributeAccessor newInstance(String expr) {
        Expression expression = AviatorEvaluator.compile(expr);
        String varName = expr.split("\\.")[0].trim();
        return new ELBasedAttributeAccessor(expression, varName);
    }

    private ELBasedAttributeAccessor(Expression expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }

    /**
     * 获取目标对象中的某个属性。
     *
     * @param target 包含需要属性的目标对象
     * @return 希望获取的属性
     */
    public Object getAttributeFrom(Object target) {
        Map<String, Object> env = Maps.newHashMap();
        env.put(varName, target);
        return expression.execute(env);
    }


}

