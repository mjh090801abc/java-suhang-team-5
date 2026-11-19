
package com.todaywhat.priority.controller;

import com.todaywhat.priority.dto.PriorityResponse;
import com.todaywhat.priority.service.PriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/priority")
@RequiredArgsConstructor
public class PriorityController {

    private final PriorityService priorityService;

    // 전체 우선순위 조회
    @GetMapping
    public ResponseEntity<List<PriorityResponse>> getPriorities() {
        return ResponseEntity.ok(priorityService.getPriorities());
    }

    // 우선순위 상위 3개 추천
    @GetMapping("/recommend")
    public ResponseEntity<List<PriorityResponse>> recommendTasks() {
        return ResponseEntity.ok(priorityService.recommendTasks());
    }
}
