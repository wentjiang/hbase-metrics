## 编译环境
使用java 8进行编译,如果需要用format插件的话,需要运行时环境如果在jre 19 byte-buddy会不生效

## 模块划分

### hbase-performance-test-cases
hbase进行性能测试的一些用例

### hbase-metrics-agent
hbase agent 切面增强的实现模块

### jmx-collector
hbase jmx性能分析的采集器

### metrics-analyse
对于采集到的metrics进行分析

## format code
Note: 需要使用java 8及以上的运行时环境才可以执行
```
mvn formatter:format
```

### Start commend

```
/Users/wentjiang/Library/Java/JavaVirtualMachines/corretto-11.0.16.1/Contents/Home/bin/java -javaagent:/Users/wentjiang/workspace/hbase-metrics/hbase-byte-buddy-agent/target/hbase-byte-buddy-agent-1.0-SNAPSHOT.jar -Dfile.encoding=UTF-8 -classpath /Users/wentjiang/workspace/hbase-metrics/mock-hbase/target/classes:/Users/wentjiang/.m2/repository/net/bytebuddy/byte-buddy/1.12.18/byte-buddy-1.12.18.jar:/Users/wentjiang/.m2/repository/net/bytebuddy/byte-buddy-agent/1.12.18/byte-buddy-agent-1.12.18.jar:/Users/wentjiang/.m2/repository/org/slf4j/slf4j-log4j12/1.7.25/slf4j-log4j12-1.7.25.jar:/Users/wentjiang/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar:/Users/wentjiang/.m2/repository/log4j/log4j/1.2.17/log4j-1.2.17.jar com.wentjiang.Main
```
