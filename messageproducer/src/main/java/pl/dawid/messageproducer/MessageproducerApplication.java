package pl.dawid.messageproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class MessageproducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MessageproducerApplication.class, args);
  }

}
