package ru.webkonditer.resiver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.webkonditer.resiver.model.MessageRecord;
import ru.webkonditer.resiver.service.KafkaListenerService;
import ru.webkonditer.resiver.service.MessageService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final MessageService messageService;
    private final KafkaListenerService kafkaListenerService;

    public MainController(MessageService messageService, KafkaListenerService kafkaListenerService) {
        this.messageService = messageService;
        this.kafkaListenerService = kafkaListenerService;
    }

    @GetMapping
    public String getMainPage(Model model) {

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();

        model.addAttribute("messages",
                messageService.getMessagesByTopicAndDateRange(null, startDate.atStartOfDay(), LocalDateTime.now()));
        model.addAttribute("topics", messageService.getAllTopicNames());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "main_page";
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<MessageRecord> getMessagesByTopic(
            @RequestParam(name = "topic", required = false) String topic,
            @RequestParam(name = "startDate", required = true) LocalDate startDate,
            @RequestParam(name = "endDate", required = true) LocalDate endDate
    ) {
        return messageService.getMessagesByTopicAndDateRange(topic, startDate.atStartOfDay(), endDate.atTime(23,59,59));
    }

    @GetMapping("/messages/{id}")
    @ResponseBody
    public MessageRecord getMessage(@PathVariable Long id) {
        return messageService.getMessagesById(id);
    }

    @GetMapping("/messages/kafkaConnection")
    @ResponseBody
    public ResponseEntity<?> getKafkaStatus() throws InterruptedException {

        if(kafkaListenerService.getKafkaStatus()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/messages/update")
    @ResponseBody
    public ResponseEntity<String> updateMessage(@RequestBody MessageRecord messageRecord) {
        try {
            messageService.updateMessage(messageRecord);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/messages/send-to-topic")
    public ResponseEntity<?> sendMessageToTopic(@RequestBody Map<String, String> messageData) {
        System.out.println("Топик: " + messageData.get("topic"));
        System.out.println("Сообщение: " + messageData.get("messageBody"));
        // Обработка сообщения
        try {
            kafkaListenerService.sendMessage(messageData.get("topic"), messageData.get("messageBody"));
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

}
