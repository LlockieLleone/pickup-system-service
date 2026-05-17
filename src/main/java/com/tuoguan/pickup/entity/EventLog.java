package com.tuoguan.pickup.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.tuoguan.pickup.enums.EventType;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "event_logs")
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(nullable = false)
    private Long taskId;

    @Column(nullable = false)
    private Long studentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EventType eventType;

    @Column(nullable = false)
    private Long operatorId;
    // 老师ID / 管理员ID

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(length = 100)
    private String location;

    @Column(nullable = false, length = 30)
    private String source = "MANUAL";
    // RFID, MANUAL, ADMIN

    @Column(length = 255)
    private String note;
}