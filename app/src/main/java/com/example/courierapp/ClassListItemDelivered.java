package com.example.courierapp;

public class ClassListItemDelivered {

    public String description;
    public String deliveryDate;
    public float amount;
    public String orderLocation;


    public ClassListItemDelivered(String description, String deliveryDate, float amount, String orderLocation) {
        this.description = description;
        this.deliveryDate = deliveryDate;
        this.amount = amount;
        this.orderLocation = orderLocation;
    }

    public String getDescription() {
        return description;
    }

    public String getOrderLocation() {
        return orderLocation;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public float getAmount() {
        return amount;
    }
}
