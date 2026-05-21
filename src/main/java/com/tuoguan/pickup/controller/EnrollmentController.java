package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.entity.Enrollment;
import com.tuoguan.pickup.repository.EnrollmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentController(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @PostMapping
    public ApiResponse<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) {
        return ApiResponse.ok("Enrollment created", enrollmentRepository.save(enrollment));
    }

    @GetMapping
    public ApiResponse<List<Enrollment>> getAllEnrollments() {
        return ApiResponse.ok(enrollmentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Enrollment> getEnrollmentById(@PathVariable Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        return ApiResponse.ok(enrollment);
    }
}