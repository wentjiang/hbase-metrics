echo "stop hbase"
stop-hbase.sh
echo "delete hdfs hbase dir"
hdfs dfs -rm -r /hbase
echo "delete successfully"
