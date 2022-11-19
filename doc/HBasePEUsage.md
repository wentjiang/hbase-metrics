General Options:
操作选项
nomapred        Run multiple clients using threads (rather than use mapreduce)
                运行多个客户端使用多个线程
oneCon          all the threads share the same connection. Default: False
                所有的线程使用相同的连接
connCount          connections all threads share. For example, if set to 2, then all thread share 2 connection. Default: depend on oneCon parameter. if oneCon set to true, then connCount=1, if not, connCount=thread number
                所有的线程共享的连接数
sampleRate      Execute test on a sample of total rows. Only supported by randomRead. Default: 1.0
                执行总数,只支持随机读,默认1
period          Report every 'period' rows: Default: opts.perClientRunRows / 10 = 104857
                执行多少行输出一个报告
cycles          How many times to cycle the test. Defaults: 1.
                当前测试用例循环多少次
traceRate       Enable HTrace spans. Initiate tracing every N rows. Default: 0
                多少个请求记录一个HTrace
latency         Set to report operation latencies. Default: False
                设置报告操作延迟
measureAfter    Start to measure the latency once 'measureAfter' rows have been treated. Default: 0
                记录延迟从指定的行之后,默认从0开始
valueSize       Pass value size to use: Default: 1000
                传递要使用的值
valueRandom     Set if we should vary value size between 0 and 'valueSize'; set on read for stats on size: Default: Not set.
                随机指定传递的值 0到设置的valueSize的值
blockEncoding   Block encoding to use. Value should be one of [NONE, PREFIX, DIFF, FAST_DIFF, ROW_INDEX_V1]. Default: NONE
                使用哪种encoding.

Table Creation / Write Tests:
建表,写入
table           Alternate table name. Default: 'TestTable'
                可选的table名称
rows            Rows each client runs. Default: 1048576.  In case of randomReads and randomSeekScans this could be specified along with --size to specify the number of rows to be scanned within the total range specified by the size.
                每个客户端跑的,指定的size中扫描的行数
size            Total size in GiB. Mutually exclusive with --rows for writes and scans. But for randomReads and randomSeekScans when you use size with --rows you could use size to specify the end range and --rows specifies the number of rows within that range. Default: 1.0.
                GB的总量,与写和查询的-- rows 互斥. 
compress        Compression type to use (GZ, LZO, ...). Default: 'NONE'
                压缩格式.
flushCommits    Used to determine if the test should flush the table. Default: false
                测试后是否要刷新表
valueZipf       Set if we should vary value size between 0 and 'valueSize' in zipf form: Default: Not set.
                
writeToWAL      Set writeToWAL on puts. Default: True
                添加时是否使用WAL
autoFlush       Set autoFlush on htable. Default: False
                客户端自动flush到表中,如果设置为false的话,客户端缓存存满了之后再发送到服务端
multiPut        Batch puts together into groups of N. Only supported by write. If multiPut is bigger than 0, autoFlush need to set to true. Default: 0
                一次写入的数量
presplit        Create presplit table. If a table with same name exists, it'll be deleted and recreated (instead of verifying count of its existing regions). Recommended for accurate perf analysis (see guide). Default: disabled
                创建预制的分割表,用与准确的性能分析,删除并且重新创建新的表
usetags         Writes tags along with KVs. Use with HFile V3. Default: false
                使用tags写,默认关闭
numoftags       Specify the no of tags that would be needed. This works only if usetags is true. Default: 1
                tags的数量
splitPolicy     Specify a custom RegionSplitPolicy for the table.
                region分割策略
columns         Columns to write per row. Default: 1
                每行分割多少列
families        Specify number of column families for the table. Default: 1
                定义有多少个列簇

Read Tests:
读测试
filterAll       Helps to filter out all the rows on the server side there by not returning any thing back to the client.  Helps to check the server side performance.  Uses FilterAllFilter internally.
                在服务端过滤所有的数据,不会有数据返回到客户端,协助检查server端的性能
multiGet        Batch gets together into groups of N. Only supported by randomRead. Default: disabled
                随机读的时候batch的返回多个结果,默认关闭
inmemory        Tries to keep the HFiles of the CF inmemory as far as possible. Not guaranteed that reads are always served from memory.  Default: false
                尽可能的保证HFiles在内存中,不保证总是在内存里,默认关闭
bloomFilter     Bloom filter type, one of [NONE, ROW, ROWCOL, ROWPREFIX_FIXED_LENGTH]
                布隆过滤器类型
blockSize       Blocksize to use when writing out hfiles.
                写出hfile时使用的块大小
inmemoryCompaction  Makes the column family to do inmemory flushes/compactions. Uses the CompactingMemstore
                在内存中压缩和落盘.使用CompactingMemstore
addColumns      Adds columns to scans/gets explicitly. Default: true
                使用列去准确的获取和搜索,默认开启
replicas        Enable region replica testing. Defaults: 1.
                开启region备份测试,默认为1
randomSleep     Do a random sleep before each get between 0 and entered value. Defaults: 0
                随机睡眠在获取 0和输出的值之前
caching         Scan caching to use. Default: 30
                搜索缓存,默认30
asyncPrefetch   Enable asyncPrefetch for scan
                搜索时打开异步的预加载
cacheBlocks     Set the cacheBlocks option for scan. Default: true
                搜索时设置缓存块,默认为true
scanReadType    Set the readType option for scan, stream/pread/default. Default: default
                设置搜索时的读类型
bufferSize      Set the value of client side buffering. Default: 2MB
                设置客户端的缓存,默认2MB
Note: -D properties will be applied to the conf used.
                
For example:
-Dmapreduce.output.fileoutputformat.compress=true
-Dmapreduce.task.timeout=60000

Command:
命令
append               Append on each row; clients overlap on keyspace so some concurrent operations
                        每行去增加,客户端覆盖key的空间,所以会有并发操作
asyncRandomRead      Run async random read test
                        执行异步随机读测试
asyncRandomWrite     Run async random write test
                        执行异步随机写测试
asyncScan            Run async scan test (read every row)
                        执行异步搜索测试(读每一行)
asyncSequentialRead  Run async sequential read test
                        执行异步顺序读测试        
asyncSequentialWrite Run async sequential write test
                        执行异步顺序写测试
checkAndDelete       CheckAndDelete on each row; clients overlap on keyspace so some concurrent operations
                        对于每一行检查和删除,客户端覆盖key,所以有并发操作
checkAndMutate       CheckAndMutate on each row; clients overlap on keyspace so some concurrent operations
                        每一行检查和变化
checkAndPut          CheckAndPut on each row; clients overlap on keyspace so some concurrent operations
                        每一行检查和添加
filterScan           Run scan test using a filter to find a specific row based on it's value (make sure to use --rows=20)
                        用基于值的过滤器跑查询测试来发现特定的行
increment            Increment on each row; clients overlap on keyspace so some concurrent operations
                        每行增量
randomRead           Run random read test
                        随机读测试
randomSeekScan       Run random seek and scan 100 test
                        随机搜索测试
randomWrite          Run random write test
                        随机写测试
scan                 Run scan test (read every row)
                        读所有行的测试
scanRange10          Run random seek scan with both start and stop row (max 10 rows)
                        随机搜索最多10行
scanRange100         Run random seek scan with both start and stop row (max 100 rows)
                        随机搜索最多100行
scanRange1000        Run random seek scan with both start and stop row (max 1000 rows)
            
scanRange10000       Run random seek scan with both start and stop row (max 10000 rows)
sequentialRead       Run sequential read test
                        顺序读
sequentialWrite      Run sequential write test
                        顺序写

Args:
nclients        Integer. Required. Total number of clients (and HRegionServers) running. 1 <= value <= 500
Examples:
To run a single client doing the default 1M sequentialWrites:
$ hbase pe sequentialWrite 1
To run 10 clients doing increments over ten rows:
$ hbase pe --rows=10 --nomapred increment 10