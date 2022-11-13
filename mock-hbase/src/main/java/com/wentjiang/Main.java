package com.wentjiang;

public class Main {
    public static void main(String[] args) {
        MockHBase hBase = new MockHBase();
        String result = hBase.mockReadHBaseCache("test.json");
        System.out.println(hBase.toString());
        System.out.println("result: " + result);
    }
}