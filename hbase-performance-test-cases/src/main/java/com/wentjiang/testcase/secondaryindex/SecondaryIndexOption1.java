package com.wentjiang.testcase.secondaryindex;

import com.wentjiang.testcase.common.HBaseClient;
import com.wentjiang.testcase.common.Timer;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 二级索引实现1,使用同一个region实现
 */
public class SecondaryIndexOption1 extends BaseSecondaryIndexTest {
    private static final HBaseClient client = new HBaseClient();

    private static final String tableName = "person";

    private static final String dataFamilyName = "personal_info";

    private static final String id = "id";

    private static final String passportId = "passportId";

    private static final String gender = "gender";

    private static final String indexFamilyName = "index";

    private static final String indexField = "idx";

    public SecondaryIndexOption1(Integer addDataCount, Integer scanDataCount) {
        super(addDataCount, scanDataCount, SecondaryIndexOption1.class.getName());
    }

    /**
     * 创建表 添加数据 测试scan的用法
     */
    public static void main(String[] args) {
        Timer timer = new SecondaryIndexOption1(1, 1).executeTest();
        System.out.println(timer.getMetricReport());
    }

    @Override
    void createTable() {
        client.createTable(tableName, Arrays.asList(dataFamilyName, indexFamilyName));
    }

    @Override
    void putPerson(Person person) {
        putDataWithSecondaryIndex(person);
    }

    @Override
    void scanData() {
        IntStream.range(1, getScanDataCount()+1).forEach(index -> {
            getBySecondaryIndexKey("idx" + ":" + index + "00" + ":");
        });
    }

    @Override
    void deleteTable() {
        client.deleteTable(tableName);
    }

    private void getByRowKey(String rowKey) {
        List<Cell> cells = client.getData(tableName, rowKey);
        showCells(cells);
    }

    private void getBySecondaryIndexKey(String indexKey) {
        Scan scan = new Scan();
        scan.setRowPrefixFilter(indexKey.getBytes());
        try {
            String row = Bytes.toString(client.scanData(tableName, scan).next().getRow());
            String id = row.substring(row.lastIndexOf(":") + 1);
            getByRowKey(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void putDataWithSecondaryIndex(Person person) {
        putData(person);
        putSecondaryIndex(person);
    }

    private void putSecondaryIndex(Person person) {
        String rowKey = "idx" + ":" + person.getPassportId() + ":" + person.getId();
        client.putData(tableName, rowKey, indexFamilyName, indexField, String.valueOf(1), Durability.SYNC_WAL);
    }

    private void putData(Person person) {
        client.putData(tableName, person.getId(), dataFamilyName, id, person.getId(), Durability.SYNC_WAL);
        client.putData(tableName, person.getId(), dataFamilyName, passportId, person.getPassportId(),
                Durability.SYNC_WAL);
        client.putData(tableName, person.getId(), dataFamilyName, gender, person.getGender(), Durability.SYNC_WAL);
    }

}
