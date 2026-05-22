package com.tuoguan.pickup.dto;

import com.tuoguan.pickup.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private boolean success;

    private Long teacherId;

    private String teacherName;

    private UserRole role;

    private String token;

    private String message;
}