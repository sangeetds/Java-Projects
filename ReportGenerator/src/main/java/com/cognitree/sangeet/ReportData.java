package com.cognitree.sangeet;

import java.sql.Timestamp;

public class ReportData {
    // Can be treat everyone of them as a String?
    Integer sessionId;
    Timestamp timestamp;
    Integer itemId;
    Integer price;
    Integer quantity;

    public ReportData(String[] data) {
        this.sessionId = Integer.parseInt(data[0]);
        /*Time Taking */this.timestamp = Timestamp.valueOf(data[1].replace("T", " ").replace("Z", ""));
        this.itemId = Integer.valueOf(data[2]);
        this.price = Integer.valueOf(data[3]);
        this.quantity = Integer.valueOf(data[4]);
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
