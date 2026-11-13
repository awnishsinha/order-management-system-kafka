package com.awnish.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

    @Bean
    public NewTopic inventoryReserved(){
        return  TopicBuilder.name("inventory-reserved").build();
    }

    @Bean
    public NewTopic inventoryRejected(){
        return TopicBuilder.name("inventory-rejected").build();
    }

}
