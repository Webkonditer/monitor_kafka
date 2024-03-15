package ru.webkonditer.resiver;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    @KafkaListener(topics = "topicName", groupId = "myGroup")
    public void listen(String message) {
        System.out.println("Received message in group myGroup: " + message);
    }
}
