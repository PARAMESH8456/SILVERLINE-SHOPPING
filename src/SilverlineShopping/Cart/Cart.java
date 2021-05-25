package SilverlineShopping.Cart;

import SilverlineShopping.Checkout.Checkout;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.security.auth.RefreshFailedException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Cart implements Initializable {
    public TableView<cartItem> CartTable;
    public Label priceLabel;
    public TableColumn<cartItem,String> itemColumn;
    public TableColumn<cartItem,Integer> quantityColumn;
    public TableColumn<cartItem,Double> totalColumn;
    ObservableList<cartItem> itemsList;
    ArrayList<Cart.cartItem> items;
    double subTotal;
    public void init(){
        items = InputOutput.readJson();
    }

    public void associateColumns(){
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        associateColumns();
        itemsList = FXCollections.observableArrayList();
        if(items != null){
            itemsList.addAll(items);
        }
        CartTable.setItems(itemsList);
        ArrayList<cartItem> priceForItems = InputOutput.readJson();
        double totalPrice = 0;
        for(int i = 0; i < priceForItems.size(); i++){
            totalPrice += priceForItems.get(i).getItemPrice();
        }
        subTotal = totalPrice;
        priceLabel.setText(""+totalPrice);
    }

    public void handleRemove(ActionEvent actionEvent) {
        cartItem selectedItem = CartTable.getSelectionModel().getSelectedItem();
        String itemName = selectedItem.getItemName();
        int itemQuantity = selectedItem.getItemQuantity();
        double itemPrice = selectedItem.getItemPrice();
        ArrayList<cartItem> itemsList = InputOutput.readJson();
        for(int i = 0 ; i < itemsList.size(); i++){
            String name = itemsList.get(i).getItemName();
            int  quantity = itemsList.get(i).getItemQuantity();
            double price = itemsList.get(i).getItemPrice();
            if(name.equals(itemName) && quantity == itemQuantity && itemPrice == price){
                itemsList.remove(i);
                break;
            }
        }
        InputOutput.rewriteJson(itemsList);
    }

    public void handleCheckout(ActionEvent actionEvent) {
        String location = "/SilverlineShopping/Checkout/Checkout.fxml";
        if(subTotal == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Thank you for shopping with Silver Line");
            alert.setContentText("Cart is empty");
            alert.showAndWait();
            InputOutput. clearJson();
        }else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
                Parent parent =loader.load();
                Checkout controller = loader.getController();
                controller.init(subTotal);
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Checkout");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleRefresh(ActionEvent actionEvent) {
        items = InputOutput.readJson();
        itemsList = FXCollections.observableArrayList();
        if(items != null){
            itemsList.addAll(items);
        }
        CartTable.setItems(itemsList);
    }

    public static class cartItem{
        private final SimpleStringProperty itemName;
        private final SimpleIntegerProperty itemQuantity;
        private final SimpleDoubleProperty itemPrice;

        public cartItem(String itemName, Integer itemQuantity, Double itemPrice) {
            this.itemName = new SimpleStringProperty(itemName);
            this.itemQuantity = new SimpleIntegerProperty(itemQuantity);
            this.itemPrice = new SimpleDoubleProperty(itemPrice);
        }

        public String getItemName() {
            return itemName.get();
        }

        public int getItemQuantity() {
            return itemQuantity.get();
        }

        public double getItemPrice() {
            return itemPrice.get();
        }

    }
}
