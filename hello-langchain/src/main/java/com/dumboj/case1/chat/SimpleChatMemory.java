package com.dumboj.case1.chat;

import com.dumboj.case1.model.ModelProvider;
import com.dumboj.case1.model.OpenAiCompatibleChatModelFactory;
import com.dumboj.case1.model.ProviderSettings;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;

import java.util.List;

/**
 * 普通带会话前后文的内容样例 {@link InMemoryChatMemoryStore}
 *
 * @author : Dumbo
 */
public class SimpleChatMemory {
    public static void main(String[] args) {
        ProviderSettings providerSettings = ProviderSettings.fromEnvironment();
        ChatModel chatModel = new OpenAiCompatibleChatModelFactory().create(ModelProvider.CUSTOM, providerSettings);

        //1. 创建内存消息(指定最大保存消息数量，底层内存 ConcurrentHashMap 保存)
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(6);

        //2. 创建系统 + 用户会话消息，并添加至 ChatMemory
        String sMsg = "你是旅行专家、知名旅游博主";
        SystemMessage sysMsg = SystemMessage.from(sMsg);
        String uMsg = "我想去北京玩两天";
        UserMessage usrMsg = UserMessage.from(uMsg);

        chatMemory.add(sysMsg,usrMsg);

        //3. 首次大语言模型提问
        ChatResponse firstResp = chatModel.chat(chatMemory.messages());
        System.out.printf("首次会话响应:\n %s ",firstResp.aiMessage().text());

        //4. 保存首次会话响应内容至内存消息,并继续提问
        UserMessage secondUsrMsg = UserMessage.from("3000块预算够吗？");
        chatMemory.add(firstResp.aiMessage(),secondUsrMsg);

        //5. 第二次大预言模型提问
        ChatResponse secondResp = chatModel.chat(chatMemory.messages());
        System.out.printf("\n 第二次会话响应:\n %s ",secondResp.aiMessage().text());

    }
}
