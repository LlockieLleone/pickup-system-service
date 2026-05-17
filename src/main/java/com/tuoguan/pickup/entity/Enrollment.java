package com.tuoguan.pickup.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false, length = 50)
    private String weekdays;
    // 例如：MON,TUE,WED,THU,FRI

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private LocalTime defaultPickupTime;

    @Column(nullable = false)
    private Long defaultTeacherId;

    @Column(nullable = false)
    private Boolean active = true;
}