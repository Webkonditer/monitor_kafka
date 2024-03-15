package ru.webkonditer.resiver.service;

import org.springframework.stereotype.Service;
import ru.webkonditer.resiver.MessageRecord;
import ru.webkonditer.resiver.repository.MessageRecordRepository;

import java.util.List;

@Service
public class MessageService {


    private final MessageRecordRepository messageRecordRepository;

    public MessageService(MessageRecordRepository messageRecordRepository) {
        this.messageRecordRepository = messageRecordRepository;
    }

    // Получение топиков
    public List<String> getAllTopicNames() {
        return messageRecordRepository.findDistinctTopicByOrderByTopicAsc();
    }

    // Получение сообщений по топику
    public List<MessageRecord> getMessagesByTopic(String topic) {
        List<MessageRecord> records;
        if (topic == null || topic.isEmpty()) {
            records = messageRecordRepository.findAll();
        } else {
            records = messageRecordRepository.findByTopicOrderByReceivedAtDesc(topic);
        }
//        return records.stream().map(this::convertToDto).collect(Collectors.toList());
        return records;
    }

//    private MessageRecordDto convertToDto(MessageRecord record) {
//        // Здесь происходит преобразование сущности в DTO
//        return new MessageRecordDto(record.getId(), record.getReceivedAt(), record.getTopic(), record.getMessageBody(), record.isChecked(), record.getComment());
//    }

}
