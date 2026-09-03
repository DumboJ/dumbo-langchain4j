package com.dumboj.case1.integration;

import dev.langchain4j.model.chat.request.ChatRequest;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对外访问层
 *
 * @author : Dumbo
 */
@RestController
@RequestMapping("/travel")
public class TravelChatController {
    @Resource
    private TravelConversationService travelConversationService;

    @PostMapping("/chat")
    public String chat(@Validated @RequestBody TravelChatRequest chatRequest) {
        return travelConversationService.ask(chatRequest);
    }
}
