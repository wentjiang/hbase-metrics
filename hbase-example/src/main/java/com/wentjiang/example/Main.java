package com.wentjiang.example;

import com.wentjiang.example.testcase.common.HBaseClient;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Durability;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class Main {
    private static String tableName = "test_table";
    private static String rowKey = "test_rowKey1";
    private static String familyName = "test_field";
    private static String qualifier = "test_qualifierName";
    private static String value = "test_value1";

    public static void main(String[] args) {
        createTable(tableName, Collections.singletonList(familyName));
        printTableNames();
        putData(tableName, rowKey, familyName, qualifier, value);
        getData(tableName, rowKey);
    }

    public static void createTable(String tableName, List<String> fieldsName) {
        HBaseClient client = new HBaseClient();
        client.createTable(tableName, fieldsName);
    }

    public static void printTableNames() {
        HBaseClient client = new HBaseClient();
        for (String tableName : client.getTableList()) {
            System.out.println(tableName);
        }
    }

    public static void putData(String tableName, String rowKey, String familyName, String qualifier, String value) {
        HBaseClient client = new HBaseClient();
        client.putData(tableName, rowKey, familyName, qualifier, value, Durability.SYNC_WAL);
    }

    public static void getData(String tableName, String rowKey) {
        HBaseClient client = new HBaseClient();
        List<Cell> cells = client.getData(tableName, rowKey);
        for (Cell cell : cells) {
            System.out.println(new String(cell.getValueArray(), StandardCharsets.UTF_8));
            System.out.println(CellUtil.getCellKeyAsString(cell));
        }
    }
}