package com.todaywhat.task.controller;
import com.todaywhat.task.dto.request.TaskRequest; // 요청 데이터 전달받는 클래스
import com.todaywhat.task.dto.request.TaskStatusRequest;
import com.todaywhat.task.dto.response.TaskResponse; // 응답 데이터 전달 클래스
import com.todaywhat.task.service.TaskService;  // 할 일 기능 처리 Service
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Spring 웹 관련 어노테이션 가져오기
import java.util.List;

@RestController
@RequestMapping("/tasks") // 모든 요청의 기본 URL
public class TaskController {
    private final TaskService taskService; // TaskService 객체 저장

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 할 일 등록
    @PostMapping
    public ResponseEntity<TaskResponse> createTask (
        @RequestBody TaskRequest request // 요청 본문 JSON 데이터 -> TaskRequest 객체로 변환하여 저장
    ) {
        // Service에서 할 일 등록하고, 201(CREATED) + 등록 결과 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.creatTask(request));
    }


    // 전체 할 일 조회
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // 특정 할 일 조회
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask (
            // URL에 포함된 값을 가져옴.
            @PathVariable Long id
    ) {
        // Service에서 해당 ID의 할 일 조회하고, 200(OK) + 조회 결과 반환
        return ResponseEntity.ok(taskService.getTask(id));
    }

    // 할 일 수정
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask (
            @PathVariable Long id,
            @RequestBody TaskRequest request
    ) {
        // Service에서 해당 ID의 할 일을 수정하고, 200(OK)과 수정 결과 반환
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    // 할 일 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask (
            @PathVariable Long id
    ) {
        taskService.deleteTask(id); // 해당 ID 할 일 삭제 요청
        return ResponseEntity.noContent().build(); //204(NO_CONTENT) 반환
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody TaskStatusRequest request) {
        return ResponseEntity.ok(taskService.updateStatus(id, request));
    }


}
