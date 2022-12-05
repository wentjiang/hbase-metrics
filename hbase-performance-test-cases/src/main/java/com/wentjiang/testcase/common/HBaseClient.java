package com.wentjiang.testcase.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        if (!getTableList().contains(tableName)) {
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
    }

    public void putData(String tableName, String rowKey, String familyName, String qualifier, String value,
            Durability durability) {
        byte[] row = Bytes.toBytes(rowKey);
        Put p = new Put(row);
        p.addImmutable(familyName.getBytes(), qualifier.getBytes(), Bytes.toBytes(value));
        p.setDurability(durability);
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.put(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void disableTable(String tableName) {
        try {
            admin.disableTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void enableTable(String tableName) {
        try {
            admin.enableTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteRow(String tableName, String rowKey) {
        byte[] row = Bytes.toBytes(rowKey);
        Delete d = new Delete(row);
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.delete(d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTable(String tableName) {
        try {
            TableName table = TableName.valueOf(tableName);
            admin.disableTable(table);
            admin.deleteTable(table);
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

    public ResultScanner scanData(String tableName, Scan scan) {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            return table.getScanner(scan);
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

    public Admin getAdmin() {
        return admin;
    }
}
