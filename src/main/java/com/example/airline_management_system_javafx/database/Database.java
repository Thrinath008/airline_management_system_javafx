package com.example.airline_management_system_javafx.database;
import com.example.airline_management_system_javafx.passengers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.airline_management_system_javafx.LogEntry;

import java.sql.*;
import java.time.format.DateTimeFormatter;

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
            Database.logAction("searched flight","the flight "+from+" to "+to+" has searched on "+ date);
        } catch (SQLException e) {
            System.out.println("Error fetching flight data: " + e.getMessage());
        }
        return data;
    }

    public static ObservableList<FlightTabel> getFlightDatatoAdmin() {
        ObservableList<FlightTabel> data = FXCollections.observableArrayList();
        String sql = "SELECT * FROM flights_info_main";
        try (Connection connection = connectDb();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

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

    public static void logAction(String actionType, String description){
        String query = "INSERT INTO activity_log (action_type, description) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connectDb().prepareStatement(query)){
            preparedStatement.setString(1,actionType);
            preparedStatement.setString(2,description);
            preparedStatement.executeUpdate();
            System.out.println("action logged: "+actionType+"- "+description);
        } catch (SQLException e) {
            System.out.println("error while loged action "+e.getMessage());
        }
    }
    public static ObservableList<LogEntry> getLogEntry(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        ObservableList<LogEntry> data = FXCollections.observableArrayList();
        try (Statement statement = connectDb().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM activity_log ORDER BY timestamp DESC");

            while (resultSet.next()){
                String formattedTimestamp = resultSet.getTimestamp("timestamp").toLocalDateTime().format(formatter);
                data.add(new LogEntry(resultSet.getString("action_type"),
                        resultSet.getString("description"),formattedTimestamp));
            }
            connectDb().close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
    public static ObservableList<passengers> getpassengerbyemail(String email) {
        String sql = "SELECT * FROM passengers WHERE email = ?";
        ObservableList<passengers> passengersInfo = FXCollections.observableArrayList();

        try (Connection connection = connectDb();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Add each passenger to the ObservableList
                passengersInfo.add(new passengers(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching passenger data", e);
        }

        return passengersInfo;
    }



}
