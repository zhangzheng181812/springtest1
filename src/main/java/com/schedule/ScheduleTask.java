package com.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

//    @Scheduled(fixedRate = 5 *1000)
    public void myTask(){
        System.out.println("-------------------myTask-------------------");
    }
}
