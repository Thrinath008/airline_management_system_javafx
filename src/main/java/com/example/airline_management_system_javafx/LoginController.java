package com.example.airline_management_system_javafx;

import com.example.airline_management_system_javafx.database.Database;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button login_button;

    @FXML
    private AnchorPane login_dashboard;
    @FXML
    private Button exit_button;
    @FXML
    private TextField login_textfield;
    @FXML
    private ComboBox<String> rolePicker_combobox;

    @FXML
    private AnchorPane main_anchorplane;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signup_button;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private double X = 0;
    private  double Y = 0;


    public void loginPassengers(){
        String sql = "SELECT * FROM passengers where email = ? and password = ?";
        String dashboard = "Dashboard-home.fxml";
        String admin = "admin-home-screen.fxml";
        String screen;
        String selectedRole = rolePicker_combobox.getSelectionModel().getSelectedItem();
        if ("Admin".equals(selectedRole)){
            screen = admin;
        }else {
            screen = dashboard;
        }
        connection = Database.connectDb();

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,login_textfield.getText());
            preparedStatement.setString(2,password_field.getText());

            resultSet = preparedStatement.executeQuery();
            Alert alert;

            if (login_textfield.getText().isEmpty()|| password_field.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setHeaderText(null);
                alert.setContentText("fill all the fields");
                alert.showAndWait();
                String x = login_button.getText();
                System.out.println(x);
            }else {
                if (resultSet.next()){
                    Database.logAction("Login",login_textfield.getText()+" has loged in successfully");
                    login_button.getScene().getWindow().hide();
                    Parent parent = FXMLLoader.load(getClass().getResource(screen));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    parent.setOnMousePressed(mouseEvent -> {
                        X = mouseEvent.getSceneX();
                        Y = mouseEvent.getSceneY();
                    });
                    parent.setOnMouseDragged(mouseEvent -> {
                        stage.setX(mouseEvent.getScreenX() - X);
                        stage.setY(mouseEvent.getScreenY() - Y);
                    });
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(scene);
                    stage.show();
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("success");
                    alert.setHeaderText(null);

                    alert.setContentText("Login successful");
                    PauseTransition delay = new PauseTransition(Duration.seconds(3));
                    delay.setOnFinished(event -> alert.close()); // Close the alert after 3 seconds

                    alert.show();
                    delay.play();
                }
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("error");
                    alert.setHeaderText(null);
                    alert.setContentText("incorrect login details, try signing up..");
                    alert.showAndWait();

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    public void signup() throws IOException {
        signup_button.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("Signin-screen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        parent.setOnMousePressed(event -> {
            X = event.getSceneX();
            Y = event.getSceneY();
        });

        parent.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - X);
            stage.setY(event.getScreenY() - Y);
        });
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rolePicker_combobox.setOnKeyPressed(e->{
            switch (e.getCode()){
                case ENTER -> login_textfield.requestFocus();
            }
        });
        login_textfield.setOnKeyPressed(e->{
            switch (e.getCode()){
                case ENTER -> password_field.requestFocus();
            }
        });
        password_field.setOnKeyPressed(e->{
            switch (e.getCode()){

                case ENTER -> login_button.fire();
            }
        });
        String []items = {"Passenger","Admin"};
        rolePicker_combobox.getItems().addAll(items);
    }

    public void exit_button_on_action(ActionEvent event) {
        Stage stage = (Stage)exit_button.getScene().getWindow();
        stage.close();
    }

    public void setAdminScreen(ActionEvent e) throws IOException {
        if (rolePicker_combobox.getSelectionModel().getSelectedItem().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Empty fields","Select the role");
            return;
        }
        String selectedRole = rolePicker_combobox.getSelectionModel().getSelectedItem();
        if ("Admin".equals(selectedRole)){
            FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("admin-home-screen.fxml"));
            Stage stage = new Stage();
            Pane root = fxmlLoader.load();
            root.setOnMousePressed(event -> {
                X = event.getSceneX();
                Y = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - X);
                stage.setY(event.getScreenY() - Y);
            });
            // Display the home screen
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("booking.css").toExternalForm());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        }else {
            FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("Dashboard-home.fxml"));
            Stage stage = new Stage();
            Pane root = fxmlLoader.load();
            root.setOnMousePressed(event -> {
                X = event.getSceneX();
                Y = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - X);
                stage.setY(event.getScreenY() - Y);
            });
            // Display the home screen
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("booking.css").toExternalForm());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();

        }

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.show();
    }
}