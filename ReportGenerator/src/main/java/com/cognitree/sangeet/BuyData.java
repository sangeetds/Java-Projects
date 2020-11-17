package com.cognitree.sangeet;

import java.sql.Timestamp;
import java.util.Objects;

final public class BuyData {
    private final Integer sessionId;
    private final Timestamp timestamp;
    private final Integer itemId;
    private final Integer price;
    private final Integer quantity;

    public BuyData(String[] data) {
        this.sessionId = Integer.parseInt(data[0]);
        this.timestamp = Timestamp.valueOf(data[1].replace("T", " ").replace("Z", ""));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyData buyData = (BuyData) o;
        return getSessionId().equals(buyData.getSessionId()) &&
                getTimestamp().equals(buyData.getTimestamp()) &&
                getItemId().equals(buyData.getItemId()) &&
                getPrice().equals(buyData.getPrice()) &&
                getQuantity().equals(buyData.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSessionId(), getTimestamp(), getItemId(), getPrice(), getQuantity());
    }

    @Override
    public String toString() {
        return "BuyData{" +
                "sessionId=" + sessionId +
                ", timestamp=" + timestamp +
                ", itemId=" + itemId +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
