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

import com.example.projectbasisdata.DatabaseConnection;
import com.example.projectbasisdata.MainApp;
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

public class MainScreenController implements Initializable {

    @FXML
    private AnchorPane main_form;
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
    private AnchorPane dashboard_form;
    @FXML
    private AreaChart<?, ?> dashboard_incomeChart;
    @FXML
    private AreaChart<?, ?> dashboard_customerChart;
    @FXML
    private Label dashboard_customerNumber;
    @FXML
    private Label dashboard_activeMember;
    @FXML
    private Label dashboard_bestSeller;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    public void menuClick() throws IOException {
        MainApp.setRoot("menuScreen");
    }
    @FXML
    public void transaksiClick() throws IOException {
        MainApp.setRoot("transaksiScreen");
    }
    @FXML
    public void promoClick() throws IOException {
        MainApp.setRoot("PromoScreen");
    }
    @FXML
    public void customerClick() throws IOException {
        MainApp.setRoot("customerScreen");
    }

    /*
    Dashboard Utama Yang Menampikan Semua Report
     */
    public void dashboardDisplayCustomer() throws SQLException {
        String sql = "SELECT COUNT(customer_id) FROM customer";
        this.connect = DatabaseConnection.getConnection();

        try {
            int nc = 0;
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                nc = this.result.getInt("count");
            }
            this.dashboard_customerNumber.setText(String.valueOf(nc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void dashboardDisplayMemberActive() throws SQLException {
        String sql = "select customer_name from customer\n" +
                "where jenis_customer='MEMBER'\n" +
                "order by member_total_point desc\n" +
                "limit 1";
        this.connect = DatabaseConnection.getConnection();

        try {
            String member = "None";
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                member = this.result.getString("customer_name");
            }
            this.dashboard_activeMember.setText(member);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void dashboardBestSeller() throws SQLException {
        String sql = "select menu_name\n" +
                "from \"order\" o join detail_menu dm on o.detailmenu_id=dm.detailmenu_id\n" +
                "join menu m on dm.menu_id=m.menu_id\n" +
                "group by menu_name, quantity\n" +
                "order by count(*)*quantity desc\n" +
                "limit 1\n";
        this.connect = DatabaseConnection.getConnection();

        try {
            String menu = "None";
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                menu = this.result.getString("menu_name");
            }
            this.dashboard_bestSeller.setText(menu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.dashboardDisplayCustomer();
            this.dashboardDisplayMemberActive();
            this.dashboardBestSeller();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
