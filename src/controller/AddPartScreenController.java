package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import javax.print.DocFlavor;
import java.io.IOException;

public class AddPartScreenController {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton addPartInHouseRadio;
    @FXML
    private RadioButton addPartOutsourcedRadio;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartInvoice;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartMax;
    @FXML
    private Label addPartMachIdTxt;
    @FXML
    private TextField addPartMachId;
    @FXML
    private TextField addPartMin;



    /////
    //RUBRIC:H.  Add the following functionalities to the part screens, using the methods provided in the attached “UML Class Diagram”:
    //
    //       1.  “Add Part” screen
    //
    //          •  select “In-House” or “Outsourced”
    //
    //          •  enter name, inventory level, price, max and min values, and company name or machine ID
    //
    //          •  save the data and then redirect to the main screen
    //
    //          •  cancel or exit out of this screen and go back to the main screen
    /////
    @FXML
    private void onActionSelectInHouse(ActionEvent actionEvent) {
        addPartMachIdTxt.setText("Machine ID");
        addPartMachId.setPromptText("Machine ID");
        addPartOutsourcedRadio.setSelected(false);
    }

    @FXML
    private void onActionSelectOutsourced(ActionEvent actionEvent) {
        addPartMachIdTxt.setText("Company Name");
        addPartMachId.setPromptText("Company Name");
        addPartInHouseRadio.setSelected(false);
    }

    @FXML
    private void onActionSavePart(ActionEvent actionEvent) throws IOException {
        // validate radio button selection
        if(!addPartInHouseRadio.isSelected() && !addPartOutsourcedRadio.isSelected()){

            Helper.alert("WARNING", "Select a Part Type.", "Please select InHouse or Outsourced radio buttons before saving.");
            System.out.println("Please select InHouse or Outsourced radio buttons.");

        // validate all input fields have a value
        } else if (
                addPartName.getText().isEmpty() || addPartInvoice.getText().isEmpty() ||
                addPartPrice.getText().isEmpty()) {

            Helper.alert("ERROR", "Enter required values.", "Parts must have a Name, Inventory Level, and Price.");

        // enter name, inventory level, price, max and min values, and company name or machine ID
        } else {

            // auto generate part id
            int id = Inventory.getAllParts().size() + 1;
            String name = addPartName.getText();
            int stock = Integer.parseInt(addPartInvoice.getText());
            double price = Double.parseDouble(addPartPrice.getText());
            int max = Integer.parseInt(addPartMax.getText());
            int min = Integer.parseInt(addPartMin.getText());

            // RUBRIC:J.  Write code to implement exception controls with custom error messages for one requirement out of
            // each of the following sets (pick one from each):
            //      1.  Set 1
            //          •  entering an inventory value that exceeds the minimum or maximum value for that part or product
            //      2.  Set 2
            //          •  ensuring that a product and part must have a name, price, and inventory level (default 0)

            if(min >= max ) {
                Helper.alert("ERROR", "Minimum Larger Than Max", "The min must be less than the max.");
            }

            // validate input fields - all have text
            else if(addPartName.getText().isEmpty() || addPartPrice.getText().isEmpty()) {

                Helper.alert("ERROR", "Enter required values.", "Products must have a Name, Inventory Level, and Price.");
            } else {
                // save the data and then redirect to the main screen
                if (addPartInHouseRadio.isSelected()) {
                    int machineId = Integer.parseInt(addPartMachId.getText());
                    InHouse part = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(part);
                }

                if (addPartOutsourcedRadio.isSelected()) {
                    String companyName = addPartMachId.getText();
                    Outsourced part = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(part);
                }

                Helper.returnToMainScreen(actionEvent);
            }
        }


    }

    //cancel or exit out of this screen and go back to the main screen
    @FXML
    private void onActionCancelPart(ActionEvent actionEvent) throws IOException {
//        Helper.alert("")
        Helper.returnToMainScreen(actionEvent);
    }
    @FXML
    public void initialize() {
        // no init required
    }
}
