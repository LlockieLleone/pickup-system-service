package com.tuoguan.pickup.dto;

import com.tuoguan.pickup.enums.EventType;
import com.tuoguan.pickup.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScanCardResponse {

    private boolean success;

    private Long studentId;

    private String studentName;

    private Long taskId;

    private EventType eventType;

    private TaskStatus currentStatus;

    private String message;
}