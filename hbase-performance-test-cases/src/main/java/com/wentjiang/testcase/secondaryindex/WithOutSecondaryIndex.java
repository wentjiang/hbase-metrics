package com.wentjiang.testcase.secondaryindex;

import com.wentjiang.testcase.common.HBaseClient;
import com.wentjiang.testcase.common.Timer;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.IntStream;

/**
 * 没有二级索引时的性能测试
 */
public class WithOutSecondaryIndex extends BaseSecondaryIndexTest {

    private static final HBaseClient client = new HBaseClient();

    private static final String tableName = "person";

    private static final String dataFamilyName = "personal_info";

    private static final String id = "id";

    private static final String passportId = "passportId";

    private static final String gender = "gender";

    public WithOutSecondaryIndex(Integer addDataCount, Integer scanDataCount) {
        super(addDataCount, scanDataCount, WithOutSecondaryIndex.class.getName());
    }

    public static void main(String[] args) {
        Timer timer = new WithOutSecondaryIndex(1, 1).executeTest();
        System.out.println(timer.getMetricReport());
    }

    @Override
    void createTable() {
        client.createTable(tableName, Collections.singletonList(dataFamilyName));
    }

    @Override
    void putPerson(Person person) {
        putData(person);
    }

    @Override
    void scanData() {
        IntStream.range(1, getScanDataCount() + 1).forEach(index -> {
            scanByPassportId(index + "00");
        });
    }

    @Override
    void deleteTable() {
        client.deleteTable(tableName);
    }

    private void scanByPassportId(String passportId) {
        Scan scan = new Scan();

        Filter filter = new SingleColumnValueFilter(dataFamilyName.getBytes(), passportId.getBytes(),
                CompareOperator.EQUAL, "100".getBytes());
        scan.setFilter(filter);
        try {
            Result result = client.scanData(tableName, scan).next();
            showCells(result.listCells());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void putData(Person person) {
        client.putData(tableName, person.getId(), dataFamilyName, id, person.getId(), Durability.SYNC_WAL);
        client.putData(tableName, person.getId(), dataFamilyName, passportId, person.getPassportId(),
                Durability.SYNC_WAL);
        client.putData(tableName, person.getId(), dataFamilyName, gender, person.getGender(), Durability.SYNC_WAL);
    }
}
