package com.tuoguan.pickup.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
@Table(name = "guardians")
public class Guardian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guardianId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 30)
    private String relation;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToMany(mappedBy = "guardians")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();
}