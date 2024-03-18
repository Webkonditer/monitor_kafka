package ru.webkonditer.resiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.webkonditer.resiver.model.MessageRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRecordRepository extends JpaRepository<MessageRecord, Long> {
    List<MessageRecord> findByTopicOrderByReceivedAtDesc(String topic);

    @Query("SELECT DISTINCT m.topic FROM MessageRecord m ORDER BY m.topic ASC")
    List<String> findDistinctTopicByOrderByTopicAsc();

    List<MessageRecord> findByTopicAndReceivedAtBetweenOrderByReceivedAtDesc(String topic, LocalDateTime start, LocalDateTime end);

    List<MessageRecord> findByReceivedAtBetweenOrderByReceivedAtDesc(LocalDateTime start, LocalDateTime end);

}
