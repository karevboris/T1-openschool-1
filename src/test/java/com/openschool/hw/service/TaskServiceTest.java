package com.openschool.hw.service;

import com.openschool.hw.dto.TaskDto;
import com.openschool.hw.kafka.KafkaTaskProducer;
import com.openschool.hw.model.Task;
import com.openschool.hw.model.TaskStatus;
import com.openschool.hw.repository.TaskRepository;
import com.openschool.hw.utils.TaskMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        taskService = new TaskService(new TaskMapper(), taskRepository, mock(KafkaTaskProducer.class));
    }

    @Test
    void findAll() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.CREATED);
        task.setDescription("Task Description");
        task.setTitle("Task Title");
        task.setUserId(100L);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setStatus(TaskStatus.CREATED);
        task2.setDescription("Task Description 2");
        task2.setTitle("Task Title 2");
        task2.setUserId(200L);

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task, task2));

        List<TaskDto> tasks = taskService.findAll();
        TaskDto taskDto = tasks.stream().filter(t -> Objects.equals(t.getId(), task.getId())).findFirst().get();
        assertEquals(2, tasks.size());
        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getStatus(), taskDto.getStatus());
        assertEquals(task.getDescription(), taskDto.getDescription());
        assertEquals(task.getTitle(), taskDto.getTitle());
        assertEquals(task.getUserId(), taskDto.getUserId());

        verify(taskRepository).findAll();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void findAllWhenEmpty() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        List<TaskDto> tasks = taskService.findAll();
        assertEquals(0, tasks.size());

        verify(taskRepository).findAll();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void findById() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.CREATED);
        task.setDescription("Task Description");
        task.setTitle("Task Title");
        task.setUserId(100L);

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        TaskDto taskDto = taskService.findById(task.getId());
        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getStatus(), taskDto.getStatus());
        assertEquals(task.getDescription(), taskDto.getDescription());
        assertEquals(task.getTitle(), taskDto.getTitle());
        assertEquals(task.getUserId(), taskDto.getUserId());

        verify(taskRepository).findById(task.getId());
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void findByIdWhenNotFound() {
        when(taskRepository.findById(any())).thenReturn(Optional.empty());

        TaskDto taskDto = taskService.findById(any());
        assertNull(taskDto);

        verify(taskRepository).findById(any());
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void save() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.CREATED);
        task.setDescription("Task Description");
        task.setTitle("Task Title");
        task.setUserId(100L);

        when(taskRepository.save(task)).thenReturn(task);

        TaskDto taskDto = taskService.save(new TaskMapper().mapToDto(task));
        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getStatus(), taskDto.getStatus());
        assertEquals(task.getDescription(), taskDto.getDescription());
        assertEquals(task.getTitle(), taskDto.getTitle());
        assertEquals(task.getUserId(), taskDto.getUserId());

        verify(taskRepository).save(task);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void saveWhenError() {
        doThrow(new RuntimeException()).when(taskRepository).save(any());
        assertThrows(RuntimeException.class, () -> taskService.save(new TaskDto()));

        verify(taskRepository).save(any());
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void saveWhenNull() {
        assertThrows(NullPointerException.class, () -> taskService.save(null));

        verifyNoInteractions(taskRepository);
    }

    @Test
    void deleteById() {
        Task task = new Task();
        task.setId(1L);

        doNothing().when(taskRepository).deleteById(task.getId());

        taskService.deleteById(task.getId());

        verify(taskRepository).deleteById(task.getId());
        verifyNoMoreInteractions(taskRepository);
    }
}