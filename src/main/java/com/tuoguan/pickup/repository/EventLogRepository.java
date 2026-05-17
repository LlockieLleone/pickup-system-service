package com.tuoguan.pickup.repository;

import com.tuoguan.pickup.entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

    List<EventLog> findByTaskIdOrderByTimestampAsc(Long taskId);

    List<EventLog> findByStudentIdOrderByTimestampDesc(Long studentId);
}