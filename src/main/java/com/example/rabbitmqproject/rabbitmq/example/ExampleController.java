package com.example.rabbitmqproject.rabbitmq.example;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("app")
class ExampleController {

  private final ExampleService exampleService;

  @PostMapping(path = "message-outbox", consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> post1(@RequestBody MessageDto dto) {
    exampleService.sendUsingOutboxPattern(dto);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .build();
  }

  @PostMapping(path = "message-outbox-with-error", consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> post2(@RequestBody MessageDto dto) {
    exampleService.sendAndThrowErrorUsingOutoboxPattern(dto);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .build();
  }

  @PostMapping(path = "message", consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> post3(@RequestBody MessageDto dto) {
    exampleService.sendAndThrowError(dto);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .build();
  }
}