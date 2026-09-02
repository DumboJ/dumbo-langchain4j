package com.dumboj.case1.model;

import java.util.Locale;

/**
 * ModelProvider
 * 支持的 Open-AI compatible LLM 模型提供方,默认 Deepseek
 *
 * @author : Dumbo
 */
public enum ModelProvider {
    QWEN,
    DEEPSEEK,
    GROK,
    CUSTOM;

    public static ModelProvider from(String provider) {
        if (provider == null) {
            return ModelProvider.DEEPSEEK;
        }
        try {
            return ModelProvider.valueOf(provider.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown ModelProvider " + provider);
        }
    }
}
