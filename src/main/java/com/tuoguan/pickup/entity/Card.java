package com.tuoguan.pickup.entity;

import com.tuoguan.pickup.enums.CardStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(nullable = false, unique = true, length = 100)
    private String uid;

    @Column(length = 30)
    private String type = "RFID";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CardStatus status = CardStatus.ACTIVE;

    @Column
    private Long assignedStudentId;
}