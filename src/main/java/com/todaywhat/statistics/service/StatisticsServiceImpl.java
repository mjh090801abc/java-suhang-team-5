package com.todaywhat.statistics.service;

import com.todaywhat.statistics.dto.StatisticsResponse;
import com.todaywhat.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public StatisticsResponse getStatistics() {

        long total = taskRepository.count();
        long completed = taskRepository.countByCompletedTrue();
        long remaining = total - completed;

        double completionRate = 0;

        if (total > 0) {
            completionRate = (double) completed / total * 100;
        }

        return new StatisticsResponse(
                total, completed, remaining, completionRate
        );
    }
}