package com.wentjiang.testcase.secondaryindex;

import com.wentjiang.testcase.common.CellHelper;
import com.wentjiang.testcase.common.Timer;
import lombok.Getter;
import org.apache.hadoop.hbase.Cell;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Getter
public abstract class BaseSecondaryIndexTest {

    private final Integer addDataCount;

    private final Integer scanDataCount;

    private final String caseName;

    public BaseSecondaryIndexTest(Integer addDataCount, Integer scanDataCount, String caseName) {
        this.addDataCount = addDataCount;
        this.scanDataCount = scanDataCount;
        this.caseName = caseName;
    }

    public Timer executeTest() {
        Timer timer = new Timer(caseName);
        timer.start();
        createTable();
        timer.metricTime("createTable");
        IntStream.range(1, addDataCount + 1).forEach(index -> {
            Person person = new Person(String.valueOf(index), index + "00", "name", "man");
            putPerson(person);
        });
        timer.metricTime("putData:" + addDataCount);
        scanData();
        timer.metricTime("scanData:" + scanDataCount);
        deleteTable();
        timer.metricTime("deleteTable");
        return timer;
    }

    abstract void createTable();

    abstract void putPerson(Person person);

    abstract void scanData();

    abstract void deleteTable();

    public void showCells(List<Cell> cells) {
        Map<String, String> map = CellHelper.cells2Map(cells);
        map.forEach((key, value) -> System.out.println("key:" + key + " value:" + value));
    }
}
