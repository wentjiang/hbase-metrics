package com.wentjiang.testcase.memorycompactionpolicy;

import org.apache.hadoop.hbase.MemoryCompactionPolicy;

public class MemoryCompactionPolicyTest {
    public static void main(String[] args) {
        MemoryCompactionPolicyCases cases = new MemoryCompactionPolicyCases();
        cases.testCase(MemoryCompactionPolicy.NONE);
        cases.testCase(MemoryCompactionPolicy.BASIC);
        cases.testCase(MemoryCompactionPolicy.ADAPTIVE);
        cases.testCase(MemoryCompactionPolicy.EAGER);
    }
}
