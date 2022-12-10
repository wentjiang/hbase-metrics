package com.wentjiang.testcase.secondaryindex;

import com.wentjiang.testcase.common.HBaseClient;
import org.apache.hadoop.hbase.client.Durability;

import java.util.Arrays;

/**
 * 没有二级索引时的性能测试
 */
public class WithOutSecondaryIndex {

    private static final HBaseClient client = new HBaseClient();

    private static final String tableName = "person";

    private static final String dataFamilyName = "personal_info";

    private static final String id = "id";

    private static final String passportId = "passportId";

    private static final String gender = "gender";

    public static void main(String[] args) {
        client.createTable(tableName, Arrays.asList(dataFamilyName));
        Person person = new Person("1", "100", "name", "man");
        putData(person);

    }

    private static void putData(Person person) {
        client.putData(tableName, person.getId(), dataFamilyName, id, person.getId(), Durability.SYNC_WAL);
        client.putData(tableName, person.getId(), dataFamilyName, passportId, person.getPassportId(),
                Durability.SYNC_WAL);
        client.putData(tableName, person.getId(), dataFamilyName, gender, person.getGender(), Durability.SYNC_WAL);
    }
}
