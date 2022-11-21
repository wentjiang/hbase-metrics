package com.wentjiang.agent.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MethodInfo {
    private String methodName;
    private Integer argLength;
    private List<ParamFullClassName> paramNames;
}
