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

    @FXML
    public void dashboardClick() throws IOException {
        MainApp.setRoot("mainScreen");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
