
package com.todaywhat.task.dto.response;

import com.todaywhat.task.domain.Task;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TaskResponse {

    private final Long id;
    private final String title;
    private final LocalDate deadline;
    private final Integer importance;
    private final boolean completed;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.deadline = task.getDeadline();
        this.importance = task.getImportance();
        this.completed = task.isCompleted();
    }

    public static TaskResponse from(Task task) {
        return new TaskResponse(task);
    }
}
