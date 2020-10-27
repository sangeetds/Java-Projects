package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.ReportData;

//
public interface Report {
    void aggregate(ReportData reportData);
    void generateReport();
    void openReport();
}
