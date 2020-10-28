package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.BuyData;

//
public interface Report {
    void aggregate(BuyData buyData);
    void generate();
}
