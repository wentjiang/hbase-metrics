package com.wentjiang.mockhbase;

public class MockHBase {

    public String mockReadHBaseCache(String fileName){
        String originalContent = fileName + "original content";
        System.out.println("fileName: " + originalContent);
        return originalContent;
    }

}
