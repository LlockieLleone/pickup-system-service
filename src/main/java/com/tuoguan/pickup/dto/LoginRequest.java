package com.tuoguan.pickup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String phone;

    private String password;
}