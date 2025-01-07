package com.example.airline_management_system_javafx;
import com.example.airline_management_system_javafx.database.Database;
import com.example.airline_management_system_javafx.database.FlightTabel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardHomeController implements Initializable {

    @FXML
    private TableView<FlightTabel> main_tabel;

    @FXML
    private TableColumn<?,?> col_arrival;

    @FXML
    private TableColumn<?,?> col_date;

    @FXML
    private TableColumn<?,?> col_depart;

    @FXML
    private TableColumn<?,?> col_fare;

    @FXML
    private TableColumn<?,?> col_flight_number;

    @FXML
    private TableColumn<?,?> col_from;

    @FXML
    private TableColumn<?,?> col_to;

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


    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;



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
    public void exit(ActionEvent event){
        Stage stage = (Stage)exit_button.getScene().getWindow();
        stage.close();
    }
    public void student_seniorcityzen(ActionEvent event){
        if (student_tick_box.isSelected()){
            senior_citizen_tick_box.setSelected(false);
        }else {
            System.out.println("not working");
        }
    }
    public void seniorcityzen(ActionEvent event){
        if (senior_citizen_tick_box.isSelected()){
            student_tick_box.setSelected(false);
        }else {
            System.out.println("not working");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = Database.connectDb();
        data = FXCollections.observableArrayList();
        setCellValue();
        flight_data();
        main_tabel.setOnMouseClicked((e->{
            FlightTabel selectedFlight = main_tabel.getSelectionModel().getSelectedItem();
            if (selectedFlight != null){
                System.out.print("Selected Flight Details: ");
                System.out.print("Flight Number: " + selectedFlight.getFlight_number_1() + ", ");
                System.out.print("From: " + selectedFlight.getFrom_1() + ", ");
                System.out.print("To: " + selectedFlight.getTo_1() + ", ");
                System.out.print("Date: " + selectedFlight.getDate_1() + ", ");
                System.out.print("Departure Time: " + selectedFlight.getDepart_1() + ", ");
                System.out.print("Arrival Time: " + selectedFlight.getArrival_1() + ", ");
                System.out.print("Fare: " + selectedFlight.getFare_1());
            }
        }));


    }

    @FXML
    public void searchFlights(ActionEvent event) {
        flight_data();
    }

    public void flight_data(){
        localDate = date_field.getValue();
        from_place_name = from_textfield.getText();
        to_place_name = to_textfield.getText();

        data.clear();

        if (from_place_name.isEmpty()|| to_place_name.isEmpty()){
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Input Missing");
//            alert.setHeaderText(null);
//            alert.setContentText("Please fill in all fields to search for flights.");
//            alert.show();
            return;
        }
        try {
            //String slq = "SELECT flight_number, departure_city, arrival_city, flight_date, departure_time, arrival_time, fare" + "FROM flights_info_main" + "WHERE departure_city = ? AND arrival_city = ? AND flight_date = ?";
            String sql = "SELECT * FROM flights_info_main WHERE departure_city = ? AND arrival_city = ? AND flight_date = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, from_place_name);
            preparedStatement.setString(2, to_place_name);
            preparedStatement.setString(3, localDate.toString()); // Convert LocalDate to String

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                data.add(new FlightTabel(resultSet.getString("flight_number"),
                        resultSet.getString("departure_city"),
                        resultSet.getString("arrival_city"),
                        resultSet.getString("flight_date"),
                        resultSet.getString("departure_time"),
                        resultSet.getString("arrival_time"),
                        resultSet.getString("fare")));
            }

            main_tabel.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        main_tabel.setItems(data);
        System.out.println("connected to data base ");
        System.out.println(localDate);
        System.out.println(from_place_name);
        System.out.println(to_place_name);

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
}
