package org.example.View;

import org.example.DAO.Database;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        String inputFile = "data/input.json";
        String outputFile = "data/output.json";
        String errorFile = "data/error.json";

        // Démarrer le traitement des commandes toutes les heures
        OrderProcessingTask.startProcessingEveryHour(database, inputFile, outputFile, errorFile);
    }
}
