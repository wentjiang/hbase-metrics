package com.wentjiang.example.testcase.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetricTime {
    private String metric;
    private long time;
}
