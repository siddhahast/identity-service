package com.auth.identity.config;

import com.auth.identity.domain.AppUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfig
{
    @Bean
    public KafkaTemplate<String, AppUser> appUserKafkaTemplate()
    {
        KafkaPublisherConfig kafkaPublisherConfig = new KafkaPublisherConfig<String, AppUser>();
        return kafkaPublisherConfig.kafkaTemplate();
    }

    @Bean
    public KafkaListenerContainerFactory appUserKafkaListenerContainerFactory()
    {
        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig<String, AppUser>();
        return kafkaConsumerConfig.kafkaListenerContainerFactory();
    }

}
