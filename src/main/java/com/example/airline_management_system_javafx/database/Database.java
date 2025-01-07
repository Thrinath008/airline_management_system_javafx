package com.example.airline_management_system_javafx.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/airline_management_system_javafx";
    private static final String USER = "root";
    private static final String PASSWORD = "CHATgpt@project";

    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Explicitly load the driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return null;
        }
    }
    public static void main(String[] args) {
        Connection connection = connectDb();
        if (connection != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Failed to connect.");
        }
    }


}
