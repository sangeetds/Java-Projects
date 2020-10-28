package com.cognitree.sangeet;

import com.cognitree.sangeet.reports.Report;
import com.cognitree.sangeet.reports.ReportsProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class AnalyzeData {
    ReportsProvider reportsProvider;
    List<Report> reports;

    public AnalyzeData(BufferedReader fileScanner) throws IOException {
        this.reportsProvider = ReportsProvider.getReportsProvider();
        this.reports = reportsProvider.getReports();

        String line;
        while ((line = fileScanner.readLine()) != null) {
            if (!validateData(line.split(","))) {
                System.out.println("The current data:" + line + "is invalid. Ignoring this data");
            }

            BuyData currentData = new BuyData(line.split(","));
            aggregateData(currentData);
        }

        fileScanner.close();
    }

    private boolean validateData(String[] line) {
        for (int i = 0; i < line.length; i++) {
            String data = line[i];

            if (i != 1) {
                try {
                    Integer.parseInt(data);
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            else {
                try {
                    Timestamp.valueOf(data.replace("T", " ").replace("Z", ""));
                }
                catch (DateTimeParseException e) {
                    return false;
                }
            }
        }


        return true;
    }

    public void generateAllReports() {
        this.reports.forEach(Report::generate);
    }

    private void aggregateData(BuyData currentData) {
        this.reports.forEach(report -> report.aggregate(currentData));
    }
}
