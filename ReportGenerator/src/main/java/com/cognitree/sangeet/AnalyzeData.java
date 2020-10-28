package com.cognitree.sangeet;

import com.cognitree.sangeet.reports.Report;
import com.cognitree.sangeet.reports.ReportsProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class AnalyzeData {
    ReportsProvider reportsProvider;
    List<Report> reports;

    public AnalyzeData(BufferedReader fileScanner) throws IOException {
        this.reportsProvider = ReportsProvider.getReportsProvider();
        this.reports = reportsProvider.getReports();

        String line;
        while ((line = fileScanner.readLine()) != null) {
            BuyData currentData = new BuyData(line.split(","));
            aggregateData(currentData);
        }

        fileScanner.close();
    }

    public void generateAllReports() {
        this.reports.forEach(Report::generate);
    }

    private void aggregateData(BuyData currentData) {
        this.reports.forEach(report -> report.aggregate(currentData));
    }
}
