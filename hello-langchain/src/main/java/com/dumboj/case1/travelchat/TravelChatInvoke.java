package com.dumboj.case1.travelchat;

import com.dumboj.case1.model.ModelProvider;
import com.dumboj.case1.model.OpenAiCompatibleChatModelFactory;
import com.dumboj.case1.model.ProviderSettings;
import dev.langchain4j.model.chat.ChatModel;

/**
 * 隔离的旅行会话 调用示例
 *
 * @author : Dumbo
 */
public class TravelChatInvoke {
    public static void main(String[] args) {
        ChatModel chatModel = new OpenAiCompatibleChatModelFactory().create(ModelProvider.DEEPSEEK, ProviderSettings.fromEnvironment());
        TravelConversationManager conversationManager = new TravelConversationManager(chatModel);

        //会话1
        String task001Resp1 = conversationManager.ask("task-001", "北京哪里好玩？");
        String task001Resp2 = conversationManager.ask("task-001", "三个人从云南来预算300够吗？");

        System.out.println("task001Resp1 = " + task001Resp1);
        /**
         * 故宫、长城、颐和园必去；南锣鼓巷、什刹海体验胡同；国家博物馆长知识。
         * */
        System.out.println("task001Resp2 = " + task001Resp2);
        /**
         * 300是总预算？远不够。若是三人每日人均300，勉强覆盖门票+吃饭，住宿另计。建议先算交通。
         * */

        //会话2- 并不能关联上下文内容信息
        String task002Resp = conversationManager.ask("task-002", "前面问题有什么解决方式吗？");
        System.out.println("task002Resp = " + task002Resp);
        /**
         * 请问您指的是哪个具体问题呢？请告诉我，我作为旅游专家为您提供简洁实用的解决方案。
         * */
    }
}
