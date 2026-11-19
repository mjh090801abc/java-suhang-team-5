package com.todaywhat.task.repository;

import com.todaywhat.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitle(String title);

    long countByCompletedTrue();
}