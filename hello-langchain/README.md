# Getting Started

### Reference Documentation

* [Langchain4j guide official with spring boot](https://docs.langchain4j.dev/tutorials/spring-boot-integration)

### 环境 & 依赖

- 框架：Spring Boot 4.1.1 + Java 17
- 核心库：LangChain4j 1.19.0
- 依赖引入方式：使用 `langchain4j-bom` 管理版本，当前未使用 LangChain4j 的 Spring Boot starter，而是直接基于 `langchain4j-open-ai` 手动构建兼容模型工厂。
- 配置：`src/main/resources/application.yaml`
  - 前缀 `app.ai`
  - 多模型环境变量占位（`DEEPSEEK_*`、`QWEN_*`、`GROK_*`）
  - 运行前请在 IDEA 的 `Environment variables` 里填入对应 key，或使用 `application-local.yaml` 本地引入。

### 学习内容更新记录

#### Case1 多模型支持
- 新建 [AiProperties](src/main/java/com/dumboj/case1/model/AiProperties.java)、[ProviderSettings](src/main/java/com/dumboj/case1/model/ProviderSettings.java)、[ModelProvider](src/main/java/com/dumboj/case1/model/ModelProvider.java)、[OpenAiCompatibleChatModelFactory](src/main/java/com/dumboj/case1/model/OpenAiCompatibleChatModelFactory.java) 支持多模型调用
- [OpenAiChat](src/main/java/com/dumboj/case1/chat/OpenAiChat.java) 提供基础 ChatMessage 会话示例
- [UserSysMsgChat](src/main/java/com/dumboj/case1/chat/UserSysMsgChat.java) 提供系统及用户会话交互示例
- [SimpleChatMemory](src/main/java/com/dumboj/case1/chat/SimpleChatMemory.java) 提供带前后文会话信息的交互示例

#### Case2 多会话隔离（旅行专家）
- [TravelConversation](src/main/java/com/dumboj/case1/travelchat/TravelConversation.java) 封装带 `ChatMemory` 的独立会话，内置 SystemMessage 与 MessageWindow 上下文窗口
- [TravelConversationManager](src/main/java/com/dumboj/case1/travelchat/TravelConversationManager.java) 用 `ConcurrentHashMap` 按会话 ID 隔离多轮对话
- [TravelChatInvoke](src/main/java/com/dumboj/case1/travelchat/TravelChatInvoke.java) 入口：演示同一 `task-001` 上下文连续提问，以及 `task-002` 无法访问 `task-001` 上下文的效果

#### Case3 流式响应
- [OpenAiStreamingChat](src/main/java/com/dumboj/case1/streamingchat/OpenAiStreamingChat.java) 演示 `StreamingChatModel` + `StreamingChatResponseHandler` 的逐字返回、完整响应与异常回调

#### Case4 Spring Boot 集成与接口测试
- [ModelClient](src/main/java/com/dumboj/case1/model/ModelClient.java) 封装 `AiProperties` + `OpenAiCompatibleChatModelFactory`，按模型缓存 `ChatModel` / `StreamingChatModel`
- [TravelConversationService](src/main/java/com/dumboj/case1/integration/TravelConversationService.java) 按模型隔离会话管理器，按 `requestId` 隔离多轮对话
- [TravelChatController](src/main/java/com/dumboj/case1/integration/TravelChatController.java) 对外 REST 接口 `POST /travel/chat`
- [TravelChatRequest](src/main/java/com/dumboj/case1/integration/TravelChatRequest.java) 请求参数：`requestId` / `message` / `modelType`
- HTTP 测试脚本（位于 `src/main/java/com/dumboj/case1/integration/http`）：
  - [travel-controller.http](src/main/java/com/dumboj/case1/integration/http/travel-controller.http) —— 用 IDEA HTTP Client 测本地接口
  - [llm-examples.http](src/main/java/com/dumboj/case1/integration/http/llm-examples.http) —— 直接测三方模型 API 连通性
  - [http-client.env.json](src/main/java/com/dumboj/case1/integration/http/http-client.env.json) —— IDEA HTTP Client 环境变量管理

### 本地配置方式

**方式 A：IDEA Environment variables**

```text
DEEPSEEK_API_KEY=sk-xxx;QWEN_API_KEY=sk-xxx;GROK_API_KEY=sk-xxx
```

**方式 B：本地 YAML 文件引入**

1. 项目根目录创建 `application-local.yaml`（不要提交）：

```yaml
app:
  ai:
    providers:
      deepseek:
        api-key: sk-xxx
      qwen:
        api-key: sk-xxx
      grok:
        api-key: sk-xxx
```

2. 在 IDEA 的 `Environment variables` 中填入：

```text
SPRING_CONFIG_IMPORT=optional:file:./application-local.yaml
```

3. 确保 `.gitignore` 排除：

```text
.env
application-local.yaml
```
