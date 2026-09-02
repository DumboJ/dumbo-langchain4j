package com.dumboj.case1.chat;

import com.dumboj.case1.model.ModelProvider;
import com.dumboj.case1.model.OpenAiCompatibleChatModelFactory;
import com.dumboj.case1.model.ProviderSettings;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;

/**
 * Case1 : 最小 OpenAI-compatible 调用示例
 * <p/> 运行前需要在 IDEA 中配置 AI_API_KEY AI_MODEL_NAME AI_BASE_URL
 *
 * @author : Dumbo
 */
public class OpenAiChat {
    public static void main(String[] args) {
        String prompt = """
                你是一名 Java AI 开发导师
                请用三条要点解释 Langchain4j 中的 chatModel 职责
                每条不超过 30 个中文
                """;
        ProviderSettings providerSettings = ProviderSettings.fromEnvironment();
        ChatModel chatModel = new OpenAiCompatibleChatModelFactory().create(ModelProvider.CUSTOM, providerSettings);
        ChatResponse response = chatModel.chat(SystemMessage.from(prompt));
        System.out.println(response.aiMessage().text());
        /**
         * 1. 接收用户与系统消息序列
         * 2. 调用底层大模型生成回复
         * 3. 返回结构化响应并记录令牌
         * */
        System.out.println(response.aiMessage());
        /**
         * AiMessage {
         *              text = "1. 封装大模型调用，统一接口收发消息。
         *                      2. 管理对话生成参数，如温度、最大令牌。
         *                      3. 负责流式与非流式响应，返回模型输出。",
         *              thinking = null,
         *              toolExecutionRequests = [],
         *              attributes = {}
         *         }
         * */
    }
}
