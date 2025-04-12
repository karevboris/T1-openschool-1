package com.openschool.hw.repository;

import com.openschool.hw.starter.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
