package com.openschool.hw.controller;

import com.openschool.hw.aspect.annotation.Logging;
import com.openschool.hw.model.Task;
import com.openschool.hw.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Logging
    @GetMapping
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @Logging
    @GetMapping("/{id}")
    public Optional<Task> findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @Logging
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskService.save(task);
    }

    @Logging
    @PutMapping
    public Task update(@RequestBody Task task) {
        return taskService.save(task);
    }

    @Logging
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
