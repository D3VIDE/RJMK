package com.example.projectbasisdata.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;

import com.example.projectbasisdata.DatabaseConnection;
import com.example.projectbasisdata.MainApp;
import com.example.projectbasisdata.model.DetailMenu;
import com.example.projectbasisdata.model.Order;
import com.example.projectbasisdata.model.Promo;
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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PromoScreenController implements Initializable {
    @FXML
    private AnchorPane promo_form;
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
    private TableView<Promo> promo_tableView;
    @FXML
    private TableColumn<Promo, Integer> promo_col_idPromo;
    @FXML
    private TableColumn<Promo, String> promo_col_namaPromo;
    @FXML
    private TableColumn<Promo, Double> promo_col_nominalPromo;
    @FXML
    private TableColumn<Promo, Date> promo_col_tanggalBerlaku;
    @FXML
    private TableColumn<Promo, Date> promo_col_tanggalBerakhir;
    @FXML
    private TableColumn<Promo, Integer> promo_col_kategori;
    @FXML
    private TableColumn<Promo, Integer> promo_col_menu;
    @FXML
    private TableColumn<Promo, Integer> promo_col_metode;
    @FXML
    private TextField promo_idPromo;
    @FXML
    private TextField promo_namaPromo;
    @FXML
    private Button promo_addBtn;
    @FXML
    private Button promo_updateBtn;
    @FXML
    private Button promo_deleteBtn;
    @FXML
    private Button promo_clearBtn;
    @FXML
    private TextField promo_nominal;
    @FXML
    private DatePicker promo_tanggalBerlaku;
    @FXML
    private DatePicker promo_tanggalBerakhir;
    @FXML
    private ComboBox<?> promo_kategori;
    @FXML
    private ComboBox<?> promo_menu;
    @FXML
    private ComboBox<?> promo_metode;
    private ObservableList<Promo> promoList;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private String[] kategoriList = new String[]{"Coffee", "Cream", "Add Ons"};
    private String[] metodeList = new String[]{"Cash", "QRIS", "Debit"};
    @FXML
    public void dashboardClick() throws IOException {
        MainApp.setRoot("mainScreen");
    }
    @FXML
    public void menuClick() throws IOException {
        MainApp.setRoot("menuScreen");
    }
    @FXML
    public void transaksiClick() throws IOException {
        MainApp.setRoot("transaksiScreen");
    }
    public List<String> getData() throws SQLException {
        List<String> data = new ArrayList<>();
        String query = null;
        if (this.promo_kategori.getSelectionModel().getSelectedItem()=="Coffee") {
            query = "select m.menu_name \n" +
                    "from promo p right join menu m ON p.menu_id = m.menu_id\n" +
                    "where m.kategori_id = (select kategori_id\n" +
                    "\t\t\t\t\t  from kategori\n" +
                    "\t\t\t\t\t  where kategori_name='COFFEE')";
        }
        else if (this.promo_kategori.getSelectionModel().getSelectedItem()=="Cream") {
            query = "select m.menu_name \n" +
                    "from promo p right join menu m ON p.menu_id = m.menu_id\n" +
                    "where m.kategori_id = (select kategori_id\n" +
                    "\t\t\t\t\t  from kategori\n" +
                    "\t\t\t\t\t  where kategori_name='CREAM')";
        }
        else if (this.promo_kategori.getSelectionModel().getSelectedItem()=="Add Ons") {
            query = "select m.menu_name \n" +
                    "from promo p right join menu m ON p.menu_id = m.menu_id\n" +
                    "where m.kategori_id = (select kategori_id\n" +
                    "\t\t\t\t\t  from kategori\n" +
                    "\t\t\t\t\t  where kategori_name='ADD ONS')";
        }
        this.connect = DatabaseConnection.getConnection();
        try {
             PreparedStatement statement = this.connect.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                data.add(resultSet.getString("menu_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ObservableList<Promo> promoDataList() throws SQLException {
        ObservableList<Promo> listData = FXCollections.observableArrayList();
        String sql = "SELECT p.promo_id, p.promo_name, p.promo_nominal, p.date_start, p.date_end, k.kategori_name, m.menu_name, pm.method_name\n" +
                "FROM promo p\n" +
                "LEFT JOIN kategori k ON p.kategori_id = k.kategori_id\n" +
                "LEFT JOIN menu m ON p.menu_id = m.menu_id\n" +
                "LEFT JOIN payment_method pm ON p.method_id = pm.method_id";
        this.connect = DatabaseConnection.getConnection();
        try {
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();

            while(this.result.next()) {
                Promo promo = new Promo(this.result.getInt("promo_id"), this.result.getString("promo_name"), this.result.getDouble("promo_nominal"), this.result.getDate("date_start"), this.result.getDate("date_end"), this.result.getString("kategori_name"),this.result.getString("menu_name"), this.result.getString("method_name"));
                listData.add(promo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    public void promoKategoriList() {
        List<String> kategoriL = new ArrayList<>();
        String[] var2 = this.kategoriList;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String data = var2[var4];
            kategoriL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(kategoriL);
        this.promo_kategori.setItems(listData);
    }
    public void promoMetodeList() {
        List<String> metodeL = new ArrayList<>();
        String[] var2 = this.metodeList;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String data = var2[var4];
            metodeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(metodeL);
        this.promo_metode.setItems(listData);
    }
    public void promoMenuList() throws SQLException {
        List<String> menuL = this.getData();

        ObservableList listData = FXCollections.observableArrayList(menuL);
        this.promo_menu.setItems(listData);
    }

    public void promoShowData() throws SQLException {
        this.promoList = this.promoDataList();
        this.promo_col_idPromo.setCellValueFactory(new PropertyValueFactory<>("promo_id"));
        this.promo_col_namaPromo.setCellValueFactory(new PropertyValueFactory<>("promo_name"));
        this.promo_col_nominalPromo.setCellValueFactory(new PropertyValueFactory<>("promo_nominal"));
        this.promo_col_tanggalBerlaku.setCellValueFactory(new PropertyValueFactory<>("date_start"));
        this.promo_col_tanggalBerakhir.setCellValueFactory(new PropertyValueFactory<>("date_end"));
        this.promo_col_kategori.setCellValueFactory(new PropertyValueFactory<>("kategori_name"));
        this.promo_col_menu.setCellValueFactory(new PropertyValueFactory<>("menu_name"));
        this.promo_col_metode.setCellValueFactory(new PropertyValueFactory<>("method_name"));
        this.promo_tableView.setItems(this.promoList);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.promoShowData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.promoKategoriList();
        this.promoMetodeList();


    }
}
