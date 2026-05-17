package com.tuoguan.pickup.repository;

import com.tuoguan.pickup.entity.PickupTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PickupTaskRepository extends JpaRepository<PickupTask, Long> {

    List<PickupTask> findByDate(LocalDate date);

    List<PickupTask> findByDateAndAssignedTeacherId(LocalDate date, Long assignedTeacherId);

    Optional<PickupTask> findByDateAndStudentId(LocalDate date, Long studentId);

    boolean existsByDateAndStudentId(LocalDate date, Long studentId);
}