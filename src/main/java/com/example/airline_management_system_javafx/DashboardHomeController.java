package com.example.airline_management_system_javafx;
import com.example.airline_management_system_javafx.database.Database;
import com.example.airline_management_system_javafx.database.FlightTabel;
import com.example.airline_management_system_javafx.database.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardHomeController implements Initializable {

    @FXML
    private TableView<FlightTabel> main_tabel;

    @FXML
    private TableColumn<?, ?> col_arrival;

    @FXML
    private TableColumn<?, ?> col_date;

    @FXML
    private TableColumn<?, ?> col_depart;

    @FXML
    private TableColumn<?, ?> col_fare;

    @FXML
    private TableColumn<?, ?> col_flight_number;

    @FXML
    private TableColumn<?, ?> col_from;

    @FXML
    private TableColumn<?, ?> col_to;

    @FXML
    private DatePicker date_field;

    @FXML
    private Button exit_button;

    @FXML
    private TextField from_textfield;

    @FXML
    private Button logout_button_;

    @FXML
    private AnchorPane main_AnchorPlane;

    @FXML
    private Button search_flights_button;

    @FXML
    private CheckBox senior_citizen_tick_box;

    @FXML
    private CheckBox student_tick_box;

    @FXML
    private TextField to_textfield;

    private LocalDate localDate;
    private String from_place_name;
    private String to_place_name;
    private ObservableList<FlightTabel> data;

    private static String flight_number_ticket;
    public String flight_from_ticket;
    public String flight_to_ticket;
    public String flight_date_ticket;
    public String flight_from_time_ticket;
    public String flight_to_time_ticket;
    public String flight_fare_ticket;

    @FXML
    public void logout_button() throws IOException {
        logout_button_.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("Login-screen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void exit(ActionEvent event) {
        Stage stage = (Stage) exit_button.getScene().getWindow();
        stage.close();
    }

    public void student_seniorcityzen(ActionEvent event) {
        if (student_tick_box.isSelected()) {
            senior_citizen_tick_box.setSelected(false);
        } else {
            System.out.println("not working");
        }
    }

    public void seniorcityzen(ActionEvent event) {
        if (senior_citizen_tick_box.isSelected()) {
            student_tick_box.setSelected(false);
        } else {
            System.out.println("not working");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        setCellValue();
        flight_data();
        main_tabel.setOnMouseClicked(e -> {
            FlightTabel selectedFlight = main_tabel.getSelectionModel().getSelectedItem();
            main_tabel.getScene().getWindow().hide();
            try {
                FXMLLoader parent = new FXMLLoader(BookingController.class.getResource("booking-screen.fxml"));
                Parent node = parent.load() ;
                BookingController bc = parent.getController();

                Stage stage = new Stage();
                Scene scene = new Scene(node);
//                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);

                // populate scene
                bc.setFlight(selectedFlight);

                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            flight_number_ticket = selectedFlight.getFlight_number_1();
            flight_from_ticket = selectedFlight.getFrom_1();
            flight_to_ticket = selectedFlight.getTo_1();
            flight_date_ticket = selectedFlight.getDate_1();
            flight_from_time_ticket = selectedFlight.getDepart_1();
            flight_to_time_ticket = selectedFlight.getArrival_1();
            flight_fare_ticket = selectedFlight.getFare_1();

            if (selectedFlight != null) {
                System.out.print("Selected Flight Details: ");
                System.out.print("Flight Number: " + selectedFlight.getFlight_number_1() + ", ");
                System.out.print("From: " + selectedFlight.getFrom_1() + ", ");
                System.out.print("To: " + selectedFlight.getTo_1() + ", ");
                System.out.print("Date: " + selectedFlight.getDate_1() + ", ");
                System.out.print("Departure Time: " + selectedFlight.getDepart_1() + ", ");
                System.out.print("Arrival Time: " + selectedFlight.getArrival_1() + ", ");
                System.out.print("Fare: " + selectedFlight.getFare_1());
            }
        });
    }

    @FXML
    public void searchFlights(ActionEvent event) {
        flight_data();
    }

    public void flight_data() {
        localDate = date_field.getValue();
        from_place_name = from_textfield.getText();
        to_place_name = to_textfield.getText();

        if (from_place_name.isEmpty() || to_place_name.isEmpty()) {
            System.out.println("Please fill in all fields to search for flights.");
            return;
        }

        // Fetch data using the Database class
        data = Database.getFlightData(from_place_name, to_place_name, localDate.toString());

        if (data.isEmpty()) {
            System.out.println("No flights found for the given criteria.");
        } else {
            System.out.println("Flights found: " + data.size());
        }

        main_tabel.setItems(data);
    }

    public void setCellValue() {
        col_flight_number.setCellValueFactory(new PropertyValueFactory<>("flight_number_1"));
        col_from.setCellValueFactory(new PropertyValueFactory<>("from_1"));
        col_to.setCellValueFactory(new PropertyValueFactory<>("to_1"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date_1"));
        col_depart.setCellValueFactory(new PropertyValueFactory<>("depart_1"));
        col_arrival.setCellValueFactory(new PropertyValueFactory<>("arrival_1"));
        col_fare.setCellValueFactory(new PropertyValueFactory<>("fare_1"));
    }

    public static void getTicketDetails() {
        System.out.println(flight_number_ticket);
     //   System.out.println(flight_from_ticket);
     //   System.out.println(flight_to_ticket);
     //   System.out.println(flight_date_ticket);
     //   System.out.println(flight_from_time_ticket);
     //   System.out.println(flight_to_time_ticket);
     //   System.out.println(flight_fare_ticket);
    }

    public String getflightnumber(String x) {
        x = flight_fare_ticket;
        return x;
    }
}
