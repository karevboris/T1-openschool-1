package com.openschool.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openschool.hw.dto.TaskDto;
import com.openschool.hw.kafka.KafkaTaskConsumer;
import com.openschool.hw.model.Task;
import com.openschool.hw.model.TaskStatus;
import com.openschool.hw.repository.TaskRepository;
import com.openschool.hw.service.TaskService;
import com.openschool.hw.utils.TaskMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @Mock
    private KafkaTaskConsumer consumer;

    @Container
    static final ConfluentKafkaContainer kafka = new ConfluentKafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.8.0"));

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void findAll() throws Exception {
        Task task = new Task();
        task.setStatus(TaskStatus.CREATED);
        task.setDescription("Task Description");
        task.setTitle("Task Title");
        Task task2 = new Task();
        task2.setStatus(TaskStatus.CREATED);

        taskRepository.saveAll(Arrays.asList(task, task2));
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(task.getId()))
                .andExpect(jsonPath("$[0].description").value(task.getDescription()))
                .andExpect(jsonPath("$[0].title").value(task.getTitle()))
                .andExpect(jsonPath("$[0].status").value(task.getStatus().name()));
    }

    @Test
    void findById() throws Exception {
        Task task = new Task();
        task.setStatus(TaskStatus.CREATED);
        task.setDescription("Task Description");
        task.setTitle("Task Title");
        Task task2 = new Task();
        task2.setStatus(TaskStatus.CREATED);

        taskRepository.saveAll(Arrays.asList(task, task2));
        mockMvc.perform(get("/tasks/" + task.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.status").value(task.getStatus().name()));
    }

    @Test
    void create() throws Exception {
        TaskDto task = new TaskDto();
        task.setStatus(TaskStatus.CREATED);
        task.setDescription("Task Description");
        task.setTitle("Task Title");

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.status").value(task.getStatus().name()));
    }

    @Test
    void update() throws Exception {
        TaskDto taskDto = new TaskDto();
        taskDto.setStatus(TaskStatus.CREATED);
        taskDto.setDescription("Task Description");
        taskDto.setTitle("Task Title");

        Task task = taskRepository.save(taskMapper.mapToTask(taskDto));

        taskDto.setId(task.getId());
        taskDto.setTitle("New Test Title");

        mockMvc.perform(put("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.description").value(taskDto.getDescription()))
                .andExpect(jsonPath("$.title").value(taskDto.getTitle()))
                .andExpect(jsonPath("$.status").value(taskDto.getStatus().name()));
    }

    @Test
    void deleteById() throws Exception {
        TaskDto taskDto = new TaskDto();
        taskDto.setStatus(TaskStatus.CREATED);
        taskDto.setDescription("Task Description");
        taskDto.setTitle("Task Title");

        Task task = taskRepository.save(taskMapper.mapToTask(taskDto));

        mockMvc.perform(delete("/tasks/{id}", task.getId()))
                .andExpect(status().isNoContent());
    }
}
