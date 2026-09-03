package com.dumboj.case1.travelchat;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;

/**
 * 封装带内存上下文存储的旅行会话 {@link ChatModel}
 *
 * @author : Dumbo
 */
public class TravelConversation {
    private final ChatModel chatModel;
    private final ChatMemory chatMemory;
    public TravelConversation(ChatModel chatModel) {
        this.chatModel = chatModel;
        this.chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        chatMemory.add(SystemMessage.from("你是一名旅游专家、旅游博主.回答简洁明练，不超过50字"));
    }
    public String ask(String inputText){
        chatMemory.add(UserMessage.from(inputText));
        ChatResponse chatResponse = chatModel.chat(chatMemory.messages());
        AiMessage aiMessage = chatResponse.aiMessage();
        chatMemory.add(aiMessage);
        return aiMessage.text();
    }
}
