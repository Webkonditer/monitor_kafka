package ru.webkonditer.resiver;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRecordRepository extends JpaRepository<MessageRecord, Long> {
}
