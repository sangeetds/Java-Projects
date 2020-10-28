package com.cognitree.sangeet.reports;

import java.util.Arrays;
import java.util.List;

public class ReportsProvider {

    private static ReportsProvider reportsProvider = null;
    private final List<Report> reports;

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
