package com.wentjiang.testcase.secondaryindex;

import com.wentjiang.testcase.common.CellHelper;
import com.wentjiang.testcase.common.HBaseClient;
import com.wentjiang.testcase.common.Timer;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.*;
import java.util.stream.IntStream;

/**
 * 全局二级索引
 */
public class SecondaryIndexOption2 extends BaseSecondaryIndexTest {
    private static final HBaseClient client = new HBaseClient();

    private static final String tableName = "person";

    private static final String dataFamilyName = "personal_info";

    private static final String id = "id";

    private static final String passportId = "passportId";

    private static final String gender = "gender";

    private static final String indexTableName = "person_index";
    private static final String indexFamilyName = "index";

    private static final String indexField = "idx";

    public SecondaryIndexOption2(Integer addDataCount, Integer scanDataCount) {
        super(addDataCount, scanDataCount, SecondaryIndexOption2.class.getName());
    }

    /**
     * 创建表 添加数据 测试scan的用法
     */
    public static void main(String[] args) {
        Timer timer = new SecondaryIndexOption2(1, 1).executeTest();
        System.out.println(timer.getMetricReport());
    }

    @Override
    void createTable() {
        // 创建数据表
        client.createTable(tableName, Collections.singletonList(dataFamilyName));
        // 创建index表
        client.createTable(indexTableName, Collections.singletonList(indexFamilyName));
    }

    @Override
    void putPerson(Person person) {
        putDataWithSecondaryIndex(person);
    }

    @Override
    void scanData() {
        IntStream.range(1, getScanDataCount() + 1).forEach(index -> {
            getBySecondaryIndexKey(index + "00");
        });
    }

    @Override
    void deleteTable() {
        client.deleteTable(tableName);
    }

    private void getBySecondaryIndexKey(String indexKey) {
        Optional<Cell> optCell = client.getData(indexTableName, indexKey).stream().findFirst();
        if (optCell.isPresent()) {
            Cell cell = optCell.get();
            String id = Bytes.toString(CellUtil.cloneValue(cell));
            List<Cell> cells = getByRowKey(id);
            showCells(cells);
        }
    }

    private List<Cell> getByRowKey(String rowKey) {
        return client.getData(tableName, rowKey);
    }

    private void putDataWithSecondaryIndex(Person person) {
        putDataToDataTable(person);
        putToIndexTable(person);
    }

    private void putToIndexTable(Person person) {
        client.putData(indexTableName, person.getPassportId(), indexFamilyName, indexField, person.getId(),
                Durability.SYNC_WAL);
    }

    private void putDataToDataTable(Person person) {
        client.putData(tableName, person.getId(), dataFamilyName, id, person.getId(), Durability.SYNC_WAL);
        client.putData(tableName, person.getId(), dataFamilyName, passportId, person.getPassportId(),
                Durability.SYNC_WAL);
        client.putData(tableName, person.getId(), dataFamilyName, gender, person.getGender(), Durability.SYNC_WAL);
    }
}
