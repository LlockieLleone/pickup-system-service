package com.tuoguan.pickup.repository;

import com.tuoguan.pickup.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByActiveTrue();
}