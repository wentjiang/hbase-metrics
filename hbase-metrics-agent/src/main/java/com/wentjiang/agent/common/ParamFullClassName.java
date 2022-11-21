package com.wentjiang.agent.common;

public enum ParamFullClassName {
    param_String("java.lang.String");

    private final String fullClassName;

    ParamFullClassName(String name) {
        this.fullClassName = name;
    }

    @Override
    public String toString() {
        return fullClassName;
    }
}
