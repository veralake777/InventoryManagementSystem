package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        boolean canAdd = true;
        // validate that id and name are unique
        for(Part part : allParts) {
            if (newPart.getId() == part.getId() || newPart.getName().toLowerCase().equals(part.getName().toLowerCase())) {
                canAdd = false;
                break;
            }
        }
        if(canAdd){
            allParts.add(newPart);
        } else {
            System.out.println("Part already exists. Please try another ID.");
        }

    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        for(Part part : Inventory.getAllParts()) {
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partObservableList = FXCollections.observableArrayList();
        for(Part part : Inventory.getAllParts()) {
            if(part.getName().toLowerCase().contains(partName)) {
                partObservableList.add(part);
            }
        }
        return partObservableList;
    }

    public static Product lookupProduct(int productId) {
        for(Product product : Inventory.getAllProducts()) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productObservableList = FXCollections.observableArrayList();
        for(Product product : Inventory.getAllProducts()) {
            if(product.getName().toLowerCase().contains(productName)) {
                productObservableList.add(product);
            }
        }
        return productObservableList;
    }

    public static void updatePart(int index, Part selectedPart) {
        //update part
    }

    public static void updateProduct(int index, Product selectedProduct) {
        //update product
    }

    public static void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }

    public static void deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
