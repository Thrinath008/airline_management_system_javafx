package com.example.airline_management_system_javafx;
import com.example.airline_management_system_javafx.database.FlightTabel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.time.Duration;


import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    @FXML
    private Button saveAndPay_Button;
    @FXML
    private AnchorPane passangers_details_anchorpane;
    @FXML
    private Label to_time_label_ticket;
    @FXML
    private Label from_time_labe_ticket;
    @FXML
    private Label flight_number_label_ticket;
    @FXML
    private Label from_small_label;
    @FXML
    private Label to_small_label;
    @FXML
    private Label duration_of_flight;
    @FXML
    private Label name_in_ticket;
    @FXML
    private Label passport_in_ticket;
    @FXML
    private Label contact_in_ticket;
    @FXML
    private AnchorPane farePrice_anchorpane;


    private double xOffset = 0;
    private double yOffset = 0;


    public void setFlight(FlightTabel selectedFlight){
        final_fare_label.setText( selectedFlight.getFare_1());
        flight_from_label.setText(selectedFlight.getFrom_1());
        flight_to_label.setText(selectedFlight.getTo_1());
        to_time_label_ticket.setText(selectedFlight.getArrival_1());
        from_time_labe_ticket.setText(selectedFlight.getDepart_1());
        flight_number_label_ticket.setText(selectedFlight.getFlight_number_1());
        from_small_label.setText(selectedFlight.getFrom_1());
        to_small_label.setText(selectedFlight.getTo_1());
        String to_time = selectedFlight.getArrival_1();
        String from_time = selectedFlight.getDepart_1();
        String dur = total_flight_time(from_time,to_time);
        String basefare = basicFare(selectedFlight.getFare_1());
        basic_fare_label.setText(basefare);
        duration_of_flight.setText(dur);
    }
    @FXML
    public String total_flight_time(String from_time, String to_time){
        // Parse the strings into LocalTime objects
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime fromTime = LocalTime.parse(from_time, timeFormatter);
        LocalTime toTime = LocalTime.parse(to_time, timeFormatter);

        // Calculate the duration
        Duration duration = Duration.between(fromTime, toTime);

        // Handle negative duration (if toTime is earlier than fromTime)
        if (duration.isNegative()) {
            duration = duration.plusDays(1); // Assume times are on different days
        }

        // Convert the duration to hours, minutes, and seconds
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        // Create a new string in HH:mm:ss format
        String durationString = String.format("%02d hr %02d min", hours, minutes);
        return durationString;
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
    @FXML
    public String basicFare(String originalValue){

        // Extract the numeric part (remove the € symbol and trim spaces)
        double amount = Double.parseDouble(originalValue.replace("€", "").trim());

        // Subtract 12 from the amount
        double newAmount = amount - 12;

        // Format the new amount back into a string with the € symbol
        String newValue = String.format("€ %.2f", newAmount);
        return newValue;
    }

    public void backToFlights() throws IOException {
        back_to_flights.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("Dashboard-home.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        parent.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        parent.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    public void setticket_details(){

        String pasName = full_name_textfield.getText();
        String pasPassport = pasport_textfield.getText();
        String pasContact = contact_textfield.getText();

        name_in_ticket.setText(pasName);
        passport_in_ticket.setText(pasPassport);
        contact_in_ticket.setText(pasContact);
        passangers_details_anchorpane.setVisible(false);
        farePrice_anchorpane.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        full_name_textfield.setOnKeyPressed(e->{
            switch (e.getCode()){
                case ENTER -> pasport_textfield.requestFocus();
            }
        });
        pasport_textfield.setOnKeyPressed(e->{
            switch (e.getCode()){

                case ENTER -> contact_textfield.requestFocus();
            }
        });
        contact_textfield.setOnKeyPressed(e->{
            switch (e.getCode()){
                case ENTER -> saveAndPay_Button.fire();
            }
        });





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
