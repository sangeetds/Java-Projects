package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.BuyData;

import java.util.List;

//
public interface Report {
    void aggregate(List<BuyData> buyData);
    void saveOutput();
}
