package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.dto.TodayTaskResponse;
import com.tuoguan.pickup.entity.PickupTask;
import com.tuoguan.pickup.entity.Student;
import com.tuoguan.pickup.repository.PickupTaskRepository;
import com.tuoguan.pickup.repository.StudentRepository;
import com.tuoguan.pickup.service.TaskGenerationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class PickupTaskController {

    private final PickupTaskRepository pickupTaskRepository;
    private final TaskGenerationService taskGenerationService;
    private final StudentRepository studentRepository;

    public PickupTaskController(PickupTaskRepository pickupTaskRepository,
                                TaskGenerationService taskGenerationService,
                                StudentRepository studentRepository) {
        this.pickupTaskRepository = pickupTaskRepository;
        this.taskGenerationService = taskGenerationService;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ApiResponse<PickupTask> createTask(@RequestBody PickupTask task) {
        return ApiResponse.ok("Pickup task created", pickupTaskRepository.save(task));
    }

    @GetMapping
    public ApiResponse<List<PickupTask>> getTasks(@RequestParam(required = false) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }

        return ApiResponse.ok(pickupTaskRepository.findByDate(date));
    }

    @GetMapping("/{id}")
    public ApiResponse<PickupTask> getTaskById(@PathVariable Long id) {
        PickupTask task = pickupTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PickupTask not found"));

        return ApiResponse.ok(task);
    }

    @PostMapping("/generate")
    public ApiResponse<Integer> generateTasks(@RequestParam(required = false) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }

        int count = taskGenerationService.generateTasksForDate(date);

        return ApiResponse.ok("Pickup tasks generated for " + date, count);
    }

    @GetMapping("/today")
    public ApiResponse<List<TodayTaskResponse>> getTodayTasksByTeacher(@RequestParam Long teacherId) {
        LocalDate today = LocalDate.now();

        List<TodayTaskResponse> tasks = pickupTaskRepository
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

        return ApiResponse.ok(tasks);
    }
}