package com.example.rabbitmqproject.rabbitmq.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("OutboxMessageProducer")
class OutboxMessageProducer implements MessageProducer {

  private final MessageRepository messageRepository;

  @Override
  public void send(String exchange, String routingKey, String message) {
    MessageEntity entity = MessageEntity.builder()
        .exchange(exchange)
        .routingKey(routingKey)
        .payload(message)
        .build();
    messageRepository.save(entity);
  }
}
