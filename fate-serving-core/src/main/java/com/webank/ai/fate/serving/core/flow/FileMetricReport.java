package com.webank.ai.fate.serving.core.flow;

import java.util.List;

public class FileMetricReport implements MetricReport{

    private static final long DEFAULT_FILE_SIZE = 1024 * 1024;

    MetricWriter metricWriter;

    public FileMetricReport(String appName) {
        this.metricWriter= new MetricWriter(appName, DEFAULT_FILE_SIZE);
    }

    @Override
    public void report(List<MetricNode> data) {
//        long  currentTime =  TimeUtil.currentTimeMillis();
        try {
            metricWriter.write(TimeUtil.currentTimeMillis(),data);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}