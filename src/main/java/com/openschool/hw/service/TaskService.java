package com.openschool.hw.service;

import com.openschool.hw.dto.TaskDto;
import com.openschool.hw.kafka.KafkaTaskProducer;
import com.openschool.hw.model.Task;
import com.openschool.hw.repository.TaskRepository;
import com.openschool.hw.utils.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final KafkaTaskProducer kafkaTaskProducer;

    @Value("${task.kafka.topic.updating}")
    private String topic;

    public List<TaskDto> findAll() {
        return taskRepository.findAll().stream().map(taskMapper::mapToDto).collect(Collectors.toList());
    }

    public TaskDto findById(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        return task == null ? null : taskMapper.mapToDto(task);
    }

    public TaskDto save(TaskDto task) {
        Task savedTask = taskRepository.save(taskMapper.mapToTask(task));

        kafkaTaskProducer.sendTo(topic, savedTask);
        return taskMapper.mapToDto(savedTask);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
