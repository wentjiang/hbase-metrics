package com.wentjiang.testcase.secondaryindex;

import com.wentjiang.testcase.common.CellHelper;
import com.wentjiang.testcase.common.HBaseClient;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SecondaryIndexOption2 {
    private static final HBaseClient client = new HBaseClient();

    private static final String tableName = "person";

    private static final String dataFamilyName = "personal_info";

    private static final String id = "id";

    private static final String passportId = "passportId";

    private static final String gender = "gender";

    private static final String indexTableName = "person_index";
    private static final String indexFamilyName = "index";

    private static final String indexField = "idx";

    /**
     * 创建表 添加数据 测试scan的用法
     */
    public static void main(String[] args) {
        // 创建数据表
        client.createTable(tableName, Arrays.asList(dataFamilyName));
        // 创建index表
        client.createTable(indexTableName, Arrays.asList(indexFamilyName));
        Person person = new Person("1", "100", "name", "man");
        putDataWithSecondaryIndex(person);
        getBySecondaryIndexKey("100");
        client.deleteTable(tableName);
    }

    private static void getBySecondaryIndexKey(String indexKey) {
        Optional<Cell> optCell = client.getData(indexTableName, indexKey).stream().findFirst();
        if (optCell.isPresent()) {
            Cell cell = optCell.get();
            String id = Bytes.toString(CellUtil.cloneValue(cell));
            Map<String, String> map = CellHelper.cells2Map(getByRowKey(id));
            map.forEach((key, value) -> System.out.println("key:" + key + " value:" + value));
        }
    }

    private static List<Cell> getByRowKey(String rowKey) {
        return client.getData(tableName, rowKey);
    }

    private static void putDataWithSecondaryIndex(Person person) {
        putDataToDataTable(person);
        putToIndexTable(person);
    }

    private static void putToIndexTable(Person person) {
        client.putData(indexTableName, person.getPassportId(), indexFamilyName, indexField, person.getId(),
                Durability.SYNC_WAL);
    }

    private static void putDataToDataTable(Person person) {
        client.putData(tableName, person.getId(), dataFamilyName, id, person.getId(), Durability.SYNC_WAL);
        client.putData(tableName, person.getId(), dataFamilyName, passportId, person.getPassportId(),
                Durability.SYNC_WAL);
        client.putData(tableName, person.getId(), dataFamilyName, gender, person.getGender(), Durability.SYNC_WAL);
    }
}
