package com.service;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by admin on 2018/9/6.
 */
@Service
public class TackService implements Runnable{

    @Override
    public void run() {
        System.out.println("TackService==============================="+new Date());
    }
}
