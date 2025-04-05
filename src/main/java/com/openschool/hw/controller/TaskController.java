package com.openschool.hw.controller;

import com.openschool.hw.aspect.annotation.Logging;
import com.openschool.hw.dto.TaskDto;
import com.openschool.hw.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

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
        return taskService.save(task);
    }

    @Logging
    @PutMapping
    public TaskDto update(@RequestBody TaskDto task) {
        return taskService.save(task);
    }

    @Logging
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
