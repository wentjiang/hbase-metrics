## HBase 性能测试模块

## 使用方法
打包生成对应的 `hbase-performance-test-cases.jar`
```
mvn package 
```

执行
```
java -cp hbase-performance-test-cases.jar com.wentjiang.example.testcase.Main
```

### WALDurability
package: `waldurability`

测试不同的WAL Durability对于HBase性能的影响

### MemoryCompactionPolicy
package: `memorycompactionpolicy`

测试不同的MemoryCompactionPolicy的写入性能的影响.
