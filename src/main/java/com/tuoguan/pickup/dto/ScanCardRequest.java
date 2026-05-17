package com.tuoguan.pickup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScanCardRequest {

    private String uid;

    private Long operatorId;

    private String location;

    private String source = "ANDROID_NFC";
}