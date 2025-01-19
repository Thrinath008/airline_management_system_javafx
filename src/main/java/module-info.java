module com.example.airline_management_system_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires java.sql;
//    requires mysql.connector.j;
    requires layout;
    requires kernel;
    requires io;
    requires java.desktop;
    requires com.google.gson;

    opens com.example.airline_management_system_javafx to javafx.fxml;
    exports com.example.airline_management_system_javafx;
    exports com.example.airline_management_system_javafx.database;
    opens com.example.airline_management_system_javafx.database to javafx.fxml;
}