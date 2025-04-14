package com.openschool.hw.utils;

import com.openschool.hw.dto.TaskDto;
import com.openschool.hw.model.Task;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    private final KafkaTemplate<String, TaskDto> task;

    public TaskMapper(KafkaTemplate<String, TaskDto> task) {
        this.task = task;
    }

    public TaskDto mapToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .userId(task.getUserId())
                .status(task.getStatus())
                .build();
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
