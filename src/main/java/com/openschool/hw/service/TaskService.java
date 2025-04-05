package com.openschool.hw.service;

import com.openschool.hw.dto.TaskDto;
import com.openschool.hw.model.Task;
import com.openschool.hw.repository.TaskRepository;
import com.openschool.hw.utils.TaskMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public TaskService(TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

    public List<TaskDto> findAll() {
        return taskRepository.findAll().stream().map(taskMapper::mapToDto).collect(Collectors.toList());
    }

    public TaskDto findById(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        return task == null ? null : taskMapper.mapToDto(task);
    }

    public TaskDto save(TaskDto task) {
        Task savedTask = taskRepository.save(taskMapper.mapToTask(task));
        return taskMapper.mapToDto(savedTask);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
