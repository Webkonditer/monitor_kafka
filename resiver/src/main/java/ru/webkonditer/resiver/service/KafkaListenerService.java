package ru.webkonditer.resiver.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import ru.webkonditer.resiver.MessageRecord;
import ru.webkonditer.resiver.repository.MessageRecordRepository;

import java.time.LocalDateTime;

@Service
public class KafkaListenerService {

    private final MessageRecordRepository repository;

    public KafkaListenerService(MessageRecordRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = {"topic1", "topic2", "topic3", "topic4", "topic5"})
    public void listen(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        MessageRecord record = new MessageRecord();
        record.setReceivedAt(LocalDateTime.now());
        record.setTopic(topic);
        record.setMessageBody(message);
        // Set 'checked' and 'comment' as needed
        repository.save(record);
        System.out.printf("Saved message from topic %s: %s%n", topic, message);
    }

    @KafkaListener(topics = "topicName", groupId = "myGroup")
    public void listen(String message) {
        System.out.println("Received message in group myGroup: " + message);
    }


}
