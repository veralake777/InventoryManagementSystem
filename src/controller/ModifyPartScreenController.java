package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class ModifyPartScreenController {
    Part partToModify;
    Stage stage;
    Parent scene;

    /////
    // RUBIRC:H.  Add the following functionality to the part screens, using the methods provided in the attached “UML Class Diagram”:
    //     2.  “Modify Part” screen
    //
    //          •  select “In-House” or “Outsourced”
    //
    //          •  modify or change data values
    //
    //          •  save modifications to the data and then redirect to the main screen
    //
    //          •  cancel or exit out of this screen and go back to the main screen
    /////

    // Event Handlers
    private boolean isInHouse;
    @FXML
    private Label modifyPartCompanyNameTxt;

    public void receivePart(Part part) {
        if(part instanceof InHouse){
            inHouseRadio.setSelected(true);
            modifyPartCompanyNameTxt.setText("Machine ID");
            partCompanyNameTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else {
            outsourcedRadio.setSelected(true);
            partCompanyNameTxt.setPromptText("Company Name");
            partCompanyNameTxt.setText(String.valueOf(((Outsourced) part).getCompanyName()));
        }
        partIdTxt.setText(String.valueOf(part.getId()));
        partNameTxt.setText(part.getName());
        partPriceTxt.setText(String.valueOf(part.getPrice()));
        partInventoryTxt.setText(String.valueOf(part.getStock()));
        partMinTxt.setText(String.valueOf(part.getMin()));
        partMaxTxt.setText(String.valueOf(part.getMax()));
    }
    @FXML
    private void onActionSelectInHouse(ActionEvent actionEvent) {
        //select “In-House” or “Outsourced”
        isInHouse = true;
        modifyPartCompanyNameTxt.setText("Machine ID");
        partCompanyNameTxt.setPromptText("Machine ID");
        outsourcedRadio.setSelected(false);

    }
    @FXML
    private void onActionSelectOutsourced(ActionEvent actionEvent) {
        // select “In-House” or “Outsourced”
        isInHouse = false;
        modifyPartCompanyNameTxt.setText("Company Name");
        partCompanyNameTxt.setPromptText("Company Name");
        outsourcedRadio.setSelected(true);
    }


    @FXML
    private void onActionSave(ActionEvent actionEvent) throws IOException {


        // RUBRIC:J.  Write code to implement exception controls with custom error messages for one requirement out of
        // each of the following sets (pick one from each):
        //      1.  Set 1
        //          •  entering an inventory value that exceeds the minimum or maximum value for that part or product
        //      2.  Set 2
        //          •  ensuring that a product and part must have a name, price, and inventory level (default 0)


        // validate input fields - all have text
        if(!inHouseRadio.isSelected() && !outsourcedRadio.isSelected()) {
            Helper.alert("ERROR", "Radio Buttons", "Please select InHouse or Outsourced");
        } else if(partNameTxt.getText().isEmpty() || partInventoryTxt.getText().isEmpty() || partPriceTxt.getText().isEmpty() ) {
                Helper.alert("ERROR", "Enter required values.", "Products must have a Name, Inventory Level, and Price.");
        } else if (partMaxTxt.getText().isEmpty() || partMinTxt.getText().isEmpty() ||
                (Integer.parseInt(partMinTxt.getText()) >= Integer.parseInt(partMaxTxt.getText()))) {
            Helper.alert("ERROR", "Minimum Larger Than Max", "The min must be less than the max.");
        }
        else {
            // counter
            int index = -1;

            // modify or change data values
            int id = Integer.parseInt(partIdTxt.getText());
            String name = partNameTxt.getText();
            double price = Double.parseDouble(partPriceTxt.getText());
            int stock = Integer.parseInt(partInventoryTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());

            //
            for(Part part : Inventory.getAllParts()) {
                index++;

                // if InHouse
                if(part.getId() == id && inHouseRadio.isSelected()){
                    int machineId = Integer.parseInt(partCompanyNameTxt.getText());
                    InHouse updated = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.getAllParts().set(index, updated);
                }

                // if Outsourced
                if(part.getId() == id && outsourcedRadio.isSelected()) {
                    String companyName = partCompanyNameTxt.getText();
                    Outsourced updated = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.getAllParts().set(index, updated);
                }
            }

            Helper.returnToMainScreen(actionEvent);
        }
    }

    // cancel or exit out of this screen and go back to the main screen
    @FXML
    private void onActionCancel(ActionEvent actionEvent) throws IOException {
        Helper.returnToMainScreen(actionEvent);
    }

    @FXML
    public void initialize() {
        // no init required
    }

    // Radio Buttons
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outsourcedRadio;

    // Inputs
    @FXML
    private TextField partIdTxt;
    @FXML
    private TextField partNameTxt;
    @FXML
    private TextField partInventoryTxt;
    @FXML
    private TextField partPriceTxt;
    @FXML
    private TextField partMaxTxt;
    @FXML
    private TextField partCompanyNameTxt;
    @FXML
    private TextField partMinTxt;
}
