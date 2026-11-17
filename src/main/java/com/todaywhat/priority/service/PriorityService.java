package com.todaywhat.priority.service;

import com.todaywhat.priority.dto.PriorityResponse;
import java.util.List;

public interface PriorityService {

    List<PriorityResponse> getPriorities();
}
