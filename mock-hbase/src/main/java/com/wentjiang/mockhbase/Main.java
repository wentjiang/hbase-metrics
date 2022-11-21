package com.wentjiang.mockhbase;

import com.wentjiang.mockhbase.model.HMaster;
import com.wentjiang.mockhbase.model.RegionInfo;

public class Main {
    public static void main(String[] args) {
        HMaster hMaster = new HMaster();
//        System.out.println(hMaster.getRegion(new RegionInfo("region"),"test"));
        System.out.println(hMaster.run("test"));
    }
}