package org.example.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
    private int id;
    private Timestamp date;
    private BigDecimal amount;
    private int customerId;

    public Order(int id, Timestamp date, BigDecimal amount, int customerId) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.customerId = customerId;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
