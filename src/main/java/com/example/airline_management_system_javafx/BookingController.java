package com.example.airline_management_system_javafx;
import com.example.airline_management_system_javafx.database.FlightTabel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    @FXML
    private Label arrival_to_label;

    @FXML
    private Label basic_fare_label;

    @FXML
    private TextField contact_textfield;

    @FXML
    private Label date_label;

    @FXML
    private Label departure_from_label;

    @FXML
    private Button exit_button;

    @FXML
    private Label final_fare_label;

    @FXML
    private Label flight_number_label;

    @FXML
    private Label from_label;

    @FXML
    private Label from_time_label;

    @FXML
    private TextField full_name_textfield;

    @FXML
    private TextField pasport_textfield;

    @FXML
    private Label to_label;
    @FXML
    private Button back_to_flights;
    @FXML
    private Label flight_from_label;
    @FXML
    private Label flight_to_label;

    public void setFlight(FlightTabel selectedFlight){
        final_fare_label.setText( selectedFlight.getFare_1());
        flight_from_label.setText(selectedFlight.getFrom_1());
        flight_to_label.setText(selectedFlight.getTo_1());
    }

    @FXML
    private static Label to_time_label;

    @FXML
    public void exit(ActionEvent event){
        Stage stage = (Stage)exit_button.getScene().getWindow();
        stage.close();
    }
    public void saveAndPay(ActionEvent event) {

    }

    public void backToFlights(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    /*    String x = "";
        String flightNumber = DashboardHomeController.flight_fare_ticket; // Static method example
        String flightFrom = DashboardHomeController.flight_from_ticket;
        String flightTo = DashboardHomeController.flight_to_ticket;
        String flightDate = DashboardHomeController.flight_date_ticket;
        String departureTime = DashboardHomeController.flight_from_time_ticket;
        String arrivalTime = DashboardHomeController.flight_to_time_ticket;
        String fare = DashboardHomeController.flight_fare_ticket;
*/
//        flightNumberLabel.setText(flightNumber);
//        fromLabel.setText(flightFrom);
//        toLabel.setText(flightTo);
//        dateLabel.setText(flightDate);
//        departureTimeLabel.setText(departureTime);
//        arrivalTimeLabel.setText(arrivalTime);
//        final_fare_label.setText(flightNumber);

    }
}
