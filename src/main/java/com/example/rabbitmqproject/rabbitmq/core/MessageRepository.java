package com.example.rabbitmqproject.rabbitmq.core;


import org.springframework.data.jpa.repository.JpaRepository;

interface MessageRepository extends JpaRepository<MessageEntity, Long> {

}
