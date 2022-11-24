rm -f /opt/agent/hbase-metrics-agent.jar
if [ $? -ne 0 ]; then
  echo "remove the old agent fail"
else
  echo "remove the old agent success"
fi
cp /wentjiang/workspace/hbase-metrics/hbase-metrics-agent/target/hbase-metrics-agent.jar /opt/agent/
if [ $? -ne 0 ]; then
  echo "copy the new agent fail"
else
  echo "copy the new agent success"
fi