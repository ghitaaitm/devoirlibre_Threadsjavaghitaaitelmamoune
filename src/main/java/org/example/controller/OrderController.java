package org.example.controller;

import com.google.gson.*;
import org.example.model.DatabaseConnection;
import org.example.View.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderController {

    private final String inputFilePath = "data/input.json";
    private final String outputFilePath = "data/output.json";
    private final String errorFilePath = "data/error.json";
    private final ExecutorService executor = Executors.newFixedThreadPool(5); // Pool de threads avec 5 threads

    public void processOrders() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Lire les commandes depuis le fichier input.json
            String inputData = Files.readString(Paths.get(inputFilePath));
            JsonArray orders = JsonParser.parseString(inputData).getAsJsonArray();

            JsonArray outputOrders = new JsonArray();
            JsonArray errorOrders = new JsonArray();

            for (JsonElement orderElement : orders) {
                JsonObject order = orderElement.getAsJsonObject();

                // Soumettre chaque commande à un thread
                executor.submit(() -> processSingleOrder(conn, order, outputOrders, errorOrders));
            }

            // Attendre la fin de tous les threads
            executor.shutdown();
            while (!executor.isTerminated()) {
                Thread.sleep(100); // Pause pour éviter une boucle infinie
            }

            // Sauvegarder les commandes traitées et en erreur
            saveJsonToFile(outputOrders, outputFilePath);
            saveJsonToFile(errorOrders, errorFilePath);

            Logger.log("Traitement terminé avec succès.");
        } catch (Exception e) {
            Logger.log("Erreur lors du traitement : " + e.getMessage());
        }
    }

    private void processSingleOrder(Connection conn, JsonObject order, JsonArray outputOrders, JsonArray errorOrders) {
        try {
            int customerId = order.get("customer_id").getAsInt();

            if (customerExists(conn, customerId)) {
                // Ajouter la commande à la base de données
                addOrderToDatabase(conn, order);
                synchronized (outputOrders) {
                    outputOrders.add(order); // Ajout synchronisé pour éviter les conflits
                }
            } else {
                synchronized (errorOrders) {
                    errorOrders.add(order); // Ajout synchronisé pour éviter les conflits
                }
            }
        } catch (Exception e) {
            Logger.log("Erreur lors du traitement d'une commande : " + e.getMessage());
        }
    }

    private boolean customerExists(Connection conn, int customerId) throws SQLException {
        String query = "SELECT COUNT(*) FROM customer WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private void addOrderToDatabase(Connection conn, JsonObject order) throws SQLException {
        String query = "INSERT INTO `order` (date, amount, customer_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(order.get("date").getAsString()));
            stmt.setBigDecimal(2, order.get("amount").getAsBigDecimal());
            stmt.setInt(3, order.get("customer_id").getAsInt());
            stmt.executeUpdate();
        }
    }

    private void saveJsonToFile(JsonArray jsonArray, String filePath) throws IOException {
        Files.writeString(Paths.get(filePath), new Gson().toJson(jsonArray), StandardOpenOption.CREATE);
    }
}
