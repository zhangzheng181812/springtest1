package com.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2018/8/23.
 */
@Service
public class AsyncService {

    @Async("activeExecutor")
    public void doAsync(int i){
        System.out.println(i);
        i++;
    }
}
