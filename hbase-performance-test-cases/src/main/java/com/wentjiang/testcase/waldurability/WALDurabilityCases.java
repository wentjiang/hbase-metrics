package com.wentjiang.testcase.waldurability;

import com.wentjiang.testcase.common.HBaseClient;
import com.wentjiang.testcase.common.Timer;
import org.apache.hadoop.hbase.client.Durability;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WALDurabilityCases {

    private final HBaseClient hBaseClient;

    private final List<String> familyNames = Arrays.asList("test_family_0", "test_family_1", "test_family_2");

    private final int totalInsertNum = 10000;

    public WALDurabilityCases() {
        this.hBaseClient = new HBaseClient();
    }

    public void SKIP_WALTestCase() {
        testCase(Durability.SKIP_WAL);
    }

    public void ASYNC_WALTestCase() {
        testCase(Durability.ASYNC_WAL);
    }

    public void SYNC_WALTestCase() {
        testCase(Durability.SYNC_WAL);
    }

    public void FSYNC_WALTestCase() {
        testCase(Durability.FSYNC_WAL);
    }

    public void USE_DEFAULTTestCase() {
        SYNC_WALTestCase();
    }

    private void testCase(Durability durability) {
        System.out.println(durability.name() + " report:");
        Timer timer = new Timer();
        timer.start();
        String tableName = createTempTable(durability.name());
        timer.metricTime("createTable");
        writeRandomRecord(tableName, durability);
        timer.metricTime("writeRandomRecord");
        deleteRecords(tableName);
        timer.metricTime("deleteRecord");
        deleteTable(tableName);
        timer.metricTime("deleteTable");
        System.out.println(timer.getMetricReport());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String createTempTable(String durability) {
        String tableName = durability + "_" + new Random().nextInt();
        hBaseClient.createTable(tableName, familyNames);
        return tableName;
    }

    private void writeRandomRecord(String tableName, Durability durability) {
        for (int i = 0; i < totalInsertNum; i++) {
            String rowKey = "row_" + i;
            String familyName = "test_family_" + i % 3;
            String qualifier = "test_qualifier_" + i % 3;
            String value = "test_value_" + i;
            hBaseClient.putData(tableName, rowKey, familyName, qualifier, value, durability);
        }
    }

    private void deleteRecords(String tableName) {
        for (int i = 0; i < totalInsertNum; i++) {
            String rowKey = "row_" + i;
            hBaseClient.deleteRow(tableName, rowKey);
        }
    }

    private void deleteTable(String tableName) {
        hBaseClient.disableTable(tableName);
        hBaseClient.deleteTable(tableName);
    }

}
