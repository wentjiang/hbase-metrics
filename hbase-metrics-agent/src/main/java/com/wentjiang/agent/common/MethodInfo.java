package com.wentjiang.agent.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MethodInfo {
    // 必须有methodName
    private String methodName;
    private Integer argumentLength;
    private List<String> paramNames;
}
