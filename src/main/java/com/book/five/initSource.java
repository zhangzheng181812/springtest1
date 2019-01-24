package com.book.five;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by admin on 2019/1/15.
 */
@Configuration
public class initSource {
    @Bean("topLeadClient")
    public TopLeadClient topLeadClient(){
        return new TopLeadClient("123","456");
    }
}
