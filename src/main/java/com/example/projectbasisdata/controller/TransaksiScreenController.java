package com.example.projectbasisdata.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import com.example.projectbasisdata.controller.BingkaiProductController;
import com.example.projectbasisdata.DatabaseConnection;
import com.example.projectbasisdata.MainApp;
import com.example.projectbasisdata.model.Customer;
import com.example.projectbasisdata.model.DetailMenu;
import com.example.projectbasisdata.model.Order;
import com.example.projectbasisdata.model.Temp_order;
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

public class TransaksiScreenController implements Initializable {
    @FXML
    private AnchorPane transaksi_form;
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
    private GridPane menu_gridPane;
    @FXML
    private TableView<Order> transaksi_tableView;
    @FXML
    private TableColumn<Order, String> transaksi_col_namaProduk;
    @FXML
    private TableColumn<Order, Integer> transaksi_col_jumlah;
    @FXML
    private TableColumn<Order, Integer> transaksi_col_harga;
    @FXML
    private TableColumn<Order, String> transaksi_col_size;
    @FXML
    private Label transaksi_labelTotal;
    @FXML
    private TextField transaksi_jumlahBayar;
    @FXML
    private Label transaksi_labelKembalian;
    @FXML
    private Button transaksi_bayarBtn;
    @FXML
    private Button transaksi_deleteBtn;
    @FXML
    private Button transaksi_strukBtn;
    @FXML
    private Button transaksi_clear;
    @FXML
    private TextField transaksi_NamaCustomer;

    @FXML
    private AnchorPane menu_Form2;

    @FXML
    private ScrollPane transaksi_ScrollPane;


    private  ObservableList<DetailMenu> bingkaiListData = FXCollections.observableArrayList();
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private ObservableList<Temp_order> transaksiList;

    public ObservableList<DetailMenu> menuGetData(){
        String query = "SELECT dm.detailmenu_id, k.kategori_name, m.menu_name, s.size_name, dm.harga_nominal " +
                "FROM detail_menu dm " +
                "JOIN menu m ON dm.menu_id = m.menu_id " +
                "JOIN kategori k ON m.kategori_id = k.kategori_id " +
                "JOIN size s ON dm.size_id = s.size_id";
        ObservableList<DetailMenu> listData = FXCollections.observableArrayList();
        try{
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement prepare = conn.prepareStatement(query);
            ResultSet result = prepare.executeQuery();
            DetailMenu detail;
            while (result.next()){
                detail = new DetailMenu(result.getInt("detailmenu_id"),
                        result.getString("kategori_name"),
                        result.getString("menu_name"),
                        result.getString("size_name"),
                        result.getInt("harga_nominal"));
                listData.add(detail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void menuDisplayCard(){
        bingkaiListData.clear();
        bingkaiListData.addAll(menuGetData());
        int row = 0;
        int col = 0;
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();
        for(int i = 0;i<bingkaiListData.size();i++){
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/com/example/projectbasisdata/bingkaiProduct.fxml"));
                AnchorPane pane = load.load();
                BingkaiProductController bingkaiS = load.getController();
                bingkaiS.setData(bingkaiListData.get(i));
                if(col == 3){
                    col = 0;
                    row += 1;
                }
                menu_gridPane.add(pane,col++,row);
                GridPane.setMargin(pane,new Insets(10));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public ObservableList<Temp_order> transaksiDataList() throws SQLException {
        ObservableList<Temp_order> listData = FXCollections.observableArrayList();
        String sql = "select menu_name, quantity, harga_nominal*quantity, size_name\n" +
                "from temp_order o join detail_menu dm on o.detailmenu_id=dm.detailmenu_id\n" +
                "join menu m on dm.menu_id=m.menu_id\n" +
                "join size s on dm.size_id=s.size_id";
        this.connect = DatabaseConnection.getConnection();
        try {
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();

            while (this.result.next()) {
                Temp_order temp = new Temp_order(this.result.getInt("temp_number"), this.result.getDate("order_date"), this.result.getInt("quantity"),
                        this.result.getInt("detailmenu_id"));
                listData.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    public void transaksiShowData() throws SQLException {
        this.transaksiList = this.transaksiDataList();
        this.transaksi_col_namaProduk.setCellValueFactory(new PropertyValueFactory<>("menu_name"));
        this.transaksi_col_jumlah.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        this.transaksi_col_harga.setCellValueFactory(new PropertyValueFactory<>("?column?"));
        this.transaksi_col_size.setCellValueFactory(new PropertyValueFactory<>("size_name"));
    }

    @FXML
    public void dashboardClick() throws IOException {
        MainApp.setRoot("mainScreen");
    }
    @FXML
    public void menuClick() throws IOException {
        MainApp.setRoot("menuScreen");
    }
    @FXML
    public void promoClick() throws IOException {
        MainApp.setRoot("PromoScreen");
    }
    @FXML
    public void customerClick() throws IOException {
        MainApp.setRoot("customerScreen");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuDisplayCard();
    }
}
