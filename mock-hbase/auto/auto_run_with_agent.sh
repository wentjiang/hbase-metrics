CURRENT_DIR=$(cd $(dirname "$0"); pwd)

PROJECT_DIR=$(cd "$CURRENT_DIR" && cd .. && cd .. && pwd)

# clean 整个项目
echo "clean"
cd $PROJECT_DIR
mvn clean

# 打包编译agent模块
echo "compile agent"
cd "$PROJECT_DIR"/hbase-metrics-agent
mvn -DskipTests=true package

# 打包编译mock-hbase模块
echo "package mock-hbase"
cd "$PROJECT_DIR"/mock-hbase
mvn -DskipTests=true package

# 执行主类测试
echo "run main with agent"
java -javaagent:"$PROJECT_DIR"/hbase-metrics-agent/target/hbase-metrics-agent.jar -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath "$PROJECT_DIR"/mock-hbase/target/mock-hbase.jar com.wentjiang.mockhbase.Main
echo "test finished"
