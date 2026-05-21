package com.tuoguan.pickup.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "system_settings")
public class SystemSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settingId;

    @Column(nullable = false, unique = true, length = 100)
    private String settingKey;

    @Column(nullable = false, length = 255)
    private String settingValue;

    @Column(length = 255)
    private String description;
}