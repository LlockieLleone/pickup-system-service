package com.tuoguan.pickup.entity;

import com.tuoguan.pickup.enums.NotificationChannel;
import com.tuoguan.pickup.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "notification_logs")
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private NotificationChannel channel = NotificationChannel.SMS;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.PENDING;

    @Column(nullable = false, length = 30)
    private String recipientPhone;

    @Column(nullable = false, length = 255)
    private String content;

    @Column(length = 255)
    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime sentAt;
}