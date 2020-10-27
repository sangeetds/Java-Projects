package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.ReportData;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class ReportsProvider {

    private static ReportsProvider reportsProvider = null;
    private final List<Report> reports; // Is it necessary to return back?

    private ReportsProvider() {
        this.reports = Arrays.asList(new AverageQuantityReport(), new PurchaseEventCountReport(), new DistinctSessionReport());
    }

    public static ReportsProvider getReportsProvider() {
        if (reportsProvider == null) {
            reportsProvider = new ReportsProvider();
        }

        return reportsProvider;
    }

    public List<Report> getReports() {
        return reports;
    }
}
