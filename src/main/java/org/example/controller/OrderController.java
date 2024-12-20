package org.example.controller;

import org.example.model.Order;
import org.example.model.Customer;
import org.example.DAO.Database;
import org.example.Utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private final Database database;

    public OrderController(Database database) {
        this.database = database;
    }

    public void processOrders(String inputFilePath, String outputFilePath, String errorFilePath) {
        try {
            List<Order> orders = FileUtils.readOrdersFromFile(inputFilePath);
            List<Order> validOrders = new ArrayList<>();
            List<Order> invalidOrders = new ArrayList<>();

            for (Order order : orders) {
                Customer customer = database.findCustomerById(order.getCustomerId());

                if (customer != null) {
                    database.insertOrder(order);
                    validOrders.add(order);
                } else {
                    invalidOrders.add(order);
                }
            }

            if (!validOrders.isEmpty()) {
                FileUtils.writeOrdersToFile(outputFilePath, validOrders);
            }

            if (!invalidOrders.isEmpty()) {
                FileUtils.writeOrdersToFile(errorFilePath, invalidOrders);
            }

            // Nettoyer le fichier d'entrée
            FileUtils.clearFile(inputFilePath);
        } catch (Exception e) {
            System.err.println("Une erreur s'est produite lors du traitement des commandes : " + e.getMessage());
        }
    }
}
