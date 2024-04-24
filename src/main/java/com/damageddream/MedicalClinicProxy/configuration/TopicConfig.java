package com.damageddream.MedicalClinicProxy.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
    @Bean
    public NewTopic newsTopic() {
        return TopicBuilder.name("clinic").build();
    }
}
