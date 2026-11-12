package com.todaywhat.dto.response;
import com.todaywhat.domain.Task;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class TaskResponse {

    private Long id;
    private String title;
    private LocalDate deadline;
    private Integer importance;

    //객체를 생성할 때 필요한 데이터를 전달받는 생성자
    // 전달받은 데이터를 각 필드에 저장함
    public TaskResponse(
            Long id,
            String title,
            LocalDate deadline,
            Integer importance
    ) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
        this.importance = importance;
    }

    public static TaskResponse from(Task task) {
        // Task 객체에 저장된 데이터를 가져와
        // TaskResponse 생성자에 전달하고 새로운 DTO 객체를 반환함
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDeadline(),
                task.getImportance()
        );
    }

}