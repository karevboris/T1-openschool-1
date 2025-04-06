package com.openschool.hw.service;

import com.openschool.hw.dto.TaskDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private static final String MY_EMAIL_ADDRESS = "karevboris333@gmail.com";
    private static final String EMAIL_SUBJECT = "New Task Status";

    private final JavaMailSender emailSender;

    public NotificationService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmailNotification(List<TaskDto> tasks) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(MY_EMAIL_ADDRESS);
        simpleMailMessage.setSubject(EMAIL_SUBJECT);
        StringBuilder text = new StringBuilder();
        for (TaskDto task : tasks) {
            text.append("Task with ID=").append(task.getId())
                    .append(" updated, new status is ").append(task.getStatus().getName())
                    .append(System.lineSeparator());
        }
        simpleMailMessage.setText(text.toString());
        emailSender.send(simpleMailMessage);
    }
}
