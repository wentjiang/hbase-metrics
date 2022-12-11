package com.wentjiang.testcase.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 计时器类
 */
public class Timer {

    private String caseName;

    private long startTime;

    private final List<MetricTime> metricTimes = new ArrayList<>();

    public Timer(String caseName) {
        this.caseName = caseName;
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void metricTime(String metricName) {
        metricTimes.add(new MetricTime(metricName, System.currentTimeMillis()));
    }

    public String getMetricReport() {
        StringBuilder result = new StringBuilder();
        result.append(caseName).append("\n");
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
