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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
