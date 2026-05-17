package com.tuoguan.pickup.dto;

import com.tuoguan.pickup.enums.VerificationMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmStudentRequest {

    private VerificationMethod method;

    private Long studentId;   // MANUAL 用

    private String uid;       // NFC 用

    private String faceToken; // FACE 预留

    private Long operatorId;

    private String location;
}