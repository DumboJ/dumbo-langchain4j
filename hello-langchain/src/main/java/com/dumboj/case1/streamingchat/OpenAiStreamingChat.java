package com.dumboj.case1.streamingchat;

import com.dumboj.case1.model.ModelProvider;
import com.dumboj.case1.model.OpenAiCompatibleChatModelFactory;
import com.dumboj.case1.model.ProviderSettings;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.PartialResponse;
import dev.langchain4j.model.chat.response.PartialResponseContext;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;

import java.util.concurrent.TimeUnit;

/**
 * StreamingChatModel 示例
 *
 * @author : Dumbo
 */
public class OpenAiStreamingChat {
    public static void main(String[] args) {
        String prompt = """
                你是一名 Java AI 开发导师
                请用三条要点解释 Langchain4j 中的 StreamChatModel 职责
                每条不超过 30 个中文
                """;
        ProviderSettings providerSettings = ProviderSettings.fromEnvironment();
        StreamingChatModel streaming = new OpenAiCompatibleChatModelFactory().createStreaming(ModelProvider.CUSTOM, providerSettings);
        streaming.chat(prompt, new StreamingChatResponseHandler() {
            //全量响应内容
            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                System.out.println("completeResponse: "+completeResponse.aiMessage());
                /** AiMessage { text =
                 *  "1. 接收用户消息，流式获取模型回复片段。
                 *   2. 回调处理增量 Token，实时暴露内容。
                 *   3. 管理流生命周期，支持中断与异常。",
                 *   thinking = null, toolExecutionRequests = [], attributes = {} }
                 */
            }

            // 部分内容响应
            @Override
            public void onPartialResponse(PartialResponse partialResponse, PartialResponseContext context) {
                System.out.println("partialResponse: "+partialResponse.text());
                System.out.println("StreamingHandle is Cancel: "+context.streamingHandle().isCancelled());
            }

            //发生错误时
            @Override
            public void onError(Throwable error) {
                System.err.println("error: "+error.getMessage());
            }
        });

        //休眠十秒
        try {
            TimeUnit.SECONDS.sleep(10L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
