package ru.webkonditer.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send/{message}")
    public void sendMessage(@PathVariable String message) {
        kafkaTemplate.send("topicName", message);
    }
}
