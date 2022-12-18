package pl.dawid.messageproducer.rabbitmq.core;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("DefaultMessageProducer")
class DefaultMessageProducer implements MessageProducer {

  private final RabbitTemplate rabbitTemplate;

  @Override
  public void send(String exchange, String routingKey, String message) {
    rabbitTemplate.convertAndSend(exchange, routingKey, message);
  }
}
