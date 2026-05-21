package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.entity.AttendanceException;
import com.tuoguan.pickup.repository.AttendanceExceptionRepository;
import com.tuoguan.pickup.service.AttendanceExceptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance-exceptions")
public class AttendanceExceptionController {

    private final AttendanceExceptionRepository attendanceExceptionRepository;
    private final AttendanceExceptionService attendanceExceptionService;

    public AttendanceExceptionController(
            AttendanceExceptionRepository attendanceExceptionRepository,
            AttendanceExceptionService attendanceExceptionService
    ) {
        this.attendanceExceptionRepository = attendanceExceptionRepository;
        this.attendanceExceptionService = attendanceExceptionService;
    }

    @PostMapping
    public ApiResponse<AttendanceException> createException(
            @RequestBody AttendanceException exception
    ) {
        AttendanceException saved =
                attendanceExceptionService.createOrUpdateException(exception);

        return ApiResponse.ok(
                "Attendance exception saved",
                saved
        );
    }

    @GetMapping
    public ApiResponse<List<AttendanceException>> getAllExceptions() {
        return ApiResponse.ok(attendanceExceptionRepository.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<AttendanceException> getExceptionById(
            @PathVariable Long id
    ) {
        AttendanceException exception =
                attendanceExceptionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("AttendanceException not found")
                        );

        return ApiResponse.ok(exception);
    }
}