package com.tuoguan.pickup.scheduler;

import com.tuoguan.pickup.service.TaskGenerationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TaskGenerationScheduler {

    private final TaskGenerationService taskGenerationService;

    public TaskGenerationScheduler(TaskGenerationService taskGenerationService) {
        this.taskGenerationService = taskGenerationService;
    }

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Shanghai")
    public void generateTodayTasks() {
        LocalDate today = LocalDate.now();
        int count = taskGenerationService.generateTasksForDate(today);

        System.out.println("Generated " + count + " pickup tasks for " + today);
    }
}