package com.openschool.hw.service;

import com.openschool.hw.dto.TaskDto;
import com.openschool.hw.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Test
    void sendEmailNotification() {
        TaskDto task = new TaskDto();
        task.setId(1L);
        task.setStatus(TaskStatus.CREATED);
        task.setDescription("Task Description");
        task.setTitle("Task Title");
        task.setUserId(100L);

        ArgumentCaptor<SimpleMailMessage> message = ArgumentCaptor.forClass(SimpleMailMessage.class);

        JavaMailSender javaMailSender = Mockito.mock(JavaMailSender.class);
        doNothing().when(javaMailSender).send(message.capture());

        NotificationService notificationService = new NotificationService(javaMailSender);
        notificationService.sendEmailNotification(Collections.singletonList(task));

        String text = "Task with ID=" + task.getId() + " updated, new status is " + task.getStatus().getName() + System.lineSeparator();
        assertEquals(text, message.getValue().getText());

        verify(javaMailSender).send(message.getValue());
        verifyNoMoreInteractions(javaMailSender);
    }
}