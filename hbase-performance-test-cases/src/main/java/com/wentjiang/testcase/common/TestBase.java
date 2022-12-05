package com.wentjiang.testcase.common;

import org.apache.hadoop.hbase.client.Admin;

public class TestBase {

    protected final HBaseClient hBaseClient;
    protected final Admin admin;

    public TestBase() {
        this.hBaseClient = new HBaseClient();
        admin = hBaseClient.getAdmin();
    }

    protected void deleteTable(String tableName) {
        hBaseClient.deleteTable(tableName);
    }
}
