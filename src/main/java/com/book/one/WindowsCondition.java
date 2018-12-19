package com.book.one;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by admin on 2018/12/18.
 */
public class WindowsCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        System.out.println(conditionContext.getEnvironment().getProperty("os.name"));
        return conditionContext.getEnvironment().getProperty("os.name").contains("Windows");
    }
}
