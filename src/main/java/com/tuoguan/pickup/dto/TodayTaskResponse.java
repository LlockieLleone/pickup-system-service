package com.tuoguan.pickup.dto;

import com.tuoguan.pickup.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class TodayTaskResponse {

    private Long taskId;

    private Long studentId;

    private String studentName;

    private String school;

    private LocalTime expectedPickupTime;

    private TaskStatus currentStatus;
}