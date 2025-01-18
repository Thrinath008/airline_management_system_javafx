package com.example.airline_management_system_javafx;
import com.example.airline_management_system_javafx.database.FlightTabel;
import com.itextpdf.kernel.pdf.PdfOutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.time.Duration;


import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;



import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import javafx.application.Application;
import javafx.scene.Scene;
import java.nio.ByteBuffer;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
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

    private Stage parentStage;

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
    @FXML
    private AnchorPane confirmbooking_download_anchorpane;
    @FXML
    private Button download_ticket_button;

    @FXML
    private HBox first_Hbox_booking;
    @FXML
    private AnchorPane main_ticket_anchorpane;

    private double xOffset = 0;
    private double yOffset = 0;
    List<String> imagePaths = Arrays.asList(
            "C:/Users/lakshmi ramesh/IdeaProjects/airline_management_system_javafx/src/main/resources/images/1.png",
            "C:/Users/lakshmi ramesh/IdeaProjects/airline_management_system_javafx/src/main/resources/images/2.png"
    );


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
        first_Hbox_booking.setVisible(false);
        confirmbooking_download_anchorpane.setVisible(true);
    }

    public void setParentscreen(Stage stage){
        this.parentStage = stage;
    }

    public void createPdf(AnchorPane anchorPane, String pdfPath,List<String> imagePaths) throws IOException {
        // Take a snapshot of the AnchorPane
        WritableImage writableImage = new WritableImage((int) anchorPane.getWidth(), (int) anchorPane.getHeight());
        anchorPane.snapshot(new SnapshotParameters(), writableImage);

        // Convert the WritableImage to a BufferedImage
        BufferedImage bufferedImage = convertWritableImageToBufferedImage(writableImage);

        // Save the BufferedImage to a temporary PNG file
        File tempImageFile = new File("temp_anchorPaneSnapshot.png");
        ImageIO.write(bufferedImage, "png", tempImageFile);

        // Write the image to a PDF
        PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Add the image to the PDF
        ImageData imageData = ImageDataFactory.create(tempImageFile.getAbsolutePath());
        Image pdfImage = new Image(imageData);
        document.add(pdfImage);
        document.add(new com.itextpdf.layout.element.Paragraph("\n").setMarginTop(20));

        for (String imagePath : imagePaths) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                ImageData additionalImageData = ImageDataFactory.create(imageFile.getAbsolutePath());
                Image additionalImage = new Image(additionalImageData);

                // Scale the image to fit the page width (if necessary)
                additionalImage.setAutoScale(true);

                // Add the additional image to the document
                document.add(additionalImage);
            } else {
                System.out.println("Image not found: " + imagePath);
            }
        }
        // Close the document
        document.close();

        // Delete the temporary image file
        tempImageFile.delete();

        System.out.println("PDF created at: " + pdfPath);
    }

    private BufferedImage convertWritableImageToBufferedImage(WritableImage writableImage) {
        int width = (int) writableImage.getWidth();
        int height = (int) writableImage.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        PixelReader pixelReader = writableImage.getPixelReader();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bufferedImage.setRGB(x, y, pixelReader.getArgb(x, y));
            }
        }
        return bufferedImage;
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
        download_ticket_button.setOnAction(e->{
            try {
                // Use FileChooser to select the save location
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save PDF");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

                // Show the save dialog
                File file = fileChooser.showSaveDialog(parentStage);
                if (file != null) {
                    createPdf(main_ticket_anchorpane, file.getAbsolutePath(), imagePaths); // Pass the image paths
                }
            } catch (IOException ex) {
                ex.printStackTrace();
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
