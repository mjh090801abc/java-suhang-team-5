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

    @GetMapping
    public ResponseEntity<List<PriorityResponse>> getPriorities() {
        return ResponseEntity.ok(priorityService.getPriorities());
    }
}
