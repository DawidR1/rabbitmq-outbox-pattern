package pl.dawid.relayservice.service;


import static io.debezium.data.Envelope.FieldName.AFTER;
import static io.debezium.data.Envelope.FieldName.OPERATION;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toMap;

import io.debezium.config.Configuration;
import io.debezium.data.Envelope.Operation;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class DebeziumRunner {

  private DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;
  private final Executor executor = Executors.newSingleThreadExecutor();
  private final RelayService relayService;

  public DebeziumRunner(Configuration customerConnectorConfiguration, RelayService relayService) {

    this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
        .using(customerConnectorConfiguration.asProperties())
        .notifying(handleEvent())
        .build();

    this.relayService = relayService;
  }

  @PostConstruct
  private void start() {
    this.executor.execute(debeziumEngine);
  }

  @PreDestroy
  private void stop() throws IOException {
    if (this.debeziumEngine != null) {
      this.debeziumEngine.close();
    }
  }

  private Consumer<RecordChangeEvent<SourceRecord>> handleEvent() {
    return changeEvent -> {
      of(changeEvent.record())
          .map(ConnectRecord::value)
          .map(Struct.class::cast)
          .filter(this::checkIfCreateOperation)
          .ifPresent(this::sendData);
    };
  }

  private boolean checkIfCreateOperation(Struct struct) {
    String code = (String) struct.get(OPERATION);
    Operation operation = Operation.forCode(code);
    return operation == Operation.CREATE;
  }

  private void sendData(Struct record) {
    Struct struct = (Struct) record.get(AFTER);
    Map<String, Object> payload = getValueByColumnName(struct);
    try {
      relayService.sendMessage(payload);
    } catch (Exception e) {
      log.error("Exception debezium", e);
    }
  }

  private Map<String, Object> getValueByColumnName(Struct struct) {
    return struct.schema()
        .fields()
        .stream()
        .map(Field::name)
        .filter(fieldName -> struct.get(fieldName) != null)
        .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
        .collect(toMap(Pair::getLeft, Pair::getRight));
  }
}
