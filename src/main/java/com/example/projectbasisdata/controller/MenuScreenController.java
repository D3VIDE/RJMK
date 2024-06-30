package com.example.projectbasisdata.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.projectbasisdata.DatabaseConnection;
import com.example.projectbasisdata.MainApp;
import com.example.projectbasisdata.model.DetailMenu;
import com.example.projectbasisdata.model.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MenuScreenController implements Initializable {

    @FXML
    private AnchorPane menu_form;
    @FXML
    private Button dashboard_btn;
    @FXML
    private Button menu_btn;
    @FXML
    private Button transaksi_btn;
    @FXML
    private Button customer_btn;
    @FXML
    private Button promo_btn;
    @FXML
    private TableView<DetailMenu> menu_tableView;
    @FXML
    private TableColumn<DetailMenu, Integer> menu_col_idProduk;
    @FXML
    private TableColumn<DetailMenu, String> menu_col_kategori;
    @FXML
    private TableColumn<DetailMenu, String> menu_col_menu;
    @FXML
    private TableColumn<DetailMenu, String> menu_col_ukuran;
    @FXML
    private TableColumn<DetailMenu, Integer> menu_col_harga;
    @FXML
    private TextField menu_idProduk;
    @FXML
    private TextField menu_menu;
    @FXML
    private ComboBox<?> menu_kategori;
    @FXML
    private TextField menu_harga;
    @FXML
    private Button menu_addBtn;
    @FXML
    private Button menu_updateBtn;
    @FXML
    private Button menu_deleteBtn;
    @FXML
    private Button menu_clearBtn;
    @FXML
    private ComboBox<?> menu_ukuran;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private String[] sizeList = new String[]{"Large", "Medium", "Small"};
    private String[] kategoriList = new String[]{"COFFEE", "CREAM", "ADD ONS"};
    private Alert alert;

    @FXML
    public void dashboardClick() throws IOException {
        MainApp.setRoot("mainScreen");
    }
    @FXML
    public void transaksiClick() throws IOException {
        MainApp.setRoot("transaksiScreen");
    }
    @FXML
    public void promoClick() throws IOException {
        MainApp.setRoot("PromoScreen");
    }
    public void menuSizeList() {
        List<String> sizeL = new ArrayList<>();
        String[] var2 = this.sizeList;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String data = var2[var4];
            sizeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(sizeL);
        this.menu_ukuran.setItems(listData);
    }
    public void menuKategoriList() {
        List<String> kategoriL = new ArrayList<>();
        String[] var2 = this.kategoriList;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String data = var2[var4];
            kategoriL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(kategoriL);
        this.menu_kategori.setItems(listData);
    }

//    public ObservableList<Menu> menuList() throws SQLException { //untuk query menampilkan list yang kita miliki
//        ObservableList <Menu> listMenu = FXCollections.observableArrayList();
//        String query = "SELECT * FROM menu";
//
//        try{
//            Connection connect = DatabaseConnection.getConnection();
//            PreparedStatement prepare = connect.prepareStatement(query);
//            ResultSet result  = prepare.executeQuery();
//            Menu menu;
//            while(result.next()){
//                menu = new Menu(result.getInt("menu_id"),
//                        result.getString("menu_name"),
//                        result.getInt("kategori_id"));
//                listMenu.add(menu);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return listMenu;
//    }
//    public void initializeMenu()  {
//      //initialize and load
//        try{
//            menu_col_idProduk.setCellValueFactory(new PropertyValueFactory<>("menu_id"));
//            menu_col_menu.setCellValueFactory(new PropertyValueFactory<>("menu_name"));
//            menu_col_kategori.setCellValueFactory(new PropertyValueFactory<>("kategori_id"));
//            ObservableList<Menu> menuInventory = menuList();
//            menu_tableView.setItems(menuInventory);
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }


public ObservableList<DetailMenu> detailMenuList() throws SQLException {
    ObservableList<DetailMenu> listDetailMenu = FXCollections.observableArrayList();
    String query = "SELECT dm.detailmenu_id, k.kategori_name, m.menu_name, s.size_name, dm.harga_nominal " +
            "FROM detail_menu dm " +
            "JOIN menu m ON dm.menu_id = m.menu_id " +
            "JOIN kategori k ON m.kategori_id = k.kategori_id " +
            "JOIN size s ON dm.size_id = s.size_id";

    try (Connection connect = DatabaseConnection.getConnection();
         PreparedStatement prepare = connect.prepareStatement(query);
         ResultSet result = prepare.executeQuery()) {
        while (result.next()) {
            DetailMenu menu = new DetailMenu(
                    result.getInt("detailmenu_id"),
                    result.getString("kategori_name"),
                    result.getString("menu_name"),
                    result.getString("size_name"),
                    result.getInt("harga_nominal")
            );

            listDetailMenu.add(menu);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return listDetailMenu;
}


    public void initializeMenu() {
        try {
            menu_col_idProduk.setCellValueFactory(new PropertyValueFactory<>("detailmenu_id"));
            menu_col_kategori.setCellValueFactory(new PropertyValueFactory<>("kategori_name"));
            menu_col_menu.setCellValueFactory(new PropertyValueFactory<>("menu_name"));
            menu_col_ukuran.setCellValueFactory(new PropertyValueFactory<>("size_name"));
            menu_col_harga.setCellValueFactory(new PropertyValueFactory<>("harga_nominal"));

            ObservableList<DetailMenu> detailMenus = detailMenuList();
            menu_tableView.setItems(detailMenus);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void menuAddBtn() throws SQLException {
        if (!this.menu_idProduk.getText().isEmpty() && !this.menu_menu.getText().isEmpty() && this.menu_kategori.getSelectionModel().getSelectedItem() != null && this.menu_ukuran.getSelectionModel().getSelectedItem() != null && !this.menu_harga.getText().isEmpty()) {
            String checkDetailMenuID = "SELECT detailmenu_id FROM detail_menu WHERE detailmenu_id = " + this.menu_idProduk.getText();
            this.connect = DatabaseConnection.getConnection();

            try {
                this.statement = this.connect.createStatement();
                this.result = this.statement.executeQuery(checkDetailMenuID);
                if (this.result.next()) {
                    this.alert = new Alert(AlertType.ERROR);
                    this.alert.setTitle("Message");
                    this.alert.setHeaderText((String)null);
                    this.alert.setContentText("ID produk "+ this.menu_idProduk.getText() + " sudah digunakan");
                    this.alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO menu (menu_name, kategori_id)" +
                            "VALUES (" +
                            "?," +
                            "(SELECT kategori_id FROM kategori WHERE kategori_name = ?)" +
                            ")";
                    this.prepare = this.connect.prepareStatement(insertData);
                    this.prepare.setString(1, this.menu_menu.getText());
                    this.prepare.setString(2, (String) this.menu_kategori.getSelectionModel().getSelectedItem());
                    this.prepare.executeUpdate();
                    String insertData2 = "INSERT INTO detail_menu (harga_nominal, menu_id, size_id)" +
                            "VALUES (" +
                            "?," +
                            "(SELECT menu_id FROM menu WHERE menu_name = ? AND kategori_id = (SELECT kategori_id FROM kategori WHERE kategori_name = ?))," +
                            "(SELECT size_id FROM size WHERE size_name = ?))";
                    this.prepare = this.connect.prepareStatement(insertData2);
                    this.prepare.setInt(1, Integer.parseInt(this.menu_harga.getText()));
                    this.prepare.setString(2, this.menu_menu.getText());
                    this.prepare.setString(3, (String) this.menu_kategori.getSelectionModel().getSelectedItem());
                    this.prepare.setString(4, (String) this.menu_ukuran.getSelectionModel().getSelectedItem());
                    this.prepare.executeUpdate();
                    this.alert = new Alert(AlertType.INFORMATION);
                    this.alert.setTitle("Error Message");
                    this.alert.setHeaderText((String)null);
                    this.alert.setContentText("Successfully Added!");
                    this.alert.showAndWait();
                    this.initializeMenu();
                    this.menuClearBtn();
                }
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        } else {
            this.alert = new Alert(AlertType.ERROR);
            this.alert.setTitle("Message");
            this.alert.setHeaderText((String)null);
            this.alert.setContentText("Please fill all blank fields");
            this.alert.showAndWait();
        }

    }
    public void menuClearBtn() {
        this.menu_idProduk.setText("");
        this.menu_menu.setText("");
        this.menu_kategori.getSelectionModel().clearSelection();
        this.menu_ukuran.getSelectionModel().clearSelection();
        this.menu_harga.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.menuSizeList();
        this.menuKategoriList();
        this.initializeMenu();
    }
}
