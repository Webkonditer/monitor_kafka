package ru.webkonditer.resiver.service;

import org.springframework.stereotype.Service;
import ru.webkonditer.resiver.model.MessageRecord;
import ru.webkonditer.resiver.repository.MessageRecordRepository;

import java.time.LocalDateTime;
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

    // Получение сообщений по топику и интервалу дат
    public List<MessageRecord> getMessagesByTopicAndDateRange(String topic, LocalDateTime start, LocalDateTime end) {

        if(topic == null || topic.isEmpty()) {
            return messageRecordRepository.findByReceivedAtBetweenOrderByReceivedAtDesc(start, end);
        }
        return messageRecordRepository.findByTopicAndReceivedAtBetweenOrderByReceivedAtDesc(topic, start, end);
    }

    public MessageRecord getMessagesById(Long id) {
        return messageRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Сообщение не найдено"));
    }

    public void updateMessage(MessageRecord messageRecord) {
        var messageFromDb = getMessagesById(messageRecord.getId());
        messageFromDb.setMessageBody(messageRecord.getMessageBody());
        messageFromDb.setComment(messageRecord.getComment());
        messageFromDb.setChecked(messageRecord.getChecked());
        messageRecordRepository.save(messageFromDb);
    }

//    private MessageRecordDto convertToDto(MessageRecord record) {
//        // Здесь происходит преобразование сущности в DTO
//        return new MessageRecordDto(record.getId(), record.getReceivedAt(), record.getTopic(), record.getMessageBody(), record.isChecked(), record.getComment());
//    }

}
