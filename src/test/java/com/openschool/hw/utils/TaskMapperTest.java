package com.openschool.hw.utils;

import com.openschool.hw.dto.TaskDto;
import com.openschool.hw.model.Task;
import com.openschool.hw.model.TaskStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskMapperTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void mapToDto() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.CREATED);
        task.setDescription("Task Description");
        task.setTitle("Task Title");
        task.setUserId(100L);

        TaskDto taskDto = taskMapper.mapToDto(task);
        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getStatus(), taskDto.getStatus());
        assertEquals(task.getDescription(), taskDto.getDescription());
        assertEquals(task.getTitle(), taskDto.getTitle());
        assertEquals(task.getUserId(), taskDto.getUserId());
    }

    @Test
    void mapToTask() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setStatus(TaskStatus.CREATED);
        taskDto.setDescription("Task Description");
        taskDto.setTitle("Task Title");
        taskDto.setUserId(100L);

        Task task = taskMapper.mapToTask(taskDto);

        assertEquals(taskDto.getId(), task.getId());
        assertEquals(taskDto.getStatus(), task.getStatus());
        assertEquals(taskDto.getDescription(), task.getDescription());
        assertEquals(taskDto.getTitle(), task.getTitle());
        assertEquals(taskDto.getUserId(), task.getUserId());
    }
}