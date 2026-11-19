package com.todaywhat.statistics.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class StatisticsResponse {

    private final long total;
    private final long completed;
    private final long remaining;
    private final double completionRate;
}