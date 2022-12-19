package pl.dawid.messageproducer.rabbitmq.example;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ExampleQueueConf {

  @Bean("exampleExchange")
  public FanoutExchange exchange() {
    return new FanoutExchange("ex.example");
  }

  @Bean("exampleQueue")
  public Queue queue() {
    return QueueBuilder.durable("q.queue-example")
        .build();
  }

  @Bean
  public Binding binding(
      @Qualifier("exampleExchange") FanoutExchange directExchange,
      @Qualifier("exampleQueue") Queue queue) {
    return BindingBuilder
        .bind(queue)
        .to(directExchange);
  }
}
