package ru.webkonditer.resiver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.webkonditer.resiver.model.MessageRecord;
import ru.webkonditer.resiver.service.MessageService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MainController {

    private  final MessageService messageService;

    public MainController(MessageService messageService) {
        this.messageService = messageService;
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
}
