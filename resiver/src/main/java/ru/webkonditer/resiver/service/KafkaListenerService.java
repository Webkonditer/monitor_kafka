package ru.webkonditer.resiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import ru.webkonditer.resiver.model.MessageRecord;
import ru.webkonditer.resiver.repository.MessageRecordRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class KafkaListenerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessageRecordRepository repository;


    public KafkaListenerService(KafkaTemplate<String, String> kafkaTemplate, MessageRecordRepository repository) {
        this.kafkaTemplate = kafkaTemplate;
        this.repository = repository;
    }

    // Слушаем топики в кафке
    @KafkaListener(topics = {"topic1", "topic1-error", "topic2-error", "topic3-error", "topic4-error", "topic5-error"})
    public void listen(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        MessageRecord record = new MessageRecord();
        record.setReceivedAt(LocalDateTime.now());
        record.setTopic(topic);
        record.setMessageBody(message);
        // Set 'checked' and 'comment' as needed
        repository.save(record);
        System.out.printf("Saved message from topic %s: %s%n", topic, message);
    }

    // Отправляем сообщения в кафку
    public void sendMessage(String topic, String messageBody) {
        String key = UUID.randomUUID().toString();
        kafkaTemplate.send(topic, key, messageBody);
    }

}
