package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.entity.AttendanceException;
import com.tuoguan.pickup.repository.AttendanceExceptionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance-exceptions")
public class AttendanceExceptionController {

    private final AttendanceExceptionRepository attendanceExceptionRepository;

    public AttendanceExceptionController(
            AttendanceExceptionRepository attendanceExceptionRepository
    ) {
        this.attendanceExceptionRepository = attendanceExceptionRepository;
    }

    @PostMapping
    public ApiResponse<AttendanceException> createException(
            @RequestBody AttendanceException exception
    ) {

        AttendanceException saved =
                attendanceExceptionRepository.save(exception);

        return ApiResponse.ok(
                "Attendance exception created",
                saved
        );
    }

    @GetMapping
    public ApiResponse<List<AttendanceException>> getAllExceptions() {

        List<AttendanceException> exceptions =
                attendanceExceptionRepository.findAll();

        return ApiResponse.ok(exceptions);
    }

    @GetMapping("/{id}")
    public ApiResponse<AttendanceException> getExceptionById(
            @PathVariable Long id
    ) {

        AttendanceException exception =
                attendanceExceptionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "AttendanceException not found"
                                )
                        );

        return ApiResponse.ok(exception);
    }
}