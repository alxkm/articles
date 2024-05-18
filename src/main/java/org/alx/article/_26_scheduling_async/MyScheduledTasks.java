package org.alx.article._26_scheduling_async;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTasks {
    @Scheduled(cron = "*/1 * * * * *") // Execute every 1 second
    public void task1() {
        // Task logic goes here
        System.out.println("Task 1 executed.");
    }

    @Scheduled(cron = "*/5 * * * * *") // Execute every 5 second
    public void task2() throws InterruptedException {
        // Task logic goes here
        System.out.println("Task 2 executed.");
        Thread.sleep(3000);
    }
}