package SilverlineShopping;

import DataBaseHandler.DataBaseHandler;
import SilverlineShopping.Cart.Cart;
import SilverlineShopping.Preview.Preview;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    public JFXTextField searchBox;
    public JFXButton searchButton;
    public Tab mobileTab;
    public TableView<mobile> mobileTable;
    public TableColumn<mobile, String> mobileNameColumn;
    public TableColumn<mobile, String> mobileModelColumn;
    public TableColumn<mobile, Double> mobilePriceColumn;
    public TableColumn<mobile, ImageView> mobileImageColumn;
    public Tab electronicsTab;
    public TableView<electronics> electronicsTable;
    public TableColumn<electronics, String> electronicsNameColumn;
    public TableColumn<electronics, String> electronicsSerialColumn;
    public TableColumn<electronics, Double> electronicsPriceColumn;
    public TableColumn<electronics, ImageView> electronicsImageColumn;
    public ContextMenu contextMenuMobile;
    public ContextMenu contextMenuElectronics;

    ObservableList<mobile> mobileList = FXCollections.observableArrayList();
    ObservableList<electronics> electronicsList = FXCollections.observableArrayList();

    DataBaseHandler DBH;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DBH = DataBaseHandler.getInstance();
        associateMobileColumns();
        associateElectronicsColumns();
        loadMobileData();
        loadElectronicsData();
    }

    public void associateMobileColumns() {
        mobileNameColumn.setCellValueFactory(new PropertyValueFactory<>("MobileName"));
        mobileModelColumn.setCellValueFactory(new PropertyValueFactory<>("MobileModel"));
        mobilePriceColumn.setCellValueFactory(new PropertyValueFactory<>("MobilePrice"));
        mobileImageColumn.setCellValueFactory(new PropertyValueFactory<>("MobileImage"));
    }

    public void associateElectronicsColumns() {
        electronicsNameColumn.setCellValueFactory(new PropertyValueFactory<>("ElectronicsName"));
        electronicsSerialColumn.setCellValueFactory(new PropertyValueFactory<>("ElectronicsModel"));
        electronicsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("ElectronicsPrice"));
        electronicsImageColumn.setCellValueFactory(new PropertyValueFactory<>("ElectronicsImage"));
    }

    public void loadMobileData() {
        mobileList.clear();
        ResultSet rs;
        DBH = DataBaseHandler.getInstance();
        rs = DBH.execQuery("select * from mobile");
        while (true) {
            try {
                if (rs.next()) {
                    String name = rs.getString(2);
                    String model = rs.getString(3);
                    Double price = rs.getDouble(4);
                    Image img = new Image(rs.getString(5));
                    ImageView imageView = new ImageView(img);
                    imageView.setFitWidth(70);
                    imageView.setFitHeight(90);
                    mobileList.add(new mobile(name, model, price, imageView));
                } else {
                    break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        mobileTable.setItems(mobileList);
    }

    public void loadMobile(Event event) {
        loadMobileData();
    }

    public void loadElectronicsData() {
        electronicsList.clear();
        ResultSet rs;
        rs = DBH.execQuery("select * from electronics");
        while (true) {
            try {
                if (rs.next()) {
                    String name = rs.getString(2);
                    String model = rs.getString(3);
                    Double price = rs.getDouble(4);
                    Image img = new Image(rs.getString(5));
                    ImageView imageView = new ImageView(img);
                    imageView.setFitWidth(70);
                    imageView.setFitHeight(90);
                    electronicsList.add(new electronics(name, model, price, imageView));
                } else {
                    break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        electronicsTable.setItems(electronicsList);
    }

    public void loadElectronics(Event event) {
        loadElectronicsData();
    }

    public void search(ActionEvent actionEvent) {
        String q = "";
        ResultSet rs;
        String searchKey = searchBox.getText();
        if (mobileTab.isSelected()) {
            mobileList.clear();
            rs = DBH.execSpecialQuery(1, searchKey);
            while (true) {
                try {
                    if (rs.next()) {
                        String name = rs.getString(2);
                        String model = rs.getString(3);
                        Double price = rs.getDouble(4);
                        Image img = new Image(rs.getString(5));
                        ImageView imageView = new ImageView(img);
                        imageView.setFitWidth(70);
                        imageView.setFitHeight(90);
                        mobileList.add(new mobile(name, model, price, imageView));
                    } else {
                        break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            mobileTable.setItems(mobileList);
        } else if (electronicsTab.isSelected()) {
            electronicsList.clear();
            rs = DBH.execSpecialQuery(2, searchKey);

            while (true) {
                try {
                    if (rs.next()) {
                        String name = rs.getString(2);
                        String model = rs.getString(3);
                        Double price = rs.getDouble(4);
                        Image img = new Image(rs.getString(5));
                        ImageView imageView = new ImageView(img);
                        imageView.setFitWidth(70);
                        imageView.setFitHeight(90);
                        electronicsList.add(new electronics(name, model, price, imageView));
                    } else {
                        break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            electronicsTable.setItems(electronicsList);
        }
    }

    public void handlePreview(ActionEvent actionEvent) {
        if(mobileTab.isSelected()){
            mobile selectedMobile = mobileTable.getSelectionModel().getSelectedItem();
            String location = "/SilverlineShopping/Preview/Preview.fxml";
            String name = selectedMobile.getMobileName();
            String model = selectedMobile.getMobileModel();
            double price = selectedMobile.getMobilePrice();
            ImageView img = selectedMobile.getMobileImage();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
                Parent parent = loader.load();
                Preview controller = loader.getController();
                controller.init(name, model, price, img);
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Mobile preview");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(electronicsTab.isSelected()){
            electronics selectedElectronics = electronicsTable.getSelectionModel().getSelectedItem();
            String location = "/SilverlineShopping/Preview/Preview.fxml";
            String name = selectedElectronics.getElectronicsName();
            String model = selectedElectronics.getElectronicsModel();
            double price = selectedElectronics.getElectronicsPrice();
            ImageView img = selectedElectronics.getElectronicsImage();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
                Parent parent = loader.load();
                Preview controller = loader.getController();
                controller.init(name, model, price, img);
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Electronics preview");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void handleCart(ActionEvent actionEvent) {
        String location = "/SilverlineShopping/Cart/Cart.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
            Parent parent = loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("My cart");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class mobile {
        private SimpleStringProperty MobileName;
        private SimpleStringProperty MobileModel;
        private SimpleDoubleProperty MobilePrice;
        private javafx.scene.image.ImageView MobileImage;

        public mobile(String mobileName, String mobileModel, Double mobilePrice, ImageView mobileImage) {
            MobileName = new SimpleStringProperty(mobileName);
            MobileModel = new SimpleStringProperty(mobileModel);
            MobilePrice = new SimpleDoubleProperty(mobilePrice);
            MobileImage = mobileImage;
        }

        public String getMobileName() {
            return MobileName.get();
        }

        public String getMobileModel() {
            return MobileModel.get();
        }

        public double getMobilePrice() {
            return MobilePrice.get();
        }

        public ImageView getMobileImage() {
            return MobileImage;
        }
    }

    public static class electronics {
        private SimpleStringProperty ElectronicsName;
        private SimpleStringProperty ElectronicsModel;
        private SimpleDoubleProperty ElectronicsPrice;
        private ImageView ElectronicsImage;

        public electronics(String electronicsName, String electronicsModel, Double electronicsPrice, ImageView electronicsImage) {
            ElectronicsName = new SimpleStringProperty(electronicsName);
            ElectronicsModel = new SimpleStringProperty(electronicsModel);
            ElectronicsPrice = new SimpleDoubleProperty(electronicsPrice);
            ElectronicsImage = electronicsImage;
        }

        public String getElectronicsName() {
            return ElectronicsName.get();
        }

        public String getElectronicsModel() {
            return ElectronicsModel.get();
        }

        public double getElectronicsPrice() {
            return ElectronicsPrice.get();
        }

        public ImageView getElectronicsImage() {
            return ElectronicsImage;
        }
    }
}
