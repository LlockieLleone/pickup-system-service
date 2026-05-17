package com.tuoguan.pickup.repository;

import com.tuoguan.pickup.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByUid(String uid);
}