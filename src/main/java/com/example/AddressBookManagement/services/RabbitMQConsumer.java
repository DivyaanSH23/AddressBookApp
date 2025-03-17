package com.example.AddressBookManagement.services;

import com.example.AddressBookManagement.DTO.RabbitMQMessageDTO;
import com.example.AddressBookManagement.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(RabbitMQMessageDTO message) {
        System.out.println("Received Message: " + message.getName() + " from " + message.getCity());
    }
}
