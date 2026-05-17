package com.tuoguan.pickup.entity;

import com.tuoguan.pickup.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(
        name = "pickup_tasks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"date", "studentId"})
        }
)
public class PickupTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false, length = 100)
    private String school;

    @Column(nullable = false)
    private Long assignedTeacherId;

    @Column(nullable = false)
    private LocalTime expectedPickupTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TaskStatus currentStatus = TaskStatus.PENDING_PICKUP;

    @Column(nullable = false, length = 30)
    private String generatedFrom = "NORMAL";

    @Column(nullable = false)
    private Boolean locked = false;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}