package com.myshop.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic stockCheckTopic(){
        return TopicBuilder
                .name("Pstock-check-events")
                .partitions(10)
                .build();
    }
}