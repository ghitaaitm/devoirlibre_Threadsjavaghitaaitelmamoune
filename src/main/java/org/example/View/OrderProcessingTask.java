package org.example.View;

import org.example.controller.OrderController;
import org.example.DAO.Database;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderProcessingTask implements Runnable {
    private final Database database;
    private final String inputFile;
    private final String outputFile;
    private final String errorFile;

    public OrderProcessingTask(Database database, String inputFile, String outputFile, String errorFile) {
        this.database = database;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.errorFile = errorFile;
    }

    @Override
    public void run() {
        OrderController orderController = new OrderController(database);
        try {
            orderController.processOrders(inputFile, outputFile, errorFile);
        } catch (Exception e) {
            System.err.println("Erreur lors du traitement des commandes: " + e.getMessage());
        }

        System.out.println("Les commandes ont été traitées.");
    }

    public static void startProcessingEveryHour(Database database, String inputFile, String outputFile, String errorFile) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Tâche à exécuter toutes les heures
        scheduler.scheduleAtFixedRate(new OrderProcessingTask(database, inputFile, outputFile, errorFile), 0, 1, TimeUnit.HOURS);
    }

    public static void main(String[] args) {
        Database database = new Database();
        String inputFile = "data/input.json";
        String outputFile = "data/output.json";
        String errorFile = "data/error.json";

        startProcessingEveryHour(database, inputFile, outputFile, errorFile);
    }
}
