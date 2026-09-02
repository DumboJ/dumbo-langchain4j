# Getting Started

### Reference Documentation

* [Langchain4j guide official wtih spring boot](https://docs.langchain4j.dev/tutorials/spring-boot-integration)

### Case1 多模型支持
- 新建 [AiProperties](src/main/java/com/dumboj/case1/model/AiProperties.java)、[ProviderSettings](src/main/java/com/dumboj/case1/model/ProviderSettings.java)、[ModelProvider](src/main/java/com/dumboj/case1/model/ModelProvider.java)、[OpenAiCompatibleChatModelFactory](src/main/java/com/dumboj/case1/model/OpenAiCompatibleChatModelFactory.java) 支持多模型调用
- [OpenAiChat](src/main/java/com/dumboj/case1/chat/OpenAiChat.java) 提供基础 ChatMessage 会话示例
- [UserSysMsgChat](src/main/java/com/dumboj/case1/chat/UserSysMsgChat.java) 提供系统及用户会话交互示例
- [SimpleChatMemory](src/main/java/com/dumboj/case1/chat/SimpleChatMemory.java) 提供带前后文会话信息的交互示例