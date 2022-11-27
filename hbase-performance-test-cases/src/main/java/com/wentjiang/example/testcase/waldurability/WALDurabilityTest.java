package com.wentjiang.example.testcase.waldurability;

public class WALDurabilityTest {

    public static void main(String[] args) {
        WALDurabilityCases cases = new WALDurabilityCases();
        cases.USE_DEFAULTTestCase();
        cases.FSYNC_WALTestCase();
        cases.SYNC_WALTestCase();
        cases.ASYNC_WALTestCase();
        cases.SKIP_WALTestCase();
    }

}
