package com.openschool.hw.controller;

import com.openschool.hw.aspect.annotation.Logging;
import com.openschool.hw.dto.TaskDto;
import com.openschool.hw.kafka.KafkaTaskProducer;
import com.openschool.hw.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final KafkaTaskProducer kafkaTaskProducer;

    @Value("${task.kafka.topic.updating}")
    private String topic;

    @Logging
    @GetMapping
    public List<TaskDto> findAll() {
        return taskService.findAll();
    }

    @Logging
    @GetMapping("/{id}")
    public TaskDto findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @Logging
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TaskDto create(@RequestBody TaskDto task) {
        TaskDto taskDto = taskService.save(task);
        kafkaTaskProducer.sendTo(topic, taskDto);
        return taskDto;
    }

    @Logging
    @PutMapping
    public TaskDto update(@RequestBody TaskDto task) {
        TaskDto taskDto = taskService.save(task);
        kafkaTaskProducer.sendTo(topic, taskDto);
        return taskDto;
    }

    @Logging
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
