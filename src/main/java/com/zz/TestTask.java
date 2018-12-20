package com.zz;

import com.service.TackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by admin on 2018/9/6.
 */
@RestController
public class TestTask {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler ;

    private ScheduledFuture<?> future;

    @RequestMapping("/startCron")
    public String startCron() {

        future = threadPoolTaskScheduler.schedule(new TackService(), new CronTrigger("0/5 * * * * *"));

        System.out.println("DynamicTask.startCron()");

        return "startCron";

    }



    @RequestMapping("/stopCron")
    public String stopCron() {
        if (future != null) {
            future.cancel(true);
        }
        System.out.println("DynamicTask.stopCron()");

        return "stopCron";
    }



    @RequestMapping("/changeCron10")
    public String startCron10() {
        stopCron();// 先停止，在开启.
        future = threadPoolTaskScheduler.schedule(new TackService(), new CronTrigger("*/10 * * * * *"));
        System.out.println("DynamicTask.startCron10()");
        return "changeCron10";

    }
}
