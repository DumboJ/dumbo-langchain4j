package com.dumboj.case1.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 多 LLM 环境变量
 *
 * @author : Dumbo
 */
@ConfigurationProperties(prefix= "app.ai")
public class AiProperties {
    private String defaultProvider = "deepseek";
    private int maxRequestPerMinute = 20;
    private Map<String, ProviderProperties> providers = new LinkedHashMap<>();

    public String getDefaultProvider() {
        return defaultProvider;
    }
    public void setDefaultProvider(String defaultProvider) {
        this.defaultProvider = defaultProvider;
    }

    public int getMaxRequestPerMinute() {
        return maxRequestPerMinute;
    }

    public void setMaxRequestPerMinute(int maxRequestPerMinute) {
        this.maxRequestPerMinute = maxRequestPerMinute;
    }

    public Map<String, ProviderProperties> getProviders() {
        return providers;
    }

    public void setProviders(Map<String, ProviderProperties> providers) {
        this.providers = providers;
    }

    public record ProviderProperties(String baseUrl, String modelName, String apiKey) {
        public ProviderSettings toSettings(){
            return new ProviderSettings(baseUrl,modelName,apiKey);
        }
    }
}
