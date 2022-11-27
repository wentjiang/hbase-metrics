package com.wentjiang.testcase.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 计时器类
 */
public class Timer {

    private long startTime;

    private final List<MetricTime> metricTimes = new ArrayList<>();

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void metricTime(String metricName) {
        metricTimes.add(new MetricTime(metricName, System.currentTimeMillis()));
    }

    public String getMetricReport() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < metricTimes.size(); i++) {
            MetricTime metricTime = metricTimes.get(i);
            long usedTime;
            if (i == 0) {
                usedTime = metricTime.getTime() - startTime;
            } else {
                usedTime = metricTime.getTime() - metricTimes.get(i - 1).getTime();
            }
            result.append(metricTime.getMetric()).append(" takes ").append(usedTime).append("\n");
        }
        return result.toString();
    }

}
