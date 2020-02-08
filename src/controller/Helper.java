package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Helper {
    public static void returnToMainScreen(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent scene;
        // return to main menu
        // build stage
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();

        // load add part view
        scene = FXMLLoader.load(Helper.class.getResource("/view/MainScreen.fxml"));

        // add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();
    }

    public static void goToModifyPartScreen(ActionEvent actionEvent, FXMLLoader loader) throws IOException {
        Stage stage;
        Parent scene;
        // return to main menu
        // build stage
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();

        // load add part view
        scene = loader.getRoot();

        // add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();
    }

    public static void goToModifyProductScreen(ActionEvent actionEvent, FXMLLoader loader) throws IOException {
        Stage stage;
        Parent scene;
        // return to main menu
        // build stage
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();

        // load add product view
        scene = loader.getRoot();

        // add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();
    }

    public static void goToAddPartScreen(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent scene;
        // return to main menu
        // build stage
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();

        // load add part view
        scene = FXMLLoader.load(Helper.class.getResource("/view/AddPartScreen.fxml"));

        // add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();
    }

    public static void goToAddProductScreen(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent scene;
        // return to main menu
        // build stage
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();

        // load add part view
        scene = FXMLLoader.load(Helper.class.getResource("/view/AddProductScreen.fxml"));

        // add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();
    }

    public static void alert(String alertType, String alertTitle, String message) {
        Alert alert = new Alert(Alert.AlertType.valueOf(alertType));
        alert.setTitle(alertTitle);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


