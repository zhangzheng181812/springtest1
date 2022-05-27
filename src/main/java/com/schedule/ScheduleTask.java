package com.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

//    @Scheduled(fixedRate = 5 *1000)
    public void myTask(){
        System.out.println("-------------------myTask-------------------");
    }


    /**


     # 这个方法每隔 5 秒就会被调用，5 秒钟的间隔是指从上一次调用的完成之时开始算起
     @Scheduled(fixedDelay=5000)


     # 这个方法每隔 5 秒就会被调用，此时的 5 秒就是从上一次调用之始开始算起了
     @Scheduled(fixedRate=5000)


     #  initialDelay 属性来指定一个延迟时间，下面这个方法会先等待 1 秒
     @Scheduled(initialDelay=1000, fixedRate=5000)


     # cron表达式 每*分钟执行一次
     @Scheduled(cron="0 1 * * * ?")



     **/
}
