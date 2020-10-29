package com.cognitree.sangeet;

import com.cognitree.sangeet.reports.Report;
import com.cognitree.sangeet.reports.ReportsProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeData {

    public AnalyzeData(BufferedReader fileScanner) throws IOException {
        List<Report> reports = ReportsProvider.getReportsProvider().getReports();
        List<String> parsedData = new ArrayList<>();
        int lines = 0;

        String tempLine;
        while ((tempLine = fileScanner.readLine()) != null) {
            if (lines > 10000) {
                lines = 0;
                List<BuyData> buyData = processData(parsedData);
                aggregateData(buyData, reports);
                parsedData.clear();
            }

            parsedData.add(tempLine);
            lines++;
        }

        fileScanner.close();
    }

    private List<BuyData> processData(List<String> parsedData) {
        List<BuyData> buyDataList = new ArrayList<>();

        parsedData.forEach(data -> {
            String[] buyData = data.split(",") ;

            if (!validateData(buyData)) {
                System.out.println("The current data:" + data + "is invalid. Ignoring this data");
            }
            else {
                buyDataList.add(new BuyData(buyData));
            }
        });

        return buyDataList;
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
        ReportsProvider.getReportsProvider().getReports().forEach(Report::generate);
    }

    private void aggregateData(List<BuyData> currentData, List<Report> reports) {
        reports.forEach(report -> report.aggregate(currentData));
    }
}
