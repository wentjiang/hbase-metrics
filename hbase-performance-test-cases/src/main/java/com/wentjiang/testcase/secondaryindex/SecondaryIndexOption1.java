package com.wentjiang.testcase.secondaryindex;

import com.wentjiang.testcase.common.HBaseClient;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 二级索引实现1,使用同一个region实现
 */
public class SecondaryIndexOption1 {
    private static final HBaseClient client = new HBaseClient();

    private static final String tableName = "person";

    private static final String dataFamilyName = "personal_info";

    private static final String id = "id";

    private static final String passportId = "passportId";

    private static final String gender = "gender";

    private static final String indexFamilyName = "index";

    private static final String indexField = "idx";

    /**
     * 创建表 添加数据 测试scan的用法
     */
    public static void main(String[] args) {
        client.createTable(tableName, Arrays.asList(dataFamilyName, indexFamilyName));
        Person person = new Person("1", "100", "name", "man");
        putDataWithSecondaryIndex(person);
        getByRowKey(person.getId());
        getBySecondaryIndexKey("idx" + ":" + person.getPassportId() + ":");
        client.deleteTable(tableName);
    }

    private static void getByRowKey(String rowKey) {
        client.getData(tableName, rowKey).forEach(cell -> {
            System.out.println(CellUtil.getCellKeyAsString(cell));
            System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
        });
    }

    private static void getBySecondaryIndexKey(String indexKey) {
        Scan scan = new Scan();
        scan.setRowPrefixFilter(indexKey.getBytes());
        try {
            String row = Bytes.toString(client.scanData(tableName, scan).next().getRow());
            System.out.println(row);
            String id = row.substring(row.lastIndexOf(":") + 1);
            getByRowKey(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void putDataWithSecondaryIndex(Person person) {
        putData(person);
        putSecondaryIndex(person);
    }

    private static void putSecondaryIndex(Person person) {
        String rowKey = "idx" + ":" + person.getPassportId() + ":" + person.getId();
        client.putData(tableName, rowKey, indexFamilyName, indexField, String.valueOf(1), Durability.SYNC_WAL);
    }

    private static void putData(Person person) {
        client.putData(tableName, person.getId(), dataFamilyName, id, person.getId(), Durability.SYNC_WAL);
        client.putData(tableName, person.getId(), dataFamilyName, passportId, person.getPassportId(),
                Durability.SYNC_WAL);
        client.putData(tableName, person.getId(), dataFamilyName, gender, person.getGender(), Durability.SYNC_WAL);
    }

}
