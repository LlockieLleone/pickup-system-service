package com.tuoguan.pickup.repository;

import com.tuoguan.pickup.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {

    List<NotificationLog> findByStudentIdOrderByCreatedAtDesc(Long studentId);

    List<NotificationLog> findByEventId(Long eventId);
}