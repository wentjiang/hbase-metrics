package com.wentjiang.agent;

public class DelegateTestClass {
    public static String test(String name) {
        return "delegate 1 args: " + name;
    }

    public static String test(String firstName, String secondName) {
        return "delegate 2 args:" + firstName + secondName;
    }
}
