package com.example.courierapp;

public class ClassOrder {

    public int idOrders;
    public String description;
    public String orderLocation;
    public double amount;

    public ClassOrder(String description, String orderLocation, int id, double amount)
    {
        this.description = description;
        this.orderLocation = orderLocation;
        this.idOrders = id;
        this.amount = amount;

    }

    public String getDescription() {

        return description;
    }

    public String getOrderLocation() {

        return orderLocation;
    }

    public int getIdOrders() {
        return idOrders;
    }

    public double getAmount() {
        return amount;
    }
}

