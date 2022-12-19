package pl.dawid.relayservice.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DebeziumConf {

  @Bean
  public io.debezium.config.Configuration configuration() {
    return io.debezium.config.Configuration.create()
        .with("name", "outbox-postgresql-connector")
        .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
        .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
        .with("offset.storage.file.filename", "offsets.dat")
        .with("offset.flush.interval.ms", "10000")
        .with("database.hostname", "localhost")
        .with("database.port", "25433")
        .with("database.user", "user")
        .with("database.password", "password")
        .with("database.dbname", "projectdb")
        .with("table.include.list", "projectdb.messages")
        .with("include.schema.changes", "false")
        .with("topic.prefix", "box")
        .with("plugin.name", "pgoutput")
        .build();
  }
}
