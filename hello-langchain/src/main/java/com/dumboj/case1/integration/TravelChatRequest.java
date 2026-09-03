package com.dumboj.case1.integration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 前端请求封装
 *
 * @author : Dumbo
 */
public record TravelChatRequest(@NotBlank @Size(max = 24) String requestId,
                                @NotBlank @Size(max = 1000)String message,
                                @NotBlank @Size(max = 60)String modelType) {

}
