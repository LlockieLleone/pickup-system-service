package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.entity.EventLog;
import com.tuoguan.pickup.repository.EventLogRepository;
import com.tuoguan.pickup.service.EventService;
import org.springframework.web.bind.annotation.*;
import com.tuoguan.pickup.dto.ScanCardRequest;
import com.tuoguan.pickup.dto.ScanCardResponse;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventLogController {

    private final EventLogRepository eventLogRepository;
    private final EventService eventService;

    public EventLogController(EventLogRepository eventLogRepository, EventService eventService) {
        this.eventLogRepository = eventLogRepository;
        this.eventService = eventService;
    }

    @PostMapping
    public EventLog createEvent(@RequestBody EventLog eventLog) {
        return eventLogRepository.save(eventLog);
    }

    @GetMapping("/task/{taskId}")
    public List<EventLog> getEventsByTask(@PathVariable Long taskId) {
        return eventLogRepository.findByTaskIdOrderByTimestampAsc(taskId);
    }

    @GetMapping("/student/{studentId}")
    public List<EventLog> getEventsByStudent(@PathVariable Long studentId) {
        return eventLogRepository.findByStudentIdOrderByTimestampDesc(studentId);
    }

    @PostMapping("/scan")
    public ScanCardResponse scanCard(@RequestBody ScanCardRequest request) {
        return eventService.scanCard(request);
    }
}