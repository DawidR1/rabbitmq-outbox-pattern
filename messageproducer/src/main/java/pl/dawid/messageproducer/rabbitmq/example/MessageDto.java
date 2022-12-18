package pl.dawid.messageproducer.rabbitmq.example;

import lombok.Data;

@Data
public class MessageDto {

  private String message;
  private String exchange;
  private String routingKey;
}
