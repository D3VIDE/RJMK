package com.example.projectbasisdata;
import java.sql.Connection;
import java.sql.SQLException;
public class testConnectionDB {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("Connection to the database was successful!");
                connection.close();  // Close the connection after use
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
