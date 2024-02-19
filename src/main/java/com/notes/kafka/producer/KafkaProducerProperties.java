package com.notes.kafka.producer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProducerProperties {
  private String bootstrapServers;
}
