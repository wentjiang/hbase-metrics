package com.wentjiang.mockhbase.model;

public class HRegionServer {

    public void test() {
        System.out.println("test");
    }

    public String run(String params) {
        System.out.println("Run in hregion server");
        return params;
    }

    public void testNotImplInSubClass() {
        System.out.println("this method not implement in subclass");
    }

}
