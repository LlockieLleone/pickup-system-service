package com.tuoguan.pickup.repository;

import com.tuoguan.pickup.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {
}