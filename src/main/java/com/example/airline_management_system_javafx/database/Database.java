package com.example.airline_management_system_javafx.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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
    public static ObservableList<FlightTabel> getFlightData(String from, String to, String date) {
        ObservableList<FlightTabel> data = FXCollections.observableArrayList();
        String sql = "SELECT * FROM flights_info_main WHERE departure_city = ? AND arrival_city = ? AND flight_date = ?";
        try (Connection connection = connectDb();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, from);
            preparedStatement.setString(2, to);
            preparedStatement.setString(3, date);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data.add(new FlightTabel(
                        resultSet.getString("flight_number"),
                        resultSet.getString("departure_city"),
                        resultSet.getString("arrival_city"),
                        resultSet.getString("flight_date"),
                        resultSet.getString("departure_time"),
                        resultSet.getString("arrival_time"),
                        resultSet.getString("fare")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching flight data: " + e.getMessage());
        }
        return data;
    }


}
