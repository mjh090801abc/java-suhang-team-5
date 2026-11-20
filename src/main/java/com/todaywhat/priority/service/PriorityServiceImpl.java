
package com.todaywhat.priority.service;

import com.todaywhat.priority.dto.PriorityResponse;
import com.todaywhat.task.domain.Task;
import com.todaywhat.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriorityServiceImpl implements PriorityService {

    private final TaskRepository taskRepository;

    @Override
    public List<PriorityResponse> getPriorities() {
        return taskRepository.findAll().stream()
                .filter(task -> !task.isCompleted())
                .map(task -> new PriorityResponse(task, calculateScore(task)))
                .sorted(Comparator
                        .comparingInt(PriorityResponse::getScore)
                        .thenComparing(PriorityResponse::getDeadline))
                .toList();
    }

    // 우선순위 점수 계산
    private int calculateScore(Task task) {

        long days = ChronoUnit.DAYS.between(
                LocalDate.now(), task.getDeadline()
        );

        // 마감일 점수
        int deadlineScore = (int) Math.min(3, Math.max(0, days));

        // 중요도 점수
        int importanceScore = 5 - task.getImportance();

        return deadlineScore + importanceScore;
    }


    @Override
    @Transactional(readOnly = true)
    public List<PriorityResponse> recommendTasks() {
        List<PriorityResponse> tasks = getPriorities();

        if (tasks.size() <= 3) {
            return tasks;
        }

        PriorityResponse third = tasks.get(2);

        return tasks.stream()
                .filter(task -> task.getScore() < third.getScore()
                        || (task.getScore() == third.getScore()
                        && !task.getDeadline().isAfter(third.getDeadline())))
                .toList();
    }



}
