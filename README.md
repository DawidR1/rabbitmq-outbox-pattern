The project shows a simple example of the outbox pattern implementation. 
## Technology
* Java 11
* Spring boot
* Spring Debezium
* PostgreSQL
* RabbitMQ

## How to run app

In progress...

## Architecture
![outbox_arch2](https://user-images.githubusercontent.com/44610628/208757925-4f9642b7-3ea0-4f5d-8ae0-7adb1610e77e.png)

## How it works

### Message producer module

In the message producer module, the producer creates messages but does not send them directly to the message broker. Instead, it saves the new message in the database (in the "messages" table).
Thanks to this approach, we can be sure that a message will not be sent to the broker in the event of a transaction being rolled back in the database. 
We also ensure that a message will be created if the transaction is successful.

### Relay Service module

It is a module that reads messages from the database (table: messages) and delivers them to the RabbitMQ broker.
In this project, Spring Debezium was used, which captures data changes made in databases (in our case, changes in the messages table).
Debezium uses a database connector that reads the transaction log of the database and sends new changes to Debezium.

### Message consumer module

It is a simple module that consumes messages from RabbitMQ broker.
