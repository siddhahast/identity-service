package com.auth.identity.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerConfig<K, V>
{

    private Class keyClass;
    private Class valueClass;

    public KafkaConsumerConfig(){}

    public KafkaConsumerConfig(Class keyClass, Class valueClass)
    {
        this.keyClass = keyClass;
        this.valueClass = valueClass;
    }

    public ConsumerFactory<K, V> consumerFactory()
    {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configs.put(JsonDeserializer.TRUSTED_PACKAGES, "com.auth.*");
//        DefaultKafkaConsumerFactory<K, V> objectVDefaultKafkaConsumerFactory = new DefaultKafkaConsumerFactory<K, V>(configs, (Deserializer<K>) new StringDeserializer(), new JsonDeserializer<>(valueClass));
        return new DefaultKafkaConsumerFactory<K, V>(configs);
    }

    public ConcurrentKafkaListenerContainerFactory<K, V> kafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<K, V> factory = new ConcurrentKafkaListenerContainerFactory<K, V>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
