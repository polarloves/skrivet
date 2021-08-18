package com.skrivet.core.common.util;

import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

/**
 * spel表达式工具类
 */
public class SpringElUtil {
    public static <T> T parserValue(String expression, Object[] params, String[] paramNames, Class<T> tClass) {
        if (StringUtils.isEmpty(expression)) {
            return null;
        }
        StandardEvaluationContext itemContext = standardEvaluationContext(params, paramNames,null);
        return new SpelExpressionParser().parseExpression(expression).getValue(itemContext, tClass);
    }

    private static StandardEvaluationContext standardEvaluationContext(Object[] params, String[] paramNames,Object result) {
        StandardEvaluationContext itemContext = new StandardEvaluationContext();
        for (int i = 0; i < paramNames.length; i++) {
            itemContext.setVariable(paramNames[i], params[i]);
        }
        if(result!=null){
            itemContext.setVariable("result", result);
        }
        return itemContext;
    }

    public static Object parserValue(String expression, Object[] params, String[] paramNames) {
        if (StringUtils.isEmpty(expression)) {
            return null;
        }
        StandardEvaluationContext itemContext = standardEvaluationContext(params, paramNames,null);
        return new SpelExpressionParser().parseExpression(expression).getValue(itemContext);
    }
    public static Object parserValue(String expression, Object[] params, String[] paramNames,Object result) {
        if (StringUtils.isEmpty(expression)) {
            return null;
        }
        StandardEvaluationContext itemContext = standardEvaluationContext(params, paramNames,result);
        return new SpelExpressionParser().parseExpression(expression).getValue(itemContext);
    }

    public static <T>T parserValue(String expression, Object[] params, String[] paramNames,Object result, Class<T> tClass) {
        if (StringUtils.isEmpty(expression)) {
            return null;
        }
        StandardEvaluationContext itemContext = standardEvaluationContext(params, paramNames,result);
        return new SpelExpressionParser().parseExpression(expression).getValue(itemContext,tClass);
    }
}
