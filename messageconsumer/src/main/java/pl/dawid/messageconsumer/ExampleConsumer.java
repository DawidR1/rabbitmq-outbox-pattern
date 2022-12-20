package pl.dawid.messageconsumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
class ExampleConsumer {

  @RabbitListener(queues = "q.queue-example")
  void consume(String payload) {
    System.out.println(payload);
  }
}
