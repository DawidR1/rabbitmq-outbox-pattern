package pl.dawid.messageproducer.rabbitmq.core;

public interface MessageProducer {

  void send(String exchange, String routingKey, String message);

}
