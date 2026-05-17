package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.entity.AttendanceException;
import com.tuoguan.pickup.repository.AttendanceExceptionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance-exceptions")
public class AttendanceExceptionController {

    private final AttendanceExceptionRepository attendanceExceptionRepository;

    public AttendanceExceptionController(AttendanceExceptionRepository attendanceExceptionRepository) {
        this.attendanceExceptionRepository = attendanceExceptionRepository;
    }

    @PostMapping
    public AttendanceException createException(@RequestBody AttendanceException exception) {
        return attendanceExceptionRepository.save(exception);
    }

    @GetMapping
    public List<AttendanceException> getAllExceptions() {
        return attendanceExceptionRepository.findAll();
    }

    @GetMapping("/{id}")
    public AttendanceException getExceptionById(@PathVariable Long id) {
        return attendanceExceptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AttendanceException not found"));
    }
}