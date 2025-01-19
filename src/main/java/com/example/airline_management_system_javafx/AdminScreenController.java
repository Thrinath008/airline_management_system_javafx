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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminScreenController implements Initializable {

    @FXML
    private AnchorPane book_flights_anchorpane;
    @FXML
    private AnchorPane Logs_anchorpane;
    @FXML
    private TableView<LogEntry> Log_tableview;
    @FXML
    private TableColumn<?,?> title_log_col;
    @FXML
    private TableColumn<?,?> description_log_col;
    @FXML
    private TableColumn<?,?> time_log_col;
    @FXML
    private Button logs_button;
    @FXML
    private Button add_flights_button;
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
    private TableView<FlightTabel> main_tabel;

    @FXML
    private Button search_flights_button;

    @FXML
    private Button search_flights_button_side_anchorpane;

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

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) exit_button.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void setlogcolValue(){
        time_log_col.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        description_log_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        title_log_col.setCellValueFactory(new PropertyValueFactory<>("actionType"));
    }

    @FXML
    void logout_button(ActionEvent e) throws IOException {
        logout_button_.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("Login-screen.fxml"));
        Pane root = fxmlLoader.load();
        Stage stage =new Stage();
        root.getStylesheets().add("login.css");
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void searchFlights(ActionEvent event) {
        flight_data();
    }
    @FXML
    public void setLogs_anchorpane(){
        book_flights_anchorpane.setVisible(false);
        Logs_anchorpane.setVisible(true);
    }
    @FXML
    public void setBook_flights_anchorpane(){
        book_flights_anchorpane.setVisible(true);
        Logs_anchorpane.setVisible(false);
    }
    @FXML
    void seniorcityzen(ActionEvent event) {
        if (student_tick_box.isSelected()) {
            senior_citizen_tick_box.setSelected(false);
        } else {
            System.out.println("not working");
        }
    }
    @FXML
    void student_seniorcityzen(ActionEvent event) {
        if (senior_citizen_tick_box.isSelected()) {
            student_tick_box.setSelected(false);
        } else {
            System.out.println("not working");
        }
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        from_textfield.setOnKeyPressed(e->{
            switch (e.getCode()){
                case ENTER -> to_textfield.requestFocus();
            }
        });
        to_textfield.setOnKeyPressed(e->{
            switch (e.getCode()){
                case ENTER -> date_field.requestFocus();
            }
        });
        setlogcolValue();
        ObservableList<LogEntry> logsData = Database.getLogEntry();
        Log_tableview.setItems(logsData);
        data = FXCollections.observableArrayList();
        setCellValue();
        flight_data();
        main_tabel.setOnMouseClicked(e -> {
            FlightTabel selectedFlight = main_tabel.getSelectionModel().getSelectedItem();

            if (selectedFlight == null) {
                System.out.println("No flight selected. Please select a valid flight.");
                return;
            }
            Stage mainstage = (Stage)main_tabel.getScene().getWindow();
            mainstage.hide();
            try {
                FXMLLoader parent = new FXMLLoader(BookingController.class.getResource("booking-screen.fxml"));
                Parent node = parent.load() ;
                BookingController bc = parent.getController();
                bc.backToFlights(mainstage);

                Stage stage = new Stage();
                Scene scene = new Scene(node);

                node.setOnMousePressed(event -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });

                node.setOnMouseDragged(event -> {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                });

                stage.initStyle(StageStyle.UNDECORATED);
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
}
