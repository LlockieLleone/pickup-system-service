package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.entity.PickupTask;
import com.tuoguan.pickup.repository.PickupTaskRepository;
import com.tuoguan.pickup.service.TaskGenerationService;
import org.springframework.web.bind.annotation.*;
import com.tuoguan.pickup.service.TaskGenerationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class PickupTaskController {

    private final PickupTaskRepository pickupTaskRepository;
    private final TaskGenerationService taskGenerationService;

    public PickupTaskController(PickupTaskRepository pickupTaskRepository, TaskGenerationService taskGenerationService) {
        this.pickupTaskRepository = pickupTaskRepository;
        this.taskGenerationService = taskGenerationService;
    }

    @PostMapping
    public PickupTask createTask(@RequestBody PickupTask task) {
        return pickupTaskRepository.save(task);
    }

    @GetMapping
    public List<PickupTask> getTasks(@RequestParam(required = false) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return pickupTaskRepository.findByDate(date);
    }

    @GetMapping("/{id}")
    public PickupTask getTaskById(@PathVariable Long id) {
        return pickupTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PickupTask not found"));
    }

    @PostMapping("/generate")
    public String generateTasks(@RequestParam(required = false) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }

        int count = taskGenerationService.generateTasksForDate(date);

        return "Generated " + count + " pickup tasks for " + date;
    }
}