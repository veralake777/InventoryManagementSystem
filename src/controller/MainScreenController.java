package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;


//
//RIBRIC:G.  Add the following functionality to the main screen, using the methods provided in the attached “UML Class Diagram”:
//
//        •  redirect the user to the “Add Part”, “Modify Part”, “Add Product”, or “Modify Product” screens
//
//        •  delete a selected part or product from the list
//
//        •  search for a part or product and display matching results
//
//        •  exit the main screen

public class MainScreenController {

    // Stage & Scene
    Stage stage;
    Parent scene;

    // redirect the user to the “Add Part”, “Modify Part”, “Add Product”, or “Modify Product” screens
    @FXML
    private void onActionAddPart(ActionEvent actionEvent) throws IOException {
        Helper.goToAddPartScreen(actionEvent);
    }

    @FXML
    private void onActionModifyPart(ActionEvent actionEvent) throws IOException {
        if (partsTbl.getSelectionModel().isEmpty()) {
            Helper.alert("WARNING", "Select Part", "Please select a part to modify.");
        } else {
            FXMLLoader loader = new FXMLLoader();
            URL location = getClass().getResource("/view/ModifyPartScreen.fxml");
            loader.setLocation(location);
            loader.load();

            ModifyPartScreenController MPSController = loader.getController();
            MPSController.receivePart(partsTbl.getSelectionModel().getSelectedItem());

            Helper.goToModifyPartScreen(actionEvent, loader);
        }

    }

    @FXML
    private void onActionAddProduct(ActionEvent actionEvent) throws IOException {
        Helper.goToAddProductScreen(actionEvent);
    }

    @FXML
    private void onActionModifyProduct(ActionEvent actionEvent) throws IOException {
        if(productsTbl.getSelectionModel().isEmpty()){
            Helper.alert("WARNING", "Select Product", "Please select a product to modify.");
        } else {
            FXMLLoader loader = new FXMLLoader();
            URL location = getClass().getResource("/view/ModifyProductScreen.fxml");
            loader.setLocation(location);
            loader.load();

            ModifyProductScreenController MPSController = loader.getController();
            MPSController.receiveProduct(productsTbl.getSelectionModel().getSelectedItem());

            Helper.goToModifyProductScreen(actionEvent, loader);
        }

    }



    // delete a selected part or product from the list
    @FXML
    private void onActionDeletePart(ActionEvent actionEvent) {
        Part selected = partsTbl.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selected);
        partsTbl.setItems(Inventory.getAllParts());
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private void onActionDeleteProduct(ActionEvent actionEvent) {
        Product selected = productsTbl.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(selected);
        productsTbl.setItems(Inventory.getAllProducts());
        productsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }




    // search for a part or product and display matching results
    @FXML
    private void onActionSearchParts(ActionEvent actionEvent) {

        String searchInput =  partsSearchInput.getText().toLowerCase();

        // print true if part exists
        for(Part part : Inventory.getAllParts()){
            try{
                int searchInputInt = Integer.parseInt(searchInput);
                if(part.getId() == searchInputInt) {
                    partsTbl.getSelectionModel().select(Inventory.lookupPart(part.getId()));
                } else {
                    partsTbl.getSelectionModel().clearSelection();
                    partsTbl.setItems(Inventory.getAllParts());
                    partsSearchInput.clear();
                    partsSearchInput.setPromptText("No matches found. Please try again.");
                }
                break;
            } catch (NumberFormatException ex) {
                if (Inventory.lookupPart(searchInput).isEmpty()) {
                    partsTbl.getSelectionModel().clearSelection();
                    partsTbl.setItems(Inventory.getAllParts());
                    partsSearchInput.clear();
                    partsSearchInput.setPromptText("No matches found. Please try again.");
                } else {
                    partsTbl.setItems(Inventory.lookupPart(searchInput));
                }
                partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                break;
            }
        }
    }

    @FXML
    private void onActionSearchProducts(ActionEvent actionEvent) {
        String searchInput =  productsSearchInput.getText().toLowerCase();

            try{
                for(Product product : Inventory.getAllProducts()) {
                    int searchInputInt = Integer.parseInt(searchInput);
                    if (product.getId() == searchInputInt) {
                        productsTbl.getSelectionModel().select(Inventory.lookupProduct(product.getId()));
                    } else {
                        productsTbl.getSelectionModel().clearSelection();
                        productsTbl.setItems(Inventory.getAllProducts());
                        productsSearchInput.clear();
                        productsSearchInput.setPromptText("No matches found. Please try again.");
                    }
                    break;
                }
            } catch (NumberFormatException ex) {
                if(Inventory.lookupProduct(searchInput).isEmpty()) {
                    productsTbl.getSelectionModel().clearSelection();
                    productsTbl.setItems(Inventory.getAllProducts());
                    productsSearchInput.clear();
                    productsSearchInput.setPromptText("No matches found. Please try again.");
                } else {
                    productsTbl.setItems(Inventory.lookupProduct(searchInput));
                }
                productsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                productsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }

    // Exit Program
    @FXML
    private void onActionExit(ActionEvent actionEvent) {

        System.exit(0);
    }

    @FXML
    public void initialize() {

        partsTbl.setItems(Inventory.getAllParts());
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTbl.setItems(Inventory.getAllProducts());
        productsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    // Parts
    @FXML
    private TextField partsSearchInput;
    @FXML
    private TableView<Part> partsTbl;
    @FXML
    private TableColumn<Part, Integer> partsIdCol;
    @FXML
    private TableColumn<Part, String> partsNameCol;
    @FXML
    private TableColumn<Part, Integer> partsInventoryCol;
    @FXML
    private TableColumn<Part, Double> partsPriceCol;

    //Products fx:ids
    @FXML
    private TextField productsSearchInput;
    @FXML
    private TableView<Product> productsTbl;
    @FXML
    private TableColumn<Product, Integer> productsIdCol;
    @FXML
    private TableColumn<Product, String> productsNameCol;
    @FXML
    private TableColumn<Product, Integer> productsInventoryCol;
    @FXML
    private TableColumn<Product, Double> productsPriceCol;

}
