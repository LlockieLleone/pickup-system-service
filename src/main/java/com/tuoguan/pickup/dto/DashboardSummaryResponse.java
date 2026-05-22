package com.tuoguan.pickup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DashboardSummaryResponse {

    private int total;

    private int pendingPickup;

    private int pickedUp;

    private int arrived;

    private int released;

    private int leave;

    private int parentPickup;

    private int exception;
}