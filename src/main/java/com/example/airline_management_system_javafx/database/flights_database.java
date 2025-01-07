package com.example.airline_management_system_javafx.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class flights_database {


    public static List<String> flights_data(String from, String to,String date){
        List<String> flights = new ArrayList<>();
        final String URL = "jdbc:mysql://localhost:3306/airline_management_system_javafx";
        final String USER = "root";
        final String PASSWORD = "CHATgpt@project";
        String query = "SELECT flight_number, departure_time, arrival_time FROM flights " +
                "WHERE departure_city = ? AND arrival_city = ? AND flight_date = ?";

        try{
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,from);
            preparedStatement.setString(2,to);
            preparedStatement.setString(1,date);


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String flight = "Flight: " + resultSet.getString("flight_number") +
                        ", Departure: " + resultSet.getString("departure_time") +
                        ", Arrival: " + resultSet.getString("arrival_time");
                flights.add(flight);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flights;
    }
    public static void main(String[] args) {
        return;

    }

}
