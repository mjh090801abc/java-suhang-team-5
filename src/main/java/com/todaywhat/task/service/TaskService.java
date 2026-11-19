package com.todaywhat.task.service;

import com.todaywhat.task.dto.request.TaskRequest;
import com.todaywhat.task.dto.request.TaskStatusRequest;
import com.todaywhat.task.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {
    //할 일 등록
    TaskResponse creatTask(TaskRequest request);

    // 할 일 전체 조회
    List<TaskResponse> getAllTasks();

    //할 일 선택(ID) 조회
    TaskResponse getTask(Long id);

    //할 일 선택(제목) 조회
    TaskResponse getTaskByTitle(String title);

    // 할 일 수정
    TaskResponse updateTask(Long id, TaskRequest taskRequest);

    // 할 일 삭제
    void deleteTask(Long id);

    // 할 일 상태
    TaskResponse updateStatus(Long id, TaskStatusRequest request);
}
