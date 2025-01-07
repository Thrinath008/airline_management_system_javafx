package com.example.airline_management_system_javafx.database;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Attempt to connect to the database
        Connection connection = Database.connectDb();

        // Check if the connection was successful
        if (connection != null) {
            System.out.println("Database connection successful!");
            try {
                // Close the connection after testing
                connection.close();
                System.out.println("Database connection closed.");
            } catch (Exception e) {
                System.out.println("Error closing the connection: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}

