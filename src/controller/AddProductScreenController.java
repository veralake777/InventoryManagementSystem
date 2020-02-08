package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class AddProductScreenController {

    private ObservableList<Part> addParts = FXCollections.observableArrayList();
    Stage stage;
    Parent scene;

    // Inputs
    @FXML
    private TextField addProductIdTxt;
    @FXML
    private TextField addProductNameTxt;
    @FXML
    private TextField addProductInvTxt;
    @FXML
    private TextField addProductPriceTxt;
    @FXML
    private TextField addProductMinTxt;
    @FXML
    private TextField addProductMaxTxt;

    // Search
    @FXML
    private TextField allPartsSearchInput;

    // All Parts Table (TOP TABLE)
    @FXML
    private TableView<Part> allPartsTbl;
    @FXML
    private TableColumn<Product, Integer> allPartsIdCol;
    @FXML
    private TableColumn<Product, String> allPartsNameCol;
    @FXML
    private TableColumn<Product, Integer> allPartsInventoryCol;
    @FXML
    private TableColumn<Product, Double> allPartsPriceCol;

    // Added Parts Table (BOTTOM TABLE)
    @FXML
    private TableView<Part> addPartsTbl;
    @FXML
    private TableColumn<Object, Object> addPartsIdCol;
    @FXML
    private TableColumn<Object, Object> addPartsNameCol;
    @FXML
    private TableColumn<Object, Object> addPartsInventoryCol;
    @FXML
    private TableColumn<Object, Object> addPartsPriceCol;



    /////
    //RUBRIC:I.  Add the following functionality to the product screens, using the methods provided in the attached “UML Class Diagram”:
    //
    //          1.  “Add Product” screen
    //
    //              •  enter name, inventory level, price, and max and min values
    //
    //              •  save the data and then redirect to the main screen
    //
    //              •  associate one or more parts with a product
    //
    //              •  remove or disassociate a part from a product
    //
    //              •  cancel or exit out of this screen and go back to the main screen
    /////


    // Event Handlers
    @FXML
    private boolean onActionSearchProduct(ActionEvent actionEvent) {
        String searchInput =  allPartsSearchInput.getText().toLowerCase();

        try{
            for(Product product : Inventory.getAllProducts()) {
                int searchInputInt = Integer.parseInt(searchInput);
                if (product.getId() == searchInputInt) {
                    allPartsTbl.getSelectionModel().select(Inventory.lookupPart(product.getId()));
                } else {
                    allPartsTbl.getSelectionModel().clearSelection();
                    allPartsTbl.setItems(Inventory.getAllParts());
                    allPartsSearchInput.clear();
                    allPartsSearchInput.setPromptText("No matches found. Please try again.");
                }
                break;
            }
        } catch (NumberFormatException ex) {
            if(Inventory.lookupPart(searchInput).isEmpty()) {
                allPartsTbl.getSelectionModel().clearSelection();
                allPartsTbl.setItems(Inventory.getAllParts());
                allPartsSearchInput.clear();
                allPartsSearchInput.setPromptText("No matches found. Please try again.");
            } else {
               allPartsTbl.setItems(Inventory.lookupPart(searchInput));
            }
            allPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            allPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        }
        return true;
    }


    @FXML
    private void onActionAddPart(ActionEvent actionEvent) throws IOException {

        // associate one or more parts with a product
            Part selectedPart = allPartsTbl.getSelectionModel().getSelectedItem();
            addParts.add(selectedPart);

            addPartsTbl.setItems(addParts);
            addPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            addPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            addPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            addPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    @FXML
    private void onActionSaveProduct(ActionEvent actionEvent) throws IOException {
        // auto generate part id
        int id = Inventory.getAllParts().size() + 1;

        // RUBRIC:J.  Write code to implement exception controls with custom error messages for one requirement out of
        // each of the following sets (pick one from each):
        //      1.  Set 1
        //          •  entering an inventory value that exceeds the minimum or maximum value for that part or product
        //      2.  Set 2
        //          •  ensuring that a product and part must have a name, price, and inventory level (default 0)

        if(Integer.parseInt(addProductMinTxt.getText()) >= Integer.parseInt(addProductMaxTxt.getText()) ) {
            Helper.alert("ERROR", "Minimum Larger Than Max", "The min must be less than the max.");
        }

        // validate input fields - all have text
        else if(addProductNameTxt.getText().isEmpty() || addProductInvTxt.getText().isEmpty() || addProductPriceTxt.getText().isEmpty()) {
            Helper.alert("ERROR", "Enter required values.", "Products must have a Name, Inventory Level, and Price.");
        } else {
            //enter name, inventory level, price, and max and min values

            String name = addProductNameTxt.getText();
            int stock = Integer.parseInt(addProductInvTxt.getText());
            double price = Double.parseDouble(addProductPriceTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());


            // associate one or more parts with a product
            //save the data and then redirect to the main screen
            Product prod = new Product(addParts, id, name, price, stock, min, max);
            Inventory.addProduct(prod);

            Helper.returnToMainScreen(actionEvent);
        }
    }

    @FXML
    private void onActionDeletePart(ActionEvent actionEvent) {
        // remove or disassociate a part from a product
        Part selectedPart = addPartsTbl.getSelectionModel().getSelectedItem();
        addParts.remove(selectedPart);
        addPartsTbl.setItems(addParts);
        addPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private void onActionCancelProduct(ActionEvent actionEvent) throws IOException {
        //cancel or exit out of this screen and go back to the main screen
        Helper.returnToMainScreen(actionEvent);
    }

    @FXML
    public void initialize() {
        // auto populate the product id text input
        addProductIdTxt.setText(String.valueOf(Inventory.getAllProducts().size() +1));
        // populate add parts table
        allPartsTbl.setItems(Inventory.getAllParts());
        allPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
