package com.dumboj.case1.model;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 封装 Springboot 集成使用的多类型LLM模型参与构建
 *
 * @author : Dumbo
 */
@Component
public class ModelClient {
    private final AiProperties aiProperties;
    private final OpenAiCompatibleChatModelFactory factory = new OpenAiCompatibleChatModelFactory();
    private final Map<ModelProvider, ChatModel> chatCache = new ConcurrentHashMap<>();
    private final Map<ModelProvider, StreamingChatModel> streamingCache = new ConcurrentHashMap<>();

    public ModelClient(AiProperties aiProperties) {
        this.aiProperties = aiProperties;
    }
    public ChatModel get(ModelProvider modelProvider) {
        return chatCache.computeIfAbsent(modelProvider, this::create);
    }

    public StreamingChatModel getStream(ModelProvider modelProvider) {
        return streamingCache.computeIfAbsent(modelProvider, this::createStreaming);
    }




    /**
     * 解析获取默认的 Provider
     * */
    public ModelProvider defaultProvider() {
        return ModelProvider.from(aiProperties.getDefaultProvider());
    }

    private ChatModel create(ModelProvider modelProvider) {
        String modelProviderName = modelProvider.name().toLowerCase(Locale.ROOT);
        AiProperties.ProviderProperties providerProperties = aiProperties.getProviders().get(modelProviderName);
        if (providerProperties == null) {
            throw new IllegalArgumentException("Unknown model provider " + modelProviderName);
        }
        return factory.create(modelProvider, providerProperties.toSettings());
    }

    private StreamingChatModel createStreaming(ModelProvider modelProvider) {
        String name = modelProvider.name().toLowerCase(Locale.ROOT);
        AiProperties.ProviderProperties providerProperties = aiProperties.getProviders().get(name);
        if (providerProperties == null) {
            throw new IllegalArgumentException("Unknown streaming model provider " + name);
        }
        return factory.createStreaming(modelProvider, providerProperties.toSettings());
    }

}
