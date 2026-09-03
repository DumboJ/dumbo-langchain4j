package com.dumboj.case1.travelchat;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 旅行会话管理，根据会话id使用Map简单隔离
 *
 * @author : Dumbo
 */
public class TravelConversationManager {
    private final ChatModel chatModel;
    private Map<String, TravelConversation> conversations = new ConcurrentHashMap<>();

    public TravelConversationManager(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String ask(String conversationId, String request) {
        TravelConversation travelConversation = conversations.computeIfAbsent(conversationId, key -> new TravelConversation(chatModel));
        return travelConversation.ask(request);
    }
}
