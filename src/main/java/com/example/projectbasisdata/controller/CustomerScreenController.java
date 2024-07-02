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
import com.example.projectbasisdata.model.Customer;
import com.example.projectbasisdata.model.DetailMenu;
import com.example.projectbasisdata.model.Menu;
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

public class CustomerScreenController implements Initializable {
    @FXML
    private AnchorPane customer_form;
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
    private TableView<Customer> customer_tableView;
    @FXML
    private TableColumn<Customer, Integer> customer_col_idCustomer;
    @FXML
    private TableColumn<Customer, String> customer_col_namaCustomer;
    @FXML
    private TableColumn<Customer, String> customer_col_jenisCustomer;
    @FXML
    private TableColumn<Customer, Integer> customer_col_totalPoint;
    @FXML
    private TextField customer_idCustomer;
    @FXML
    private TextField customer_nama;
    @FXML
    private Button customer_addBtn;
    @FXML
    private Button customer_updateBtn;
    @FXML
    private Button customer_deleteBtn;
    @FXML
    private Button customer_clearBtn;
    @FXML
    private ComboBox<?> customer_jenis;
    @FXML
    private TextField customer_totalPoint;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private ObservableList<Customer> customerList;
    private Statement statement;
    private Alert alert;
    private String[] jenisList = new String[]{"UMUM", "MEMBER"};

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
    @FXML
    public void promoClick() throws IOException {
        MainApp.setRoot("PromoScreen");
    }

    //untuk mendapatkan semua data dari customer kemudian dijadikan array bentuk list
    public ObservableList<Customer> customerDataList() throws SQLException {
        ObservableList<Customer> listData = FXCollections.observableArrayList();
        String sql = "select * from customer order by customer_id";
        this.connect = DatabaseConnection.getConnection();
        try {
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();

            while (this.result.next()) {
                Customer customer = new Customer(this.result.getInt("customer_id"), this.result.getString("customer_name"), this.result.getString("jenis_customer"),
                        this.result.getInt("member_total_point"));
                listData.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    //digunakan untuk menampilkan data
    public void customerShowData() throws SQLException {
        this.customerList = this.customerDataList();
        this.customer_col_idCustomer.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        this.customer_col_namaCustomer.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        this.customer_col_jenisCustomer.setCellValueFactory(new PropertyValueFactory<>("jenis_customer"));
        this.customer_col_totalPoint.setCellValueFactory(new PropertyValueFactory<>("member_total_point"));
        this.customer_tableView.setItems(this.customerList);
    }

    //untuk combo box
    public void customerJenisList() {
        List<String> jenisL = new ArrayList<>();
        String[] var2 = this.jenisList;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String data = var2[var4];
            jenisL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(jenisL);
        this.customer_jenis.setItems(listData);
    }
    //untuk menambahkan customer yang bertipe "UMUM"/"MEMBER"
    public void customerAddBtn() throws SQLException {
        if (!this.customer_idCustomer.getText().isEmpty() && !this.customer_nama.getText().isEmpty() && this.customer_jenis.getSelectionModel().getSelectedItem() != null) {
            String checkCustomerID = "SELECT customer_id FROM customer WHERE customer_id = " + this.customer_idCustomer.getText();
            this.connect = DatabaseConnection.getConnection();

            try {
                this.statement = this.connect.createStatement();
                this.result = this.statement.executeQuery(checkCustomerID);
                if (this.result.next()) {
                    this.alert = new Alert(AlertType.ERROR);
                    this.alert.setTitle("Message");
                    this.alert.setHeaderText((String) null);
                    this.alert.setContentText("ID customer " + this.customer_idCustomer.getText() + " sudah digunakan");
                    this.alert.showAndWait();
                } else {
                    String insertData = "insert into customer values(?, ?, ?, ?)";
                    this.prepare = this.connect.prepareStatement(insertData);
                    this.prepare.setInt(1, Integer.parseInt(this.customer_idCustomer.getText()));
                    this.prepare.setString(2, this.customer_nama.getText());
                    this.prepare.setString(3, (String) this.customer_jenis.getSelectionModel().getSelectedItem());
                    if (this.customer_totalPoint.getText().isEmpty()) {
                        this.prepare.setInt(4, 0);
                    }
                    else {
                        this.prepare.setInt(4, Integer.parseInt(this.customer_totalPoint.getText()));
                    }
                    this.prepare.executeUpdate();
                    String insertData2 = "update customer\n" +
                            "set member_total_point = 0 where jenis_customer = 'UMUM' or (jenis_customer='MEMBER' and member_total_point is null)";
                    this.prepare = this.connect.prepareStatement(insertData2);
                    this.prepare.executeUpdate();
                    this.alert = new Alert(AlertType.INFORMATION);
                    this.alert.setTitle("Message");
                    this.alert.setHeaderText((String) null);
                    this.alert.setContentText("Successfully Added!");
                    this.alert.showAndWait();
                    this.customerShowData();
                    this.customerClearBtn();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.alert = new Alert(AlertType.ERROR);
            this.alert.setTitle("Message");
            this.alert.setHeaderText((String) null);
            this.alert.setContentText("Please fill all blank fields");
            this.alert.showAndWait();
        }
    }

    //clear semua
    public void customerClearBtn() {
        this.customer_idCustomer.setText("");
        this.customer_nama.setText("");
        this.customer_jenis.getSelectionModel().clearSelection();
        this.customer_totalPoint.setText("");
    }

    //mengupdate value yang ada di db customer
    public void customerUpdateBtn() throws SQLException {
        if (!this.customer_idCustomer.getText().isEmpty() && !this.customer_nama.getText().isEmpty() && this.customer_jenis.getSelectionModel().getSelectedItem() != null) {
            this.connect = DatabaseConnection.getConnection();
            try {
                String insertData = "UPDATE customer\n" +
                        "SET customer_name = ?,\n" +
                        "\tjenis_customer = ?,  \n" +
                        "\tmember_total_point = ?  \n" +
                        "WHERE customer_id = ?";
                this.prepare = this.connect.prepareStatement(insertData);
                this.prepare.setString(1, this.customer_nama.getText());
                this.prepare.setString(2, (String) this.customer_jenis.getSelectionModel().getSelectedItem());
                if (this.customer_totalPoint.getText().isEmpty()) {
                    this.prepare.setInt(3, 0);
                }
                else {
                    this.prepare.setInt(3, Integer.parseInt(this.customer_totalPoint.getText()));
                }
                this.prepare.setInt(4, Integer.parseInt(this.customer_idCustomer.getText()));
                this.prepare.executeUpdate();
                String insertData2 = "update customer\n" +
                        "set member_total_point = 0 where jenis_customer = 'UMUM' or (jenis_customer='MEMBER' and member_total_point is null)";
                this.prepare = this.connect.prepareStatement(insertData2);
                this.prepare.executeUpdate();
                this.alert = new Alert(AlertType.INFORMATION);
                this.alert.setTitle("Message");
                this.alert.setHeaderText((String)null);
                this.alert.setContentText("Successfully Updated!");
                this.alert.showAndWait();
                this.customerShowData();
                this.customerClearBtn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.alert = new Alert(AlertType.ERROR);
            this.alert.setTitle("Message");
            this.alert.setHeaderText((String)null);
            this.alert.setContentText("Please fill all blank fields");
            this.alert.showAndWait();
        }
    }
    @FXML
    public void customerDeleteBtn() {
        Customer selectedCustomer = customer_tableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer!= null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String sql = "DELETE FROM customer WHERE customer_id =?";
                try {
                    this.connect = DatabaseConnection.getConnection();
                    this.prepare = this.connect.prepareStatement(sql);
                    this.prepare.setInt(1, selectedCustomer.getCustomer_id());
                    this.prepare.executeUpdate();

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Customer Deleted");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Customer has been deleted successfully!");
                    successAlert.showAndWait();

                    customerShowData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Customer Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a customer to delete!");
            alert.showAndWait();
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.customerShowData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.customerJenisList();
    }
}
