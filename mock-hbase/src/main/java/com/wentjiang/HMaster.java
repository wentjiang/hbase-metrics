package com.wentjiang;

public class HMaster extends HRegionServer{

    @Override
    public String run(String params) {
        System.out.println("run in hmaster");
        return "HMaster" + params;
    }
}
