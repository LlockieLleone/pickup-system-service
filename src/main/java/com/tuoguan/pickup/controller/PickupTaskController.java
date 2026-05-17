package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.entity.PickupTask;
import com.tuoguan.pickup.repository.PickupTaskRepository;
import com.tuoguan.pickup.repository.StudentRepository;
import com.tuoguan.pickup.service.TaskGenerationService;
import org.springframework.web.bind.annotation.*;
import com.tuoguan.pickup.dto.TodayTaskResponse;
import com.tuoguan.pickup.entity.Student;
import com.tuoguan.pickup.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class PickupTaskController {

    private final PickupTaskRepository pickupTaskRepository;
    private final TaskGenerationService taskGenerationService;
    private final StudentRepository studentRepository;

    public PickupTaskController(PickupTaskRepository pickupTaskRepository, TaskGenerationService taskGenerationService, StudentRepository studentRepository) {
        this.pickupTaskRepository = pickupTaskRepository;
        this.taskGenerationService = taskGenerationService;
        this.studentRepository = studentRepository;
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

    @GetMapping("/today")
    public List<TodayTaskResponse> getTodayTasksByTeacher(@RequestParam Long teacherId) {

        LocalDate today = LocalDate.now();

        return pickupTaskRepository
                .findByDateAndAssignedTeacherId(today, teacherId)
                .stream()
                .map(task -> {
                    Student student = studentRepository.findById(task.getStudentId())
                            .orElseThrow(() -> new RuntimeException("Student not found"));

                    return new TodayTaskResponse(
                            task.getTaskId(),
                            task.getStudentId(),
                            student.getName(),
                            task.getSchool(),
                            task.getExpectedPickupTime(),
                            task.getCurrentStatus()
                    );
                })
                .toList();
    }
}