package ru.webkonditer.resiver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.webkonditer.resiver.MessageRecord;
import ru.webkonditer.resiver.service.MessageService;

import java.util.List;

@Controller
public class MainController {

    private  final MessageService messageService;

    public MainController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String getMainPage(Model model) {
        model.addAttribute("messages", messageService.getMessagesByTopic(null));
        model.addAttribute("topics", messageService.getAllTopicNames());
        return "main_page";
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<MessageRecord> getMessagesByTopic(@RequestParam(name = "topic", required = false) String topic) {
        return messageService.getMessagesByTopic(topic);
    }
}
