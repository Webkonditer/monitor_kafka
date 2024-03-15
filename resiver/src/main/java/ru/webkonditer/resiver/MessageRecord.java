package ru.webkonditer.resiver;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class MessageRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime receivedAt;
    private String topic;
    @Lob
    private String messageBody;
    private boolean checked = false;
    private String comment;

}
