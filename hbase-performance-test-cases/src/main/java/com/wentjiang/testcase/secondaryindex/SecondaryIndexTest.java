package com.wentjiang.testcase.secondaryindex;

import com.wentjiang.testcase.common.Timer;

public class SecondaryIndexTest {
    public static void main(String[] args) throws InterruptedException {
        Timer timer1 = new SecondaryIndexOption1(SecondaryIndexConfig.addDataCount, SecondaryIndexConfig.scanDataCount)
                .executeTest();
        Thread.sleep(5000);
        Timer timer2 = new SecondaryIndexOption2(SecondaryIndexConfig.addDataCount, SecondaryIndexConfig.scanDataCount)
                .executeTest();
        Thread.sleep(5000);
        Timer timer3 = new WithOutSecondaryIndex(SecondaryIndexConfig.addDataCount, SecondaryIndexConfig.scanDataCount)
                .executeTest();
        System.out.println(timer1.getMetricReport());
        System.out.println(timer2.getMetricReport());
        System.out.println(timer3.getMetricReport());
    }
}
