package com.example.shivrana.shivrana_comp304_assignment4;

public class Order {
    int orderId,itemId,customerId,amount;
    String deliveryDate,status;

    public Order(int orderId, int itemId, int customerId, int amount, String deliveryDate, String status) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.customerId = customerId;
        this.amount = amount;
        this.deliveryDate = deliveryDate;
        this.status = status;
    }

    public Order(){}

    public Order(int orderId, int itemId, int amount, String deliveryDate, String status) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.amount = amount;
        this.deliveryDate = deliveryDate;
        this.status = status;
    }

    public Order(Object itemAtPosition) {}

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("OrderId \t%21d\n ItemId \t%22d\n Amount \t%20d\n DeliveryDate \t%18s\n Status \t%28s",orderId,itemId,amount,deliveryDate,status);
    }
}
