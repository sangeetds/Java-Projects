package com.cognitree.sangeet;

import com.cognitree.sangeet.reports.Report;
import com.cognitree.sangeet.reports.ReportsProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalyzeData {
    ReportsProvider reportsProvider;
    List<Report> reports;

    public AnalyzeData(BufferedReader fileScanner) throws IOException {
        this.reportsProvider = ReportsProvider.getReportsProvider();
        this.reports = reportsProvider.getReports();
        List<BuyData> buyData = new ArrayList<>();
        int lines = 0;

        String tempLine;
        while ((tempLine = fileScanner.readLine()) != null) {
            String[] buyInfo = tempLine.split(",");

            if (!validateData(buyInfo)) {
                System.out.println("The current data:" + tempLine + "is invalid. Ignoring this data");
            }
            BuyData currentData = new BuyData(buyInfo);

            if (lines > 100000) {
                lines = 0;
                aggregateData(buyData);
                buyData.clear();
            }

            buyData.add(currentData);
            lines++;
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

    private void aggregateData(List<BuyData> currentData) {
        this.reports.forEach(report -> report.aggregate(currentData));
    }
}
