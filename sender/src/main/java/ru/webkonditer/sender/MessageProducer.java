package ru.webkonditer.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final String[] topics = {"topic1-error", "topic2-error", "topic3-error", "topic4-error", "topic5-error"};
    private final Random random = new Random();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(fixedRate = 15000)
    public void sendMessage() {
        String topic = topics[random.nextInt(topics.length)];
        String key = UUID.randomUUID().toString();
        String message = generateJsonMessage();
        kafkaTemplate.send(topic, key, message);
    }

    private String generateJsonMessage() {
        try {
            Map<String, Object> messageContent = Map.of(
                    "id", UUID.randomUUID().toString(),
                    "timestamp", System.currentTimeMillis()
            );
            return objectMapper.writeValueAsString(messageContent);
        } catch (Exception e) {
            throw new RuntimeException("Error generating JSON message", e);
        }
    }
}
