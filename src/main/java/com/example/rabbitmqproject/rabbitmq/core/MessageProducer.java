package com.example.rabbitmqproject.rabbitmq.core;

public interface MessageProducer {

  void send(String exchange, String routingKey, String message);

}
