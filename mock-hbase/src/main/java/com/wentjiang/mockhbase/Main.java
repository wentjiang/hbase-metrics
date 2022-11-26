package com.wentjiang.mockhbase;

import com.wentjiang.mockhbase.model.HMaster;
import com.wentjiang.mockhbase.model.RegionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("start");
        HMaster hMaster = new HMaster();
        // System.out.println(hMaster.getRegion(new RegionInfo("region"), "test"));
        hMaster.getRegion(new RegionInfo("region"), "test");
        // hMaster.test();
        logger.info("end");
    }
}