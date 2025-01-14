package com.example.airline_management_system_javafx;

import com.example.airline_management_system_javafx.database.Database;
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
                    login_button.getScene().getWindow().hide();
                    Parent parent = FXMLLoader.load(getClass().getResource("Dashboard-home.fxml"));
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



                    stage.initStyle(StageStyle.TRANSPARENT);
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
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void exit_button_on_action(ActionEvent event) {
        Stage stage = (Stage)exit_button.getScene().getWindow();
        stage.close();
    }
}