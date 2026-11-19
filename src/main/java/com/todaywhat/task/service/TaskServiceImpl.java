package com.todaywhat.task.service;

import com.todaywhat.task.domain.Task;
import com.todaywhat.task.dto.request.TaskRequest;
import com.todaywhat.task.dto.request.TaskStatusRequest;
import com.todaywhat.task.dto.response.TaskResponse;
import com.todaywhat.task.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true) // 데이터베이스 작업을 하나의 트랜잭션으로 관리
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;


    public TaskServiceImpl(TaskRepository taskRepository) { // 생성자를 통해 Repository 객체 전달받기
        this.taskRepository = taskRepository;  // 전달받은 Repository 객체 필드에 저장
    }

    //할 일 등록
    @Override //인터페이스 메서드 재정의
    @Transactional // 데이터 변경 작업 가능
    public TaskResponse creatTask(TaskRequest request){

        validate(request); // 전달받은 할 일 정보가 올바른지 검사

        Task task = new Task( // 요청받은 정보로 새로운 Task 생성
                request.getTitle().trim(), //앞뒤 공백 제거
                request.getDeadline(),
                request.getImportance()
        );
        Task savedTask = taskRepository.save(task); // 데이터베이스에 저장

        return TaskResponse.from(savedTask); //저장된 할 일 정보를 응답 DTO로 변환하여 반환
    }

    // 전체 할 일 조회
    @Override
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream().map(TaskResponse::from).toList();
        // 모든 할 일 조회 -> 각각 TaskResponse로 변환 -> List로 반환
    }

    // 특정 할 일 조회
    @Override
    public TaskResponse getTask(Long id) {
        Task task = findTask(id); // ID로 할일 검색, 결과를 task에 저장

        return TaskResponse.from(task);
    }

    // 제목으로 할 일 조회
    @Override
    public TaskResponse getTaskByTitle(String title) {
        Task task = taskRepository.findByTitle(title) // 제목이 일치하는 할 일 검색
                .orElseThrow(() -> new ResponseStatusException( // 검색 결과 없으면 예외
                        HttpStatus.NOT_FOUND,
                        "해당 제목의 할 일을 찾을 수 없습니다."
                ));

        return TaskResponse.from(task);
    }

    //할 일 수정
    @Override
    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest request) {
        validate(request); // 수정할 정보가 올바른지 검사

        Task task = findTask(id);  // ID로 기존 할 일 검색

        task.update(
                request.getTitle().trim(), //앞 뒤 공백 제거
                request.getDeadline(),
                request.getImportance()
        );
        return TaskResponse.from(task);
    }

    //할 일 삭제
    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = findTask(id);

        taskRepository.delete(task); // 검색한 할 일 삭제
    }

    //ID로 할 일 검색
    private Task findTask(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 할 일을 찾을 수 없습니다"));
        // ID로 할 일을 검색하고 없으면 HTTP 404 오류와 메시지 반환
    }

    //입력값 검증
    private void validate(TaskRequest request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            // 제목이 null이거나 비어 있거나 공백만 있는 경우
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "할 일 제목을 입력해주세요");
        }
        if (request.getDeadline() == null) {
            // 마감일이 입력되지 않은 경우
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "마감일을 입력해주세요");
        }
        if (request.getImportance() == null || request.getImportance() < 1 || request.getImportance() > 5) {
            // 중요도가 입력되지 않았거나 1보다 작거나 5보다 큰 경우
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중요도는 1부터 5까지 입력해주세요.");
        }
    }

    @Override
    @Transactional
    public TaskResponse updateStatus(Long id, TaskStatusRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        task.updateStatus(request.completed());

        return new TaskResponse(task);
    }


}
