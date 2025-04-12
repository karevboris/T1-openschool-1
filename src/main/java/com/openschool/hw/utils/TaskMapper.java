package com.openschool.hw.utils;

import com.openschool.hw.starter.dto.TaskDto;
import com.openschool.hw.starter.model.Task;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    private final KafkaTemplate<String, TaskDto> task;

    public TaskMapper(KafkaTemplate<String, TaskDto> task) {
        this.task = task;
    }

    public TaskDto mapToDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setUserId(task.getUserId());
        taskDto.setStatus(task.getStatus());
        return taskDto;
    }

    public Task mapToTask(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setUserId(taskDto.getUserId());
        task.setStatus(taskDto.getStatus());
        return task;
    }
}
