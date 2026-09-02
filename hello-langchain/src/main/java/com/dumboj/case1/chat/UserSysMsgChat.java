package com.dumboj.case1.chat;

import com.dumboj.case1.model.ModelProvider;
import com.dumboj.case1.model.OpenAiCompatibleChatModelFactory;
import com.dumboj.case1.model.ProviderSettings;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.Content;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;

import java.util.List;

/**
 * 用户输入与系统 message 内容 + token 用量统计
 *
 * @author : Dumbo
 */
public class UserSysMsgChat {
    public static void main(String[] args) {
        String sysMsg = """
                你是一名 Java AI 开发导师
                回答必须使用中文，简短列举
                """;
        String userMsg = "简述 Langchain4j 中 ChatModel SystemMessage UserMessage TokenUsage AiMessage 分别是什么";

        ProviderSettings providerSettings = ProviderSettings.fromEnvironment();
        ChatModel chatModel = new OpenAiCompatibleChatModelFactory().create(ModelProvider.CUSTOM, providerSettings);
        ChatResponse response = chatModel.chat(List.of(SystemMessage.from(sysMsg), UserMessage.from(userMsg)));

        AiMessage aiMessage = response.aiMessage();

        String text = aiMessage.text();
        TokenUsage tokenUsage = response.tokenUsage();

        System.out.printf("解答内容:%s\n  token用量:%s",text,tokenUsage);

        /**
         * 解答内容:
         * - **ChatModel**：核心接口，封装大语言模型调用，负责发送消息并返回模型响应，支持同步/异步、流式。
         * - **SystemMessage**：系统级指令消息，设定角色、行为规则或上下文约束，优先于用户输入。
         * - **UserMessage**：用户输入消息，包含问题或指令，可携带文本、图像等多媒体内容。
         * - **AiMessage**：模型生成的响应消息，包含回复内容，可能附带Token用量、拒绝原因等元数据。
         * - **TokenUsage**：Token用量统计，表示每次请求消耗的输入/输出Token数，用于成本计算和配额管理。
         *   token用量:
         *   OpenAiTokenUsage {
         *                  inputTokenCount = 119,
         *                  inputTokensDetails =
         *                  OpenAiTokenUsage.InputTokensDetails { cachedTokens = 0 },
         *                  outputTokenCount = 181,
         *                  outputTokensDetails = OpenAiTokenUsage.OutputTokensDetails {reasoningTokens = 42 },
         *                  totalTokenCount = 300
         *                  }
         * */
    }
}
