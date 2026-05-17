package com.tuoguan.pickup.service;

import com.tuoguan.pickup.entity.Enrollment;
import com.tuoguan.pickup.entity.PickupTask;
import com.tuoguan.pickup.entity.Student;
import com.tuoguan.pickup.enums.TaskStatus;
import com.tuoguan.pickup.repository.AttendanceExceptionRepository;
import com.tuoguan.pickup.repository.EnrollmentRepository;
import com.tuoguan.pickup.repository.PickupTaskRepository;
import com.tuoguan.pickup.repository.StudentRepository;
import com.tuoguan.pickup.entity.AttendanceException;
import com.tuoguan.pickup.enums.ExceptionType;
import com.tuoguan.pickup.repository.AttendanceExceptionRepository;
import org.springframework.stereotype.Service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class TaskGenerationService {

    private final EnrollmentRepository enrollmentRepository;
    private final PickupTaskRepository pickupTaskRepository;
    private final StudentRepository studentRepository;
    private final AttendanceExceptionRepository attendanceExceptionRepository;

    public TaskGenerationService(EnrollmentRepository enrollmentRepository,
                                 PickupTaskRepository pickupTaskRepository,
                                 StudentRepository studentRepository,
                                 AttendanceExceptionRepository attendanceExceptionRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.pickupTaskRepository = pickupTaskRepository;
        this.studentRepository = studentRepository;
        this.attendanceExceptionRepository = attendanceExceptionRepository;
    }

    public int generateTasksForDate(LocalDate date) {

        List<Enrollment> enrollments = enrollmentRepository.findByActiveTrue();
        int count = 0;

        for (Enrollment enrollment : enrollments) {

            if (!isEnrollmentValidForDate(enrollment, date)) {
                continue;
            }

            if (!isWeekdayMatched(enrollment.getWeekdays(), date.getDayOfWeek())) {
                continue;
            }

            if (pickupTaskRepository.existsByDateAndStudentId(date, enrollment.getStudentId())) {
                continue;
            }

            Student student = studentRepository.findById(enrollment.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            AttendanceException exception = attendanceExceptionRepository
                    .findByStudentIdAndDate(enrollment.getStudentId(), date)
                    .orElse(null);

            PickupTask task = new PickupTask();
            task.setDate(date);
            task.setStudentId(student.getStudentId());
            task.setSchool(student.getSchool());
            if (exception == null) {
                task.setAssignedTeacherId(enrollment.getDefaultTeacherId());
                task.setExpectedPickupTime(enrollment.getDefaultPickupTime());
                task.setCurrentStatus(TaskStatus.PENDING_PICKUP);
                task.setGeneratedFrom("NORMAL");
            } else {
                applyExceptionToTask(task, enrollment, exception);
            }

            pickupTaskRepository.save(task);
            count++;
        }

        return count;
    }

    private boolean isEnrollmentValidForDate(Enrollment enrollment, LocalDate date) {

        if (date.isBefore(enrollment.getStartDate())) {
            return false;
        }

        return enrollment.getEndDate() == null || !date.isAfter(enrollment.getEndDate());
    }

    private void applyExceptionToTask(PickupTask task,
                                      Enrollment enrollment,
                                      AttendanceException exception) {

        switch (exception.getType()) {

            case LEAVE -> {
                task.setAssignedTeacherId(enrollment.getDefaultTeacherId());
                task.setExpectedPickupTime(enrollment.getDefaultPickupTime());
                task.setCurrentStatus(TaskStatus.LEAVE);
                task.setGeneratedFrom("EXCEPTION");
            }

            case PARENT_PICKUP -> {
                task.setAssignedTeacherId(enrollment.getDefaultTeacherId());
                task.setExpectedPickupTime(enrollment.getDefaultPickupTime());
                task.setCurrentStatus(TaskStatus.PARENT_PICKUP);
                task.setGeneratedFrom("EXCEPTION");
            }

            case TIME_CHANGE -> {
                task.setAssignedTeacherId(enrollment.getDefaultTeacherId());
                task.setExpectedPickupTime(exception.getNewPickupTime());
                task.setCurrentStatus(TaskStatus.PENDING_PICKUP);
                task.setGeneratedFrom("EXCEPTION");
            }

            case TEACHER_CHANGE -> {
                task.setAssignedTeacherId(exception.getNewTeacherId());
                task.setExpectedPickupTime(enrollment.getDefaultPickupTime());
                task.setCurrentStatus(TaskStatus.PENDING_PICKUP);
                task.setGeneratedFrom("EXCEPTION");
            }

            case TIME_AND_TEACHER_CHANGE -> {
                task.setAssignedTeacherId(exception.getNewTeacherId());
                task.setExpectedPickupTime(exception.getNewPickupTime());
                task.setCurrentStatus(TaskStatus.PENDING_PICKUP);
                task.setGeneratedFrom("EXCEPTION");
            }
        }
    }

    private boolean isWeekdayMatched(String weekdays, DayOfWeek dayOfWeek) {

        String today = switch (dayOfWeek) {
            case MONDAY -> "MON";
            case TUESDAY -> "TUE";
            case WEDNESDAY -> "WED";
            case THURSDAY -> "THU";
            case FRIDAY -> "FRI";
            case SATURDAY -> "SAT";
            case SUNDAY -> "SUN";
        };

        return weekdays != null && weekdays.contains(today);
    }
}