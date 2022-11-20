package com.wentjiang;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;
import java.util.List;

public class HBaseClient {

    public HBaseClient() {
        Configuration config = HBaseConfiguration.create();

        String path = this.getClass().getClassLoader().getResource("hbase-site.xml").getPath();
        config.addResource(new Path(path));

        try {
            HBaseAdmin.available(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable(String tableName, List<String> families) {
        TableName table1 = TableName.valueOf("Table1");
        String family1 = "Family1";
        String family2 = "Family2";
    }
}
