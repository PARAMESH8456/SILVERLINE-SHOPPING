package SilverlineShopping.Checkout;

import SilverlineShopping.Cart.InputOutput;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class Checkout implements Initializable {
    public JFXTextField name;
    public JFXTextField phone_no;
    public JFXTextField email_id;
    public JFXTextField flat_no;
    public JFXTextField address;
    public JFXTextField landmark;
    double totalPrice;
    boolean notANumber = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(double price){
        totalPrice = price;
    }

    public void checkout(ActionEvent actionEvent) {

        String phoneNo = phone_no.getText();
        char[] phno = phoneNo.toCharArray();
        for(int i = 0 ; i < phno.length; i++){
            if(!Character.isDigit(phno[i])){
                notANumber = true;
                break;
            }else{
                notANumber = false;
            }
        }
        if(name.getText().isEmpty() || phone_no.getText().isEmpty() || email_id.getText().isEmpty() || flat_no.getText().isEmpty() || address.getText().isEmpty() || landmark.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter All Fields");
            alert.showAndWait();
            InputOutput. clearJson();
        }else if(notANumber){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Number Should consist only of digits");
            alert.showAndWait();
            InputOutput. clearJson();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Thank you for shopping with Silver Line");
            alert.setContentText("Price : "+totalPrice);
            alert.showAndWait();
            InputOutput. clearJson();
        }
    }
}
