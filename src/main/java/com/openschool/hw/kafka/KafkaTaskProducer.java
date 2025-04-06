package com.openschool.hw.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTaskProducer {

    private final KafkaTemplate kafkaTemplate;

    public void send(Long id) {
        try {
            kafkaTemplate.sendDefault(UUID.randomUUID().toString(), id);
            kafkaTemplate.flush();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public void sendTo(String topic, Object data) {
        try {
            kafkaTemplate.send(topic, data);
            kafkaTemplate.flush();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
