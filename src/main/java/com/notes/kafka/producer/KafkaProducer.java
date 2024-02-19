package com.notes.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.mail.model.MailInfo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

  public static final String TOPIC = "mail";

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  @SneakyThrows
  public void produce(final MailInfo mailInfo) {
    String message = objectMapper.writeValueAsString(mailInfo);
    kafkaTemplate.send(TOPIC, message);
    log.info("Producer produced the message {}", message);
  }

}
