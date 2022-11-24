#!/bin/bash
echo "start init env"

export JAVA_HOME=/usr/local/java/jdk
export HADOOP_HOME=/opt/hadoop-3.2.0
export HADOOP_CONF_DIR=${HADOOP_HOME}/etc/hadoop
export HADOOP_LOG_DIR=/var/log/hadoop
export HDFS_NAMENODE_USER=root
export HDFS_DATANODE_USER=root
export HDFS_SECONDARYNAMENODE_USER=root
export YARN_LOG_DIR=/var/log/yarn
export YARN_CONF_DIR=${HADOOP_HOME}/etc/hadoop
export HBASE_HOME=/opt/hbase-2.2.3

if [[ -n $HADOOP_HOME ]]; then
  export PATH=$HADOOP_HOME/bin:$PATH
  export PATH=$HADOOP_HOME/sbin:$PATH
fi

if [[ -n $HBASE_HOME ]]; then
  export PATH=$HBASE_HOME/bin:$PATH
fi

export PATH=/opt/script:$PATH