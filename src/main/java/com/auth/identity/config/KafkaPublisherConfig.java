package com.auth.identity.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaPublisherConfig<K, V>
{
    private K key;
    private V value;

    public KafkaPublisherConfig(){}

    public KafkaPublisherConfig(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public ProducerFactory<K, V> producerFactory()
    {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        configs.put(ProducerConfig.ACKS_CONFIG, "all");
//        configs.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "txId");
//        configs.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
//        configs.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);
        return new DefaultKafkaProducerFactory<K, V>(configs);
    }

    public KafkaTemplate<K, V> kafkaTemplate()
    {
        return new KafkaTemplate<>(producerFactory());
    }

}
