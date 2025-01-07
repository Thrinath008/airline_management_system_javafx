package com.example.airline_management_system_javafx.database;

public class FlightTabel {
    private String flight_number_1;
    private String from_1;
    private String to_1;
    private String date_1;
    private String depart_1;
    private String arrival_1;
    private String fare_1;

    // Constructor
    public FlightTabel(String flight_number_1, String from_1, String to_1, String date_1, String depart_1, String arrival_1, String fare_1) {
        this.flight_number_1 = flight_number_1;
        this.from_1 = from_1;
        this.to_1 = to_1;
        this.date_1 = date_1;
        this.depart_1 = depart_1;
        this.arrival_1 = arrival_1;
        this.fare_1 = fare_1;
    }

    // Getter methods
    public String getFlight_number_1() {
        return flight_number_1;
    }

    public String getFrom_1() {
        return from_1;
    }

    public String getTo_1() {
        return to_1;
    }

    public String getDate_1() {
        return date_1;
    }

    public String getDepart_1() {
        return depart_1;
    }

    public String getArrival_1() {
        return arrival_1;
    }

    public String getFare_1() {
        return fare_1;
    }
}
