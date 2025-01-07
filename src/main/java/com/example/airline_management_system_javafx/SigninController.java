package com.example.airline_management_system_javafx;


import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SigninController implements Initializable {

    @FXML
    private Button back_button;

    @FXML
    private Button help_button;

    @FXML
    private AnchorPane login_dashboard;

    @FXML
    private AnchorPane main_anchorplane;

    @FXML
    private Button signin_button;

    @FXML
    private PasswordField signin_confirm_password;

    @FXML
    private TextField signin_email;

    @FXML
    private TextField signin_name;

    @FXML
    private PasswordField signin_password;


    public void signin_button_on_action(ActionEvent event) throws SQLException {
        Alert alert;

        String name = signin_name.getText();
        String email = signin_email.getText();
        String password = signin_password.getText();
        String confirmPassword = signin_confirm_password.getText();
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "error", "fill all the blanks");
            return;
        }
        final String URL = "jdbc:mysql://localhost:3306/airline_management_system_javafx";
        final String USER = "root";
        final String PASSWORD = "CHATgpt@project";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String insertSql = "INSERT INTO passengers(name,email,password)VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int rowsinserted = preparedStatement.executeUpdate();

            if (rowsinserted > 0) {
                showAlert(Alert.AlertType.CONFIRMATION, "success", "created");
                back_to_login();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new SQLException();
        }

    }


    @FXML
    public void back_to_login() throws IOException {
        back_button.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("Login-screen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

