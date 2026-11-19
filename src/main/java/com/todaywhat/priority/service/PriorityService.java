package com.todaywhat.priority.service;

import com.todaywhat.priority.dto.PriorityResponse;
import java.util.List;

public interface PriorityService {

    List<PriorityResponse> getPriorities();

    // 우선순위가 가장 높은 할 일 하나만 반환 (우선순위 높은 목록 3개 반환)
    List<PriorityResponse> recommendTasks();
}
