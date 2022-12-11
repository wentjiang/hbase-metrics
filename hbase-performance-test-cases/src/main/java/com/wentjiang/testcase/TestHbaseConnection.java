package com.wentjiang.testcase;

import com.wentjiang.testcase.common.TestBase;

import java.io.IOException;
import java.util.Arrays;

public class TestHbaseConnection extends TestBase {
    public static void main(String[] args) {
        try {
            Arrays.stream(new TestHbaseConnection().admin.listTableNames())
                    .forEach(tableName -> System.out.println(tableName.getNameAsString()));
            System.out.println("Connect successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
