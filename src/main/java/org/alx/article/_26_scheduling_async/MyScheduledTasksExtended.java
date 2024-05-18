package org.alx.article._26_scheduling_async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTasksExtended {
    @Scheduled(cron = "*/1 * * * * *") // Execute every 1 second
    @Async // Execute in a separate thread
    public void task1() {
        // Task logic goes here
        System.out.println("Task 1 executed.");
    }

    @Scheduled(cron = "*/5 * * * * *") // Execute every 5 seconds
    @Async // Execute in a separate thread
    public void task2() {
        // Task logic goes here
        System.out.println("Task 2 executed.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // Handle interrupted exception
            e.printStackTrace();
        }
    }
}