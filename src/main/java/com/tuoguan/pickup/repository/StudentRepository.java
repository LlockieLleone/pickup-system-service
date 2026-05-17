package com.tuoguan.pickup.repository;

import com.tuoguan.pickup.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}