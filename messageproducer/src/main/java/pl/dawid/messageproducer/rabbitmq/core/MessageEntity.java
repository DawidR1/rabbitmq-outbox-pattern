package pl.dawid.messageproducer.rabbitmq.core;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "messages")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class MessageEntity {

  @Id
  @SequenceGenerator(name = "messages_s", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messages_s")
  private Long id;

  @Column(nullable = false)
  private String exchange;
  private String routingKey;
  private String headers;
  @Column(nullable = false)
  private String payload;
}
