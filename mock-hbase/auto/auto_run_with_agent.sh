# 编译agent
echo "compile agent"
cd /Users/wentjiang/workspace/hbase-metrics/hbase-metrics-agent
pwd
# java -Dmaven.multiModuleProjectDirectory=/Users/wentjiang/workspace/hbase-metrics/hbase-metrics-agent -Dmaven.home=/Users/wentjiang/tools/apache-maven-3.8.6 -Dclassworlds.conf=/Users/wentjiang/tools/apache-maven-3.8.6/bin/m2.conf -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath /Users/wentjiang/tools/apache-maven-3.8.6/boot/plexus-classworlds.license:/Users/wentjiang/tools/apache-maven-3.8.6/boot/plexus-classworlds-2.6.0.jar org.codehaus.classworlds.Launcher -Didea.version=2022.2.3 package
mvn package
# 编译当前模块
echo "compile mock-hbase"
cd /Users/wentjiang/workspace/hbase-metrics/mock-hbase
pwd
#java -Dmaven.multiModuleProjectDirectory=/Users/wentjiang/workspace/hbase-metrics/mock-hbase -Dmaven.home=/Users/wentjiang/tools/apache-maven-3.8.6 -Dclassworlds.conf=/Users/wentjiang/tools/apache-maven-3.8.6/bin/m2.conf -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath /Users/wentjiang/tools/apache-maven-3.8.6/boot/plexus-classworlds.license:/Users/wentjiang/tools/apache-maven-3.8.6/boot/plexus-classworlds-2.6.0.jar org.codehaus.classworlds.Launcher -Didea.version=2022.2.3 compile
mvn compile
# 执行主类测试
echo "run main"
java -javaagent:/Users/wentjiang/workspace/hbase-metrics/hbase-metrics-agent/target/hbase-metrics-agent.jar -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath /Users/wentjiang/workspace/hbase-metrics/mock-hbase/target/classes:/Users/wentjiang/.m2/repository/org/projectlombok/lombok/1.18.24/lombok-1.18.24.jar com.wentjiang.mockhbase.Main


