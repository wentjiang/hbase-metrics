package com.wentjiang.example.testcase.waldurability;

import com.wentjiang.example.testcase.common.HBaseClient;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WALDurabilityCases {

    private final HBaseClient hBaseClient;

    private final List<String> familyNames = Arrays.asList("test_family_1", "test_family_2", "test_family_3");

    public WALDurabilityCases() {
        this.hBaseClient = new HBaseClient();
    }

    public void SKIP_WALTestCase() {

    }

    public void ASYNC_WALTestCase() {

    }

    public void SYNC_WALTestCase() {

    }

    public void USE_DEFAULTTestCase() {
        SYNC_WALTestCase();
    }

    private void testCase(String durability){
        String tableName = createTempTable(durability);
        writeRandomRecord(tableName);
        deleteRecords(tableName);
        deleteTable(tableName);
    }

    private String createTempTable(String durability) {
        String tableName = durability + "_" + new Random().nextInt();
        hBaseClient.createTable(tableName, familyNames);
        return tableName;
    }

    private void writeRandomRecord(String tableName){

    }

    private void deleteRecords(String tableName){

    }

    private void deleteTable(String tableName){

    }

}
