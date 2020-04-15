package com.book.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Created by admin on 2018/12/18.
 *
 * 多个相同名称的bean，创建的时候，通过@onditional传入的类，匹配成功的bean创建
 *
 */
@Configuration
public class ConditionConfig {

    @Bean(name="test")
    @Conditional(WindowsCondition.class)
    public String windowsService(){
        return new String("windows");
    }

    @Bean(name="test")
    @Conditional(LinuxCondition.class)
    public String linuxService(){
        return new String("linux");
    }
}
