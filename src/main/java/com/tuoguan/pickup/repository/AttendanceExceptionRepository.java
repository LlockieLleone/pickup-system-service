package com.tuoguan.pickup.repository;

import com.tuoguan.pickup.entity.AttendanceException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceExceptionRepository extends JpaRepository<AttendanceException, Long> {

    Optional<AttendanceException> findByStudentIdAndDate(Long studentId, LocalDate date);
}