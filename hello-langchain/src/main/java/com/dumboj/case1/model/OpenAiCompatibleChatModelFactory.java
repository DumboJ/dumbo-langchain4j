package com.dumboj.case1.model;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

/**
 * OpenAiCompatibleChatModelFactory <br/>
 *
 * 以同一个 Langchain4j ChatModel 接口连接不同各 OpenAI-compatible 提供方,
 * 提供方差异由 baseUrl modelName apiKey 决定
 *
 * @author : Dumbo
 */
public class OpenAiCompatibleChatModelFactory {
    /**
     * 创建普通的 language chat model(一次Http请求获取完整模型响应结果)
     * */
    public ChatModel create(ModelProvider modelProvider, ProviderSettings providerSettings) {
        return OpenAiChatModel.builder()
                .baseUrl(providerSettings.baseUrl())
                .modelName(providerSettings.modelName())
                .apiKey(providerSettings.apiKey())
                .build();
    }

    /**
     * 创建流式 language chat model(一次Http 请求模型连续接收模型响应片段)
     * */
    public StreamingChatModel createStreaming(ModelProvider modelProvider, ProviderSettings providerSettings) {
        return OpenAiStreamingChatModel.builder()
                .baseUrl(providerSettings.baseUrl())
                .modelName(providerSettings.modelName())
                .apiKey(providerSettings.apiKey())
                .build();
    };
}
