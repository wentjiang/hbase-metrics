package com.wentjiang.mockhbase.model;

public class MockHBase {

    public String mockReadHBaseCache(String fileName) {
        String content = "fileName: " + fileName;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(content);
        return content;

    }

}
