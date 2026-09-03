package com.dumboj.case1.integration;

import com.dumboj.case1.model.ModelClient;
import com.dumboj.case1.model.ModelProvider;
import com.dumboj.case1.travelchat.TravelConversation;
import com.dumboj.case1.travelchat.TravelConversationManager;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TravelConversationService
 *
 * @author : Dumbo
 */
@Service
public class TravelConversationService {
    private final ModelClient modelClient;
    public TravelConversationService(ModelClient modelClient){
        this.modelClient = modelClient;
    }
    /**
     * 每个模型一个 Manager,相同模型再按照 requestId 隔离会话
     * */
    private final Map<ModelProvider, TravelConversationManager> conversationManagers = new ConcurrentHashMap<>();

    public String ask(TravelChatRequest travelChatRequest) {
        ModelProvider modelProvider;
        if (Objects.isNull(travelChatRequest.modelType())) {
            modelProvider = modelClient.defaultProvider();
        } else {
            modelProvider = ModelProvider.from(travelChatRequest.modelType());
        }
        //根据请求模型类型获取对应的模型旅行会话 Manager
        TravelConversationManager conversationManager = conversationManagers.computeIfAbsent(modelProvider,
                key -> new TravelConversationManager(modelClient.get(key)));
        return conversationManager.ask(travelChatRequest.requestId(), travelChatRequest.message());
    }
}
