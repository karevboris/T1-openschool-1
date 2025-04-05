package com.openschool.hw.utils;

import com.openschool.hw.dto.TaskDto;
import com.openschool.hw.model.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {
    public TaskDto mapToDto(Task task) {
        TaskDto taskDtoDto = new TaskDto();
        taskDtoDto.setId(task.getId());
        taskDtoDto.setTitle(task.getTitle());
        taskDtoDto.setDescription(task.getDescription());
        taskDtoDto.setUserId(task.getUserId());
        return taskDtoDto;
    }

    public Task mapToTask(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setUserId(taskDto.getUserId());
        return task;
    }
}
