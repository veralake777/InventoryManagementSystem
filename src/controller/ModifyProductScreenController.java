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

public class ModifyProductScreenController {
    private ObservableList<Part> addParts = FXCollections.observableArrayList();
    private Product productToModify;
    Stage stage;
    Parent scene;

    /////
    // RUBIRC:I.  Add the following functionality to the product screens, using the methods provided in the attached “UML Class Diagram”:
    //          2.  “Modify Product” screen
    //
    //              •  modify or change data values
    //
    //              •  save modifications to the data and then redirect to the main screen
    //
    //              •  associate one or more parts with a product
    //
    //              •  remove or disassociate a part from a product
    //
    //              •  cancel or exit out of this screen and go back to the main screen
    /////

    // Event Handlers
    // get product information from MainMenu
    public void receiveProduct(Product product) {

        productToModify = product;
        addParts.addAll(productToModify.getAssociatedParts());

        productIdTxt.setText(String.valueOf(product.getId()));
        productNameTxt.setText(product.getName());
        productPriceTxt.setText(String.valueOf(product.getPrice()));
        productInventoryTxt.setText(String.valueOf(product.getStock()));
        productMinTxt.setText(String.valueOf(product.getMin()));
        productMaxTxt.setText(String.valueOf(product.getMax()));

        allPartsTbl.setItems(Inventory.getAllParts());
        allPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        addedPartsTbl.setItems(productToModify.getAssociatedParts());
        addedPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartsInventoryCol.setCellValueFactory((new PropertyValueFactory<>("stock")));
        addedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    @FXML
    private boolean onActionSearchProduct(ActionEvent actionEvent) {
        System.out.println("Search Product");
        String searchInput =  productSearchTxt.getText().toLowerCase();

        try{
            for(Part part : Inventory.getAllParts()) {
                int searchInputInt = Integer.parseInt(searchInput);
                if (part.getId() == searchInputInt) {
                    allPartsTbl.getSelectionModel().select(Inventory.lookupPart(part.getId()));
                } else {
                    allPartsTbl.getSelectionModel().clearSelection();
                    allPartsTbl.setItems(Inventory.getAllParts());
                    productSearchTxt.clear();
                    productSearchTxt.setPromptText("No matches found. Please try again.");
                }
                break;
            }
        } catch (NumberFormatException ex) {
            if(Inventory.lookupPart(searchInput).isEmpty()) {
                allPartsTbl.getSelectionModel().clearSelection();
                allPartsTbl.setItems(Inventory.getAllParts());
                productSearchTxt.clear();
                productSearchTxt.setPromptText("No matches found. Please try again.");
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
    private void onActionAddPart(ActionEvent actionEvent) {
        // associate one or more parts with a product
        Part selectedPart = allPartsTbl.getSelectionModel().getSelectedItem();
        addParts.add(selectedPart);

        addedPartsTbl.setItems(addParts);
        addedPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        allPartsTbl.getSelectionModel().clearSelection();
    }

    @FXML
    private void onActionDeletePart(ActionEvent actionEvent) {
        Part selectedPart = addedPartsTbl.getSelectionModel().getSelectedItem();
        addParts.remove(selectedPart);

        addedPartsTbl.setItems(addParts);
        addedPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        addedPartsTbl.getSelectionModel().clearSelection();
    }

    @FXML
    private void onActionSaveProduct(ActionEvent actionEvent) throws IOException {
        addParts = addedPartsTbl.getItems();

        // RUBRIC:J.  Write code to implement exception controls with custom error messages for one requirement out of
        // each of the following sets (pick one from each):
        //      1.  Set 1
        //          •  entering an inventory value that exceeds the minimum or maximum value for that part or product
        //      2.  Set 2
        //          •  ensuring that a product and part must have a name, price, and inventory level (default 0)
        if(productNameTxt.getText().isEmpty() || productInventoryTxt.getText().isEmpty() || productPriceTxt.getText().isEmpty() ) {
            Helper.alert("ERROR", "Enter required values.", "Products must have a Name, Inventory Level, and Price.");
        } else if (productMaxTxt.getText().isEmpty() || productMaxTxt.getText().isEmpty() ||
                (Integer.parseInt(productMinTxt.getText()) >= Integer.parseInt(productMaxTxt.getText()))) {
            Helper.alert("ERROR", "Minimum Larger Than Max", "The min must be less than the max.");
        }else {
            // counter
            int index = -1;

            // modify or change data values
            int id = Integer.parseInt(productIdTxt.getText());
            String name = productNameTxt.getText();
            double price = Double.parseDouble(productPriceTxt.getText());
            int stock = Integer.parseInt(productInventoryTxt.getText());
            int min = Integer.parseInt(productMinTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());
            Product updated = new Product(addParts, id, name, price, stock, min, max);

            // save modifications to the data and then redirect to the main screen
            for(Product product : Inventory.getAllProducts()) {
                index++;
                if(product.getId() == updated.getId()){
                    System.out.println(updated.getId());
                    Inventory.getAllProducts().set(index, updated);
                    // fix for persistent parts list
                    updated.getAssociatedParts();
                }
            }
            Helper.returnToMainScreen(actionEvent);
        }


    }

    @FXML
    private void onActionCancelProduct(ActionEvent actionEvent) throws IOException {
        Helper.returnToMainScreen(actionEvent);
    }

    @FXML
    public void initialize() {
        //no init required
    }

    // Inputs
    @FXML
    private TextField productIdTxt;
    @FXML
    private TextField productNameTxt;
    @FXML
    private TextField productInventoryTxt;
    @FXML
    private TextField productPriceTxt;
    @FXML
    private TextField productMaxTxt;
    @FXML
    private TextField productMinTxt;

    // Search
    @FXML
    private TextField productSearchTxt;

    // Add Table
    @FXML
    private TableView<Part> allPartsTbl;
    @FXML
    private TableColumn<Part, Integer> allPartsIdCol;
    @FXML
    private TableColumn<Part, String> allPartsNameCol;
    @FXML
    private TableColumn<Part, Integer> allPartsInventoryCol;
    @FXML
    private TableColumn<Part, Double> allPartsPriceCol;

    // Delete Table
    @FXML
    private TableView<Part> addedPartsTbl;
    @FXML
    private TableColumn<Part, Integer> addedPartsIdCol;
    @FXML
    private TableColumn<Part, String> addedPartsNameCol;
    @FXML
    private TableColumn<Part, Integer> addedPartsInventoryCol;
    @FXML
    private TableColumn<Part, Double> addedPartsPriceCol;



}
