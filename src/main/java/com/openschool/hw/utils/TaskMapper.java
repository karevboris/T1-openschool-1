package com.openschool.hw.utils;

import com.openschool.hw.dto.TaskDto;
import com.openschool.hw.model.Task;
import com.openschool.hw.model.TaskStatus;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    public TaskDto mapToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .userId(task.getUserId())
                .status(TaskStatus.valueOf(task.getStatus()))
                .build();
    }

    public Task mapToTask(TaskDto taskDto) {
        return Task.builder()
                .id(taskDto.getId())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .userId(taskDto.getUserId())
                .status(taskDto.getStatus().name())
                .build();
    }
}
