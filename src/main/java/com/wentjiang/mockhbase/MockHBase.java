package com.wentjiang.mockhbase;

public class MockHBase {

    public String mockReadHBaseCache(String fileName) {
        String originalContent = fileName + "original content";
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return originalContent;
    }

}
