package com.wentjiang.testcase.memorycompactionpolicy;

import com.wentjiang.testcase.common.TestBase;
import com.wentjiang.testcase.common.Timer;
import org.apache.hadoop.hbase.MemoryCompactionPolicy;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MemoryCompactionPolicyCases extends TestBase {

    private final List<String> familyNames = Arrays.asList("test_family_0", "test_family_1", "test_family_2");

    private static final int totalUpdateNum = 10000;

    private static final String rowKey = "rowKey";

    public void testCase(MemoryCompactionPolicy compactionPolicy) {
        System.out.println(compactionPolicy.name() + " report:");
        Timer timer = new Timer(compactionPolicy.name());
        timer.start();
        String tableName = createTempTable(compactionPolicy);
        timer.metricTime("createTable");
        writeSameRawWithDifferentValue(tableName);
        timer.metricTime("writeSameRawWithDifferentValue");
        deleteTable(tableName);
        timer.metricTime("deleteTable");
        System.out.println(timer.getMetricReport());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeSameRawWithDifferentValue(String tableName) {
        for (int i = 0; i < totalUpdateNum; i++) {
            String familyName = "test_family_" + i % 3;
            String qualifier = "test_qualifier_" + i % 3;
            String value = "test_value_" + i;
            hBaseClient.putData(tableName, rowKey, familyName, qualifier, value, Durability.USE_DEFAULT);
        }
    }

    private String createTempTable(MemoryCompactionPolicy compactionPolicy) {
        String tableName = compactionPolicy.name() + "_" + new Random().nextInt();
        List<ColumnFamilyDescriptor> collect = familyNames.stream()
                .map(familyName -> ColumnFamilyDescriptorBuilder.newBuilder(familyName.getBytes(StandardCharsets.UTF_8))
                        .setInMemoryCompaction(compactionPolicy).build())
                .collect(Collectors.toList());
        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName))
                .setColumnFamilies(collect).build();
        try {
            admin.createTable(tableDescriptor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tableName;
    }
}
