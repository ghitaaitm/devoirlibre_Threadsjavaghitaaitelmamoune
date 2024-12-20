package org.example.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Order;

import java.io.*;
import java.util.List;

public class FileUtils {

    public static List<Order> readOrdersFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException("Le fichier spécifié n'existe pas : " + filePath);
        }

        if (file.length() == 0) {
            throw new IOException("Le fichier est vide : " + filePath);
        }

        try {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Order.class));
        } catch (IOException e) {
            throw new IOException("Erreur lors de la lecture du fichier JSON : " + filePath, e);
        }
    }

    public static void writeOrdersToFile(String filePath, List<Order> orders) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);

        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            objectMapper.writeValue(writer, orders);
        } catch (IOException e) {
            throw new IOException("Erreur lors de l'écriture dans le fichier : " + filePath, e);
        }
    }


    public static void clearFile(String filePath) throws IOException {
        File file = new File(filePath);

        if (file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("");
            } catch (IOException e) {
                throw new IOException("Erreur lors de la suppression du contenu du fichier : " + filePath, e);
            }
        } else {
            throw new FileNotFoundException("Le fichier spécifié n'existe pas : " + filePath);
        }
    }
}
