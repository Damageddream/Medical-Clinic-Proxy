package com.damageddream.MedicalClinicProxy.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Slf4j
public class KafkaMessageListener {
    @KafkaListener(topics = "clinic", groupId = "medical-clinic-group")
    public void listenGroupFoo(String message) {
        log.info("Received Message in group: {} ", message);
    }
}