package SilverlineShopping.Preview;

import DataBaseHandler.DataBaseHandler;
import SilverlineShopping.Cart.Cart;
import SilverlineShopping.Cart.InputOutput;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Preview implements Initializable {
    public ImageView imageDisplay;
    public Label nameLabel;
    public Label modelLabel;
    public Label priceLabel;
    public Button addToCart;
    public ChoiceBox<Integer> quantityMenu;
    ObservableList<Integer> quantityList = FXCollections.observableArrayList(1,2,3,4);
    DataBaseHandler DBH;

    String name;
    double price;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quantityMenu.setValue(1);
        quantityMenu.setItems(quantityList);
        DBH = DataBaseHandler.getInstance();
    }

    public void init(String name, String model, double price, ImageView ImageLoc){
        this.name = name +" - "+model;
        this.price = price;
        nameLabel.setText(name);
        modelLabel.setText(model);
        priceLabel.setText(""+price);
        imageDisplay.setImage(ImageLoc.getImage());
    }

    public void handleAddToCart(ActionEvent actionEvent) {
        int quantity = quantityMenu.getValue();
        double total = this.price * quantity;
        ArrayList<Cart.cartItem> items = new ArrayList<>();
        items.add(new Cart.cartItem(this.name,quantity,total));
        InputOutput.writeJson(items);
    }
}
