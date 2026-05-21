package com.tuoguan.pickup.service;

import com.tuoguan.pickup.entity.AttendanceException;
import com.tuoguan.pickup.entity.Enrollment;
import com.tuoguan.pickup.entity.PickupTask;
import com.tuoguan.pickup.enums.TaskStatus;
import com.tuoguan.pickup.repository.AttendanceExceptionRepository;
import com.tuoguan.pickup.repository.EnrollmentRepository;
import com.tuoguan.pickup.repository.PickupTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendanceExceptionService {

    private final AttendanceExceptionRepository attendanceExceptionRepository;
    private final PickupTaskRepository pickupTaskRepository;
    private final EnrollmentRepository enrollmentRepository;

    public AttendanceExceptionService(
            AttendanceExceptionRepository attendanceExceptionRepository,
            PickupTaskRepository pickupTaskRepository,
            EnrollmentRepository enrollmentRepository
    ) {
        this.attendanceExceptionRepository = attendanceExceptionRepository;
        this.pickupTaskRepository = pickupTaskRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional
    public AttendanceException createOrUpdateException(AttendanceException request) {

        AttendanceException exception = attendanceExceptionRepository
                .findByStudentIdAndDate(request.getStudentId(), request.getDate())
                .orElse(new AttendanceException());

        exception.setStudentId(request.getStudentId());
        exception.setDate(request.getDate());
        exception.setType(request.getType());
        exception.setNewTeacherId(request.getNewTeacherId());
        exception.setNewPickupTime(request.getNewPickupTime());
        exception.setNote(request.getNote());

        AttendanceException savedException = attendanceExceptionRepository.save(exception);

        pickupTaskRepository
                .findByDateAndStudentId(request.getDate(), request.getStudentId())
                .ifPresent(task -> applyExceptionToExistingTask(task, savedException));

        return savedException;
    }

    private void applyExceptionToExistingTask(
            PickupTask task,
            AttendanceException exception
    ) {
        ensureTaskCanBeModified(task);

        Enrollment enrollment = enrollmentRepository
                .findByActiveTrue()
                .stream()
                .filter(e -> e.getStudentId().equals(task.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        switch (exception.getType()) {

            case LEAVE -> {
                task.setCurrentStatus(TaskStatus.LEAVE);
                task.setGeneratedFrom("EXCEPTION");
            }

            case PARENT_PICKUP -> {
                task.setCurrentStatus(TaskStatus.PARENT_PICKUP);
                task.setGeneratedFrom("EXCEPTION");
            }

            case TIME_CHANGE -> {
                if (exception.getNewPickupTime() == null) {
                    throw new RuntimeException("newPickupTime is required for TIME_CHANGE");
                }

                task.setExpectedPickupTime(exception.getNewPickupTime());
                task.setAssignedTeacherId(enrollment.getDefaultTeacherId());
                task.setCurrentStatus(TaskStatus.PENDING_PICKUP);
                task.setGeneratedFrom("EXCEPTION");
            }

            case TEACHER_CHANGE -> {
                if (exception.getNewTeacherId() == null) {
                    throw new RuntimeException("newTeacherId is required for TEACHER_CHANGE");
                }

                task.setAssignedTeacherId(exception.getNewTeacherId());
                task.setExpectedPickupTime(enrollment.getDefaultPickupTime());
                task.setCurrentStatus(TaskStatus.PENDING_PICKUP);
                task.setGeneratedFrom("EXCEPTION");
            }

            case TIME_AND_TEACHER_CHANGE -> {
                if (exception.getNewPickupTime() == null) {
                    throw new RuntimeException("newPickupTime is required for TIME_AND_TEACHER_CHANGE");
                }

                if (exception.getNewTeacherId() == null) {
                    throw new RuntimeException("newTeacherId is required for TIME_AND_TEACHER_CHANGE");
                }

                task.setExpectedPickupTime(exception.getNewPickupTime());
                task.setAssignedTeacherId(exception.getNewTeacherId());
                task.setCurrentStatus(TaskStatus.PENDING_PICKUP);
                task.setGeneratedFrom("EXCEPTION");
            }
        }

        pickupTaskRepository.save(task);
    }

    private void ensureTaskCanBeModified(PickupTask task) {
        if (!canModifyTask(task.getCurrentStatus())) {
            throw new RuntimeException(
                    "Cannot modify attendance exception because student task is already in progress or completed. Current status: "
                            + task.getCurrentStatus()
            );
        }
    }

    private boolean canModifyTask(TaskStatus status) {
        return status == TaskStatus.PENDING_PICKUP
                || status == TaskStatus.LEAVE
                || status == TaskStatus.PARENT_PICKUP;
    }
}