package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;

public class Order {
    private int id;
    private Date date;
    private double amount;
    private int customerId;
    private String status;

    // Constructeur par défaut
    public Order() {}

    // Constructeur avec annotations sur les paramètres
    @JsonCreator
    public Order(@JsonProperty("id") int id,
                 @JsonProperty("date") @JsonFormat(pattern = "yyyy-MM-dd") Date date,
                 @JsonProperty("amount") double amount,
                 @JsonProperty("customer_id") int customerId,
                 @JsonProperty("status") String status) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.customerId = customerId;
        this.status = status;
    }

    // Getters et Setters avec les annotations JSON
    @JsonGetter("id")
    public int getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonGetter("date")
    public Date getDate() {
        return date;
    }

    @JsonSetter("date")
    public void setDate(Date date) {
        this.date = date;
    }

    @JsonGetter("amount")
    public double getAmount() {
        return amount;
    }

    @JsonSetter("amount")
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @JsonGetter("customer_id")
    public int getCustomerId() {
        return customerId;
    }

    @JsonSetter("customer_id")
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @JsonGetter("status")
    public String getStatus() {
        return status;
    }

    @JsonSetter("status")
    public void setStatus(String status) {
        this.status = status;
    }

    // Méthode pour convertir en JSON
    public String toJson() {
        return String.format("{\"id\": %d, \"date\": \"%s\", \"amount\": %.2f, \"customer_id\": %d, \"status\": \"%s\"}",
                id, date.toString(), amount, customerId, status);
    }
}
