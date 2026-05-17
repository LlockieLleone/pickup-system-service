package com.tuoguan.pickup.service;

import com.tuoguan.pickup.entity.EventLog;
import com.tuoguan.pickup.entity.PickupTask;
import com.tuoguan.pickup.enums.EventType;
import com.tuoguan.pickup.enums.TaskStatus;
import com.tuoguan.pickup.repository.EventLogRepository;
import com.tuoguan.pickup.repository.PickupTaskRepository;
import com.tuoguan.pickup.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tuoguan.pickup.dto.ScanCardRequest;
import com.tuoguan.pickup.dto.ScanCardResponse;
import com.tuoguan.pickup.entity.Card;
import com.tuoguan.pickup.repository.CardRepository;
import com.tuoguan.pickup.entity.Student;
import com.tuoguan.pickup.repository.StudentRepository;

import java.time.LocalDate;

@Service
public class EventService {

    private final EventLogRepository eventLogRepository;
    private final PickupTaskRepository pickupTaskRepository;
    private final CardRepository cardRepository;
    private final StudentRepository studentRepository;

    public EventService(EventLogRepository eventLogRepository,
                        PickupTaskRepository pickupTaskRepository,
                        CardRepository cardRepository,
                        StudentRepository studentRepository) {
        this.eventLogRepository = eventLogRepository;
        this.pickupTaskRepository = pickupTaskRepository;
        this.cardRepository = cardRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public EventLog createEvent(EventLog eventLog) {

        PickupTask task = pickupTaskRepository.findById(eventLog.getTaskId())
                .orElseThrow(() -> new RuntimeException("PickupTask not found"));

        validateStateTransition(task.getCurrentStatus(), eventLog.getEventType());

        updateTaskStatus(task, eventLog.getEventType());

        pickupTaskRepository.save(task);

        return eventLogRepository.save(eventLog);
    }

    private void updateTaskStatus(PickupTask task, EventType eventType) {

        switch (eventType) {
            case PICKED_UP -> task.setCurrentStatus(TaskStatus.PICKED_UP);
            case ARRIVED -> task.setCurrentStatus(TaskStatus.ARRIVED);
            case RELEASED -> task.setCurrentStatus(TaskStatus.RELEASED);
            case EXCEPTION -> task.setCurrentStatus(TaskStatus.EXCEPTION);
            case MANUAL_CORRECTION -> {
                throw new RuntimeException("MANUAL_CORRECTION requires admin correction logic");
            }
            default -> throw new RuntimeException("Invalid event type");
        }
    }

    private void validateStateTransition(TaskStatus currentStatus, EventType eventType) {

        switch (currentStatus) {

            case PENDING_PICKUP -> {
                if (eventType != EventType.PICKED_UP
                        && eventType != EventType.EXCEPTION) {
                    throw new RuntimeException("Invalid transition from PENDING_PICKUP");
                }
            }

            case PICKED_UP -> {
                if (eventType != EventType.ARRIVED
                        && eventType != EventType.EXCEPTION) {
                    throw new RuntimeException("Invalid transition from PICKED_UP");
                }
            }

            case ARRIVED -> {
                if (eventType != EventType.RELEASED
                        && eventType != EventType.EXCEPTION) {
                    throw new RuntimeException("Invalid transition from ARRIVED");
                }
            }

            case RELEASED -> {
                throw new RuntimeException("Task already completed");
            }

            case EXCEPTION -> {
                throw new RuntimeException("Task is in EXCEPTION status and requires admin correction");
            }

            case LEAVE -> {
                throw new RuntimeException("Student is on leave today");
            }

            case PARENT_PICKUP -> {
                throw new RuntimeException("Student is picked up by parent today");
            }
        }
    }

    @Transactional
    public ScanCardResponse scanCard(ScanCardRequest request) {

        Card card = cardRepository.findByUid(request.getUid())
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (card.getAssignedStudentId() == null) {
            throw new RuntimeException("Card is not assigned to any student");
        }

        LocalDate today = LocalDate.now();

        PickupTask task = pickupTaskRepository
                .findByDateAndStudentId(today, card.getAssignedStudentId())
                .orElseThrow(() -> new RuntimeException("Today pickup task not found"));


        Student student = studentRepository.findById(task.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        EventType nextEventType = getNextEventType(task.getCurrentStatus());

        EventLog eventLog = new EventLog();
        eventLog.setTaskId(task.getTaskId());
        eventLog.setStudentId(task.getStudentId());
        eventLog.setOperatorId(request.getOperatorId());
        eventLog.setLocation(request.getLocation());
        eventLog.setSource(request.getSource());
        eventLog.setEventType(nextEventType);
        eventLog.setNote("NFC card scan");

        EventLog savedEvent = createEvent(eventLog);

        PickupTask updatedTask = pickupTaskRepository.findById(task.getTaskId())
                .orElseThrow(() -> new RuntimeException("PickupTask not found after update"));

        return new ScanCardResponse(
                true,
                updatedTask.getStudentId(),
                student.getName(),
                updatedTask.getTaskId(),
                savedEvent.getEventType(),
                updatedTask.getCurrentStatus(),
                buildScanMessage(student.getName(), savedEvent.getEventType())
        );
    }

    private EventType getNextEventType(TaskStatus currentStatus) {

        return switch (currentStatus) {

            case PENDING_PICKUP -> EventType.PICKED_UP;

            case PICKED_UP -> EventType.ARRIVED;

            case ARRIVED -> EventType.RELEASED;

            case RELEASED ->
                    throw new RuntimeException("Task already completed");

            case EXCEPTION ->
                    throw new RuntimeException("Task is in EXCEPTION status");

            case LEAVE ->
                    throw new RuntimeException("Student is on leave today");

            case PARENT_PICKUP ->
                    throw new RuntimeException("Student is picked up by parent today");
        };
    }

    private String buildScanMessage(String studentName, EventType eventType) {

        return switch (eventType) {
            case PICKED_UP -> studentName + "已接到";
            case ARRIVED -> studentName + "已到达托管";
            case RELEASED -> studentName + "已被接走";
            case EXCEPTION -> studentName + "已记录异常";
            case MANUAL_CORRECTION -> studentName + "已人工修正";
        };
    }
}