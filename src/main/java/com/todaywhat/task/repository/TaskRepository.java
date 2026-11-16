package com.todaywhat.task.repository;

import com.todaywhat.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // 마감일 기준 오름차순 조회
    List<Task> findAllByOrderByDeadlineAsc();
    Optional<Task> findByTitle(String title);
}
 