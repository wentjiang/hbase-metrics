package com.wentjiang;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HBaseClient {
    private final Admin admin;
    private final Connection connection;

    public HBaseClient() {

        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("hbase-site.xml")).getPath();
        Configuration config = HBaseConfiguration.create();
        config.addResource(new Path(path));

        try {
            HBaseAdmin.available(config);
            connection = ConnectionFactory.createConnection(config);
            admin = connection.getAdmin();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable(String tableName, List<String> families) {

        List<ColumnFamilyDescriptor> collect = families.stream().map(ColumnFamilyDescriptorBuilder::of)
                .collect(Collectors.toList());
        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName))
                .setColumnFamilies(collect).build();

        try {
            admin.createTable(tableDescriptor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void putData(String tableName, String rowKey, String familyName, String qualifier, String value) {
        byte[] row = Bytes.toBytes(rowKey);
        Put p = new Put(row);
        p.addImmutable(familyName.getBytes(), qualifier.getBytes(), Bytes.toBytes(value));
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.put(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cell> getData(String tableName, String rowKey) {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Get g = new Get(rowKey.getBytes());
            Result result = table.get(g);
            return result.listCells();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getTableList() {
        try {
            return Arrays.stream(admin.listTableNames()).map(TableName::getNameAsString).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
