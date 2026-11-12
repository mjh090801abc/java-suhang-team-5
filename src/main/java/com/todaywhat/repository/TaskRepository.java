package com.todaywhat.repository;

import com.todaywhat.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // 마감일 기준 오름차순 조회
    List<Task> findAllByOrderByDeadlineAsc();
}
