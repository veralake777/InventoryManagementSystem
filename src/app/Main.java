package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        primaryStage.setTitle("IMS");
        primaryStage.setScene(new Scene(root, 1200, 650));
        primaryStage.show();
    }


    public static void main(String[] args) {

        InHouse inHouse1 = new InHouse(1, "in1", 1.99, 100, 0, 200, 12345);
        InHouse inHouse2 = new InHouse(2, "inA", 2.99, 100, 0, 200, 12346);
        InHouse inHouse3 = new InHouse(3, "inB", 3.99, 100, 0, 200, 12347);
        InHouse inHouse4 = new InHouse(4, "inC", 4.99, 100, 0, 200, 12348);
        InHouse inHouse5 = new InHouse(5, "inD", 5.99, 100, 0, 200, 12349);

        Outsourced out1 = new Outsourced(6, "outA", 6.99, 100, 0, 200, "ACMEA");
        Outsourced out2 = new Outsourced(7, "outB", 7.99, 100, 0, 200, "ACMEB");
        Outsourced out3 = new Outsourced(8, "outC", 8.99, 100, 0, 200, "ACMEC");
        Outsourced out4 = new Outsourced(9, "outD", 9.99, 100, 0, 200, "ACMED");
        Outsourced out5 = new Outsourced(10, "outE", 10.99, 100, 0, 200, "ACMEE");

        Inventory.addPart(inHouse1);
        Inventory.addPart(inHouse2);
        Inventory.addPart(inHouse3);
        Inventory.addPart(inHouse4);
        Inventory.addPart(inHouse5);
        Inventory.addPart(out1);
        Inventory.addPart(out2);
        Inventory.addPart(out3);
        Inventory.addPart(out4);
        Inventory.addPart(out5);

        Product bookshelf = new Product(Inventory.getAllParts(), 1, "Bookshelf", 199.99, 50, 1, 100);
        Product hammer = new Product(Inventory.getAllParts(), 2, "Hammer", 29.99, 5000, 1, 100);
        Product car = new Product(Inventory.getAllParts(), 3, "Car", 29999.99, 5, 1, 100);
        Inventory.addProduct(bookshelf);
        Inventory.addProduct(hammer);
        Inventory.addProduct(car);


        launch(args);
    }
}
