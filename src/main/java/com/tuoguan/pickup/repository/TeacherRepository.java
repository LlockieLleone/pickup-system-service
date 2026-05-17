package com.tuoguan.pickup.repository;

import com.tuoguan.pickup.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByPhone(String phone);
}