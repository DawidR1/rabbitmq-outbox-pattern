package pl.dawid.messageproducer.rabbitmq.example;


import com.example.rabbitmqproject.rabbitmq.core.MessageProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class ExampleService {

  private final MessageProducer defaultMessageProducer;
  private final MessageProducer outboxMessageProducer;

  public ExampleService(
      @Qualifier("DefaultMessageProducer") MessageProducer defaultMessageProducer,
      @Qualifier("OutboxMessageProducer") MessageProducer outboxMessageProducer) {
    this.defaultMessageProducer = defaultMessageProducer;
    this.outboxMessageProducer = outboxMessageProducer;
  }

  @Transactional
  void sendUsingOutboxPattern(MessageDto dto) {
    outboxMessageProducer.send(dto.getExchange(), dto.getRoutingKey(), dto.getMessage());
  }

  @Transactional
  void sendAndThrowErrorUsingOutoboxPattern(MessageDto dto) {
    outboxMessageProducer.send(dto.getExchange(), dto.getRoutingKey(), dto.getMessage());
    throw new RuntimeException();
  }

  @Transactional
  void sendAndThrowError(MessageDto dto) {
    defaultMessageProducer.send(dto.getExchange(), dto.getRoutingKey(), dto.getMessage());
    throw new RuntimeException();
  }
}