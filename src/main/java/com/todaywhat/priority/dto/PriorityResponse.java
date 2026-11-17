package com.todaywhat.priority.dto;

import com.todaywhat.task.domain.Task;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PriorityResponse {

    private final Long id;
    private final String title;
    private final LocalDate deadline;
    private final Integer importance;
    private final int score;

    public PriorityResponse(Task task, int score) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.deadline = task.getDeadline();
        this.importance = task.getImportance();
        this.score = score;

    }

}
