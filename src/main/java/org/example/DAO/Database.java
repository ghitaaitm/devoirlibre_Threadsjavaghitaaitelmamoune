package org.example.DAO;

import org.example.model.Customer;
import org.example.model.Order;

import java.sql.*;

public class Database {
    private Connection conn;

    public Database() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoppy", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer findCustomerById(int customerId) {
        try {
            String query = "SELECT * FROM customer WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si le client n'est pas trouvé
    }
    public void insertOrder(Order order) {
        try {
            String query = "INSERT INTO `order` (date, amount, customer_id, status) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            // Convertir java.util.Date en java.sql.Date
            stmt.setDate(1, new java.sql.Date(order.getDate().getTime()));
            stmt.setDouble(2, order.getAmount());
            stmt.setInt(3, order.getCustomerId());
            stmt.setString(4, order.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
