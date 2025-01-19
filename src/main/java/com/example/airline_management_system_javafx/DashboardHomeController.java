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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardHomeController implements Initializable {

    @FXML
    private Button search_flights_button_side_anchorpane;

    @FXML
    private AnchorPane book_flights_anchorpane;
    @FXML
    private Button search_flights_button;

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
    private CheckBox senior_citizen_tick_box;

    @FXML
    private CheckBox student_tick_box;
    @FXML
    private Label helloname_label;

    @FXML
    private TextField to_textfield;
    @FXML
    private AnchorPane settings_anchorpane;
    @FXML
    private Button eidt_profile_button;
    @FXML
    private Button settings_button;
    @FXML
    private Button chnage_theme_button;
    @FXML
    private Button update_profile_save_changes_button;
    @FXML
    private Button clean_textfield_button;
    @FXML
    private Button delete_account_button;
    @FXML
    private Button report_bug_button;
    @FXML
    private TextField update_confirm_password_textfield;
    @FXML
    private TextField update_password_textfield;
    @FXML
    private TextField upadte_name_field;
    @FXML
    private AnchorPane edit_profile_anchorpane;
    @FXML
    public Label name_label_home;

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
    private static String email;
    @FXML
    private int id1;
    private String name1;
    private String password1;
    private String email1;

    @FXML
    private void setBook_flights_anchorpane(){
        book_flights_anchorpane.setVisible(true);
        settings_anchorpane.setVisible(false);
    }
    @FXML
    private void setSettings_anchorpane(){
        book_flights_anchorpane.setVisible(false);
        settings_anchorpane.setVisible(true);
    }
    @FXML
    public void logout_button() throws IOException {
        logout_button_.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("Login-screen.fxml"));
        Pane root = fxmlLoader.load();
        Stage stage = new Stage();
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
    @FXML
    public String getemailfromlogin(String email){
        this.email = email;
        System.out.println("helooooooooooooooooooooooooooooooooooo we got the emaillllll   "+email);
        return email;
    }
    @FXML
    public void getpassengerInfo(String email){
        ObservableList<passengers> passengerList = Database.getpassengerbyemail(email);
        if (!passengerList.isEmpty()) {
            // Assuming only one passenger is fetched (unique email)
            passengers passenger = passengerList.get(0);

            // Store data in variables
            this.id1 = passenger.getId();
            this.name1 = passenger.getName();
            this.password1 = passenger.getPassword();
            this.email1 = passenger.getEmail();

            // Display or use the stored data
            System.out.println("Passenger details loaded:");
            System.out.println("ID: " + id1);
            System.out.println("Name: " + name1);
            System.out.println("Password: " + password1);
            System.out.println("Email: " + email1);
            helloname_label.setText(passenger.getName());
        } else {
            System.out.println("No passenger found with the given email.");
        }
    }
    @FXML
    public void setHelloname_label(String s){
        helloname_label.setText(s);
        name1 = s;
    }
    public String getname(String n){
        this.name1 = n;
        System.out.println("this is form getname "+name1);

        return name1;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(name1+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");


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
        System.out.println("this is s from controller "+name1);
        data = FXCollections.observableArrayList();
        setCellValue();
        flight_data();
        main_tabel.setOnMouseClicked(e -> {
            FlightTabel selectedFlight = main_tabel.getSelectionModel().getSelectedItem();

            if (selectedFlight == null) {
                System.out.println("No flight selected. Please select a valid flight.");
                return;
            }
            Stage mainStageDashboard = (Stage)main_tabel.getScene().getWindow();
            mainStageDashboard.hide();
            try {
                FXMLLoader parent = new FXMLLoader(BookingController.class.getResource("booking-screen.fxml"));
                Parent node = parent.load() ;
                BookingController bc = parent.getController();
                bc.backToFlights(mainStageDashboard);

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

}
