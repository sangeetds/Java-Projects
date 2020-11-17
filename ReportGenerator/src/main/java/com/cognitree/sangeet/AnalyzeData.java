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

    public AnalyzeData(BufferedReader fileScanner) {
        List<Report> reports = ReportsProvider.getReportsProvider().getReports();
        List<String> parsedData = new ArrayList<>();
        int batches = 0;
        int lines = 0;

        String tempLine;
        try {
            while ((tempLine = fileScanner.readLine()) != null) {
                if (lines > 10000) {
                    batches++;
                    lines = 0;
                    List<BuyData> buyData = processData(parsedData);
                    aggregateData(buyData, reports);
                    parsedData.clear();
                }

                parsedData.add(tempLine);
                lines++;
            }

        }
        catch (IOException e) {
            System.out.println("Problem with data point at: " + batches * lines);
        }
        finally {
            List<BuyData> buyData = processData(parsedData);
            aggregateData(buyData, reports);
            parsedData.clear();

            try {
                fileScanner.close();
            } catch (IOException e) {
                System.out.println("Problems at closing the file");
            }
        }
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

    private boolean validateData(String[] lines) {
        for (int buyDataIndex = 0; buyDataIndex < lines.length; buyDataIndex++) {
            String data = lines[buyDataIndex];

            if (buyDataIndex != 1) {
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
        ReportsProvider.getReportsProvider().getReports().forEach(Report::saveOutput);
    }

    private void aggregateData(List<BuyData> currentData, List<Report> reports) {
        reports.forEach(report -> report.aggregate(currentData));
    }
}
