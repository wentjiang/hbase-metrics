package com.wentjiang.testcase.common;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellHelper {

    public static Map<String, String> cells2Map(List<Cell> cells) {
        Map<String, String> map = new HashMap<>();
        cells.forEach(cell -> {
            String key = CellUtil.getCellKeyAsString(cell);
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            map.put(key, value);
        });
        return map;
    }

}
