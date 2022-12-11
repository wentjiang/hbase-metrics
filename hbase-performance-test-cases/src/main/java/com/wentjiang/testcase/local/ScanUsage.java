package com.wentjiang.testcase.local;

import com.wentjiang.testcase.common.HBaseClient;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

import java.util.Collections;
import java.util.List;

public class ScanUsage {

    private static final HBaseClient client = new HBaseClient();

    private static final String tableName = "test_scan";

    private static final String familyName = "test_family_name";

    private static final String field1 = "test_field1";

    private static final String field2 = "test_field2";

    private static final String field3 = "test_field3";

    /**
     * 创建表 添加数据 测试scan的用法
     */
    public static void main(String[] args) {
        client.createTable(tableName, Collections.singletonList(familyName));
        // putBatchData();
        // getData(tableName,"row_key:field1:1");
        scanData(tableName, "");
    }

    private static void scanData(String tableName, String keyPrefix) {
        Scan scan = new Scan();
        scan.setRowPrefixFilter("row_key:field1:".getBytes());
        ResultScanner scanner = client.scanData(tableName, scan);
        scanner.forEach(result -> {
            result.listCells().forEach(cell -> {
                System.out.println(CellUtil.getCellKeyAsString(cell));
            });
        });
    }

    private static void getData(String tableName, String rowKey) {
        List<Cell> cells = client.getData(tableName, rowKey);
        for (Cell cell : cells) {
            System.out.println(CellUtil.getCellKeyAsString(cell));
        }
    }

    private static void putBatchData() {
        for (int i = 0; i < 100; i++) {
            client.putData(tableName, "row_key:field1:" + i, familyName, field1, String.valueOf(i),
                    Durability.SYNC_WAL);
        }

        for (int i = 0; i < 100; i++) {
            client.putData(tableName, "row_key:field2:" + i, familyName, field2, String.valueOf(i),
                    Durability.SYNC_WAL);
        }

        for (int i = 0; i < 100; i++) {
            client.putData(tableName, "row_key:field3:" + i, familyName, field3, String.valueOf(i),
                    Durability.SYNC_WAL);
        }
    }

}
