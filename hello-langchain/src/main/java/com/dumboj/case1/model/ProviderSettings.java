package com.dumboj.case1.model;

import java.util.Objects;

/**
 * 从环境变量中获取模型连接配置,从 Environment Variables 中读取不上传 github
 *
 * @author : Dumbo
 */
public record ProviderSettings(String baseUrl,String modelName, String apiKey) {
    public ProviderSettings(String baseUrl, String modelName, String apiKey) {
        this.baseUrl = requireText(baseUrl, "baseUrl");
        this.modelName = requireText(modelName, "modelName");
        this.apiKey = requireText(apiKey, "apiKey");
    }

    /**
     * 从环境变量中读取 AI 模型相关配置
     * */
    public static ProviderSettings fromEnvironment() {
        return new ProviderSettings(System.getenv("AI_BASE_URL"),
                System.getenv("AI_MODEL_NAME"),
                System.getenv("AI_API_KEY"));
    }

    private String requireText(String value, String name) {
        if (Objects.requireNonNullElse(value, "").isBlank()) {
            throw new IllegalArgumentException("model config info " + name + " must not be blank");
        }
        return value.trim();
    }
}
