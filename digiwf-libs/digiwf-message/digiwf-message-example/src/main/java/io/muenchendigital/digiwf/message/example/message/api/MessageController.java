package io.muenchendigital.digiwf.message.example.message.api;


import io.muenchendigital.digiwf.message.example.message.dto.Message;
import io.muenchendigital.digiwf.message.example.message.dto.MessageSuccess;
import io.muenchendigital.digiwf.message.example.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping()
    public MessageSuccess sendMessage(@RequestBody final Message msg) {
        return this.messageService.sendMessage(msg);
    }

}
