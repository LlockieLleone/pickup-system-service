package com.tuoguan.pickup.entity;

import com.tuoguan.pickup.enums.ExceptionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "attendance_exceptions")
public class AttendanceException {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exceptionId;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private ExceptionType type;

    private Long newTeacherId;

    private LocalTime newPickupTime;

    @Column(length = 255)
    private String note;
}