package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.dto.ConfirmStudentRequest;
import com.tuoguan.pickup.dto.ScanCardRequest;
import com.tuoguan.pickup.dto.ScanCardResponse;
import com.tuoguan.pickup.entity.EventLog;
import com.tuoguan.pickup.repository.EventLogRepository;
import com.tuoguan.pickup.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventLogController {

    private final EventLogRepository eventLogRepository;
    private final EventService eventService;

    public EventLogController(EventLogRepository eventLogRepository,
                              EventService eventService) {
        this.eventLogRepository = eventLogRepository;
        this.eventService = eventService;
    }

    @PostMapping
    public ApiResponse<EventLog> createEvent(@RequestBody EventLog eventLog) {
        return ApiResponse.ok("Event created", eventService.createEvent(eventLog));
    }

    @PostMapping("/confirm")
    public ApiResponse<ScanCardResponse> confirmStudent(@RequestBody ConfirmStudentRequest request) {
        return ApiResponse.ok("Student confirmed", eventService.confirmStudent(request));
    }

    @PostMapping("/scan")
    public ApiResponse<ScanCardResponse> scanCard(@RequestBody ScanCardRequest request) {
        return ApiResponse.ok("Card scanned", eventService.scanCard(request));
    }

    @GetMapping("/task/{taskId}")
    public ApiResponse<List<EventLog>> getEventsByTask(@PathVariable Long taskId) {
        return ApiResponse.ok(eventLogRepository.findByTaskIdOrderByTimestampAsc(taskId));
    }

    @GetMapping("/student/{studentId}")
    public ApiResponse<List<EventLog>> getEventsByStudent(@PathVariable Long studentId) {
        return ApiResponse.ok(eventLogRepository.findByStudentIdOrderByTimestampDesc(studentId));
    }
}