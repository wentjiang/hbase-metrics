package com.wentjiang.mockhbase.model;

public class HMaster extends HRegionServer {

    @Override
    public String run(String params) {
        System.out.println("run in hmaster");
        return "HMaster" + params;
    }

    public Region getRegion(RegionInfo regionInfo, String regionName) {
        System.out.println(("regionName: " + regionName));
        return new Region(regionInfo.getRegionName());
    }
}
