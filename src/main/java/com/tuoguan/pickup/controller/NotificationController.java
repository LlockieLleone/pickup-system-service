package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.entity.NotificationLog;
import com.tuoguan.pickup.repository.NotificationLogRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationLogRepository notificationLogRepository;

    public NotificationController(NotificationLogRepository notificationLogRepository) {
        this.notificationLogRepository = notificationLogRepository;
    }

    @GetMapping("/student/{studentId}")
    public ApiResponse<List<NotificationLog>> getByStudent(@PathVariable Long studentId) {
        return ApiResponse.ok(
                notificationLogRepository.findByStudentIdOrderByCreatedAtDesc(studentId)
        );
    }

    @GetMapping("/event/{eventId}")
    public ApiResponse<List<NotificationLog>> getByEvent(@PathVariable Long eventId) {
        return ApiResponse.ok(
                notificationLogRepository.findByEventId(eventId)
        );
    }
}