package com.cognitree.sangeet;

import com.cognitree.sangeet.reports.Report;
import com.cognitree.sangeet.reports.ReportsProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AnalyzeData {
    ReportsProvider reportsProvider;
    List<Report> reports;

    public AnalyzeData(BufferedReader fileScanner) throws IOException {
        this.reportsProvider = ReportsProvider.getReportsProvider();
        this.reports = reportsProvider.getReports();

        String line;
        while ((line = fileScanner.readLine()) != null) {
            //TimeTaking
            ReportData currentData = new ReportData(line.split(","));
            aggregateData(currentData);
        }
        fileScanner.close();
    }

    public void generateReports() {
        for (Report report: this.reports) {
            report.generateReport();
        }
    }

    private void aggregateData(ReportData currentData) {
        for (Report report: this.reports) {
            report.aggregate(currentData);
        }
    }

}
