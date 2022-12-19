package pl.dawid.relayservice.service;


import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
class RelayService {

  private final RabbitTemplate rabbitTemplate;

  void sendMessage(Map<String, Object> payloadQuery) {
    String routingKey = Optional.ofNullable(payloadQuery.get("routingKey"))
        .map(Object::toString)
        .orElse("");
    String exchange = payloadQuery.get("exchange").toString();
    Object payload = payloadQuery.get("payload");
    rabbitTemplate.convertAndSend(exchange, routingKey, payload);
  }
}