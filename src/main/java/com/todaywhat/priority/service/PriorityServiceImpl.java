
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
    @Transactional(readOnly = true)
    // 전체 조회 -> 할 일 점수 계산 -> 점수 낮은 순으로 정렬 -> 정렬된 결과를 List로 변환
    public List<PriorityResponse> getPriorities() {
        return taskRepository.findAll().stream()
                .map(task -> new PriorityResponse(task, calculateScore(task)))
                .sorted(Comparator.comparingInt(PriorityResponse::getScore)
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
    // 우선순위 높은 목록 3개 반환
    public List<PriorityResponse> recommendTasks() {
        return getPriorities().stream()
                .limit(3)
                .toList();
    }
}
