package com.wentjiang.testcase.local;

import com.wentjiang.testcase.common.HBaseClient;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import javax.annotation.processing.Filer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class CommonUsage {
    private static final String tableName = "test_table";
    private static final String rowKey = "test_rowKey1";
    private static final String familyName = "test_field";
    private static final String qualifier = "test_qualifierName";
    private static final String value = "test_value1";

    private static final HBaseClient client = new HBaseClient();

    public static void main(String[] args) {
        client.createTable(tableName, Collections.singletonList(familyName));
        printTableNames();
        putData(tableName, rowKey, familyName, qualifier, value);
        getData(tableName, rowKey);
    }

    public static void printTableNames() {
        for (String tableName : client.getTableList()) {
            System.out.println(tableName);
        }
    }

    public static void putData(String tableName, String rowKey, String familyName, String qualifier, String value) {
        client.putData(tableName, rowKey, familyName, qualifier, value, Durability.SYNC_WAL);
    }

    public static void getData(String tableName, String rowKey) {
        List<Cell> cells = client.getData(tableName, rowKey);
        for (Cell cell : cells) {
            System.out.println(new String(cell.getValueArray(), StandardCharsets.UTF_8));
            System.out.println(CellUtil.getCellKeyAsString(cell));
        }
    }

    public static void scanData(String tableName, Scan scan) {
        client.scanData(tableName, scan);
    }
}
