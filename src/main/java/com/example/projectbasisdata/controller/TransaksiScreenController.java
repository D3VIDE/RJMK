package com.example.projectbasisdata.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.projectbasisdata.DatabaseConnection;
import com.example.projectbasisdata.MainApp;
import com.example.projectbasisdata.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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
    private TableView<Temp_order> transaksi_tableView;
    @FXML
    private TableColumn<Temp_order, String> transaksi_col_namaProduk;
    @FXML
    private TableColumn<Temp_order, Integer> transaksi_col_jumlah;
    @FXML
    private TableColumn<Temp_order, Integer> transaksi_col_harga;
    @FXML
    private TableColumn<Temp_order, String> transaksi_col_size;
    @FXML
    private TableColumn<Temp_order, String> transaksi_col_kategori;
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
    @FXML
    private ComboBox<String> transaksi_metode;


    private  ObservableList<DetailMenu> bingkaiListData = FXCollections.observableArrayList();
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private ObservableList<Temp_order> transaksiList;
    private Alert alert;
    private String[] metodeList = new String[]{"Cash", "QRIS", "Debit"};

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
                bingkaiS.setTransaksiScreen(this);
                menu_gridPane.add(pane,col++,row);
                GridPane.setMargin(pane,new Insets(10));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public ObservableList<Temp_order> transaksiDataList() throws SQLException {
        ObservableList<Temp_order> listData = FXCollections.observableArrayList();
        String sql = "select menu_name, quantity, harga_nominal, size_name, kategori_name \n" +
                "                from temp_order o join detail_menu dm on o.detailmenu_id=dm.detailmenu_id \n" +
                "                join menu m on dm.menu_id=m.menu_id\n" +
                "                join size s on dm.size_id=s.size_id\n" +
                "\t\t\t\tjoin kategori k on k.kategori_id=m.kategori_id";
        this.connect = DatabaseConnection.getConnection();
        try {
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();

            while (this.result.next()) {
                Temp_order temp = new Temp_order(
                        this.result.getString("menu_name"),
                        this.result.getInt("quantity"),
                        this.result.getInt("harga_nominal"),
                        this.result.getString("size_name"),
                        this.result.getString("kategori_name")
                );
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
        this.transaksi_col_harga.setCellValueFactory(new PropertyValueFactory<>("harga_nominal"));
        this.transaksi_col_size.setCellValueFactory(new PropertyValueFactory<>("size_name"));
        this.transaksi_col_kategori.setCellValueFactory(new PropertyValueFactory<>("kategori_name"));
        this.transaksi_tableView.setItems(transaksiList);

        int total = 0;
        for (Temp_order order : transaksiList) {
            total += order.getTotal_price();
        }
        transaksi_labelTotal.setText("$" + total);
    }
    public void transaksiClearBtn() throws SQLException {
        this.transaksi_jumlahBayar.setText("");
        this.transaksi_NamaCustomer.setText("");
        this.transaksi_metode.getSelectionModel().clearSelection();
        this.transaksi_labelTotal.setText("$0");
        this.transaksi_labelKembalian.setText("$0");
        this.connect = DatabaseConnection.getConnection();
        try {
            String insertData = "delete from temp_order";
            this.prepare = this.connect.prepareStatement(insertData);
            this.prepare.executeUpdate();
            String insertData2 = "delete from \"order\"\n" +
                    "where checkclear='TEMP'";
            this.prepare = this.connect.prepareStatement(insertData2);
            this.prepare.executeUpdate();
            this.transaksiShowData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void transaksiDeleteBtn() {
        Temp_order selectedOrder = transaksi_tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder!= null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Order");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this order?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String sql = "delete from temp_order\n" +
                        "where quantity = ? and detailmenu_id = (select detailmenu_id from detail_menu where harga_nominal = ? and menu_id=(select menu_id from menu where menu_name=?) and size_id=(select size_id from size where size_name=?) )";
                try {
                    this.connect = DatabaseConnection.getConnection();
                    this.prepare = this.connect.prepareStatement(sql);
                    this.prepare.setInt(1, selectedOrder.getQuantity());
                    this.prepare.setInt(2, selectedOrder.getHarga_nominal());
                    this.prepare.setString(3, selectedOrder.getMenu_name());
                    this.prepare.setString(4, selectedOrder.getSize_name());
                    this.prepare.executeUpdate();

                    String sql2 = "delete from \"order\"\n" +
                            "where checkclear='TEMP' and quantity = ? and detailmenu_id = (select detailmenu_id from detail_menu where harga_nominal = ? and menu_id=(select menu_id from menu where menu_name=?) and size_id=(select size_id from size where size_name=?) )";
                    this.prepare = this.connect.prepareStatement(sql2);
                    this.prepare.setInt(1, selectedOrder.getQuantity());
                    this.prepare.setInt(2, selectedOrder.getHarga_nominal());
                    this.prepare.setString(3, selectedOrder.getMenu_name());
                    this.prepare.setString(4, selectedOrder.getSize_name());
                    this.prepare.executeUpdate();
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Order Deleted");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Order has been deleted successfully!");
                    successAlert.showAndWait();

                    transaksiShowData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Order Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an order to delete!");
            alert.showAndWait();
        }

    }

    public void deleteData() throws SQLException{
        String deleteSQL = "DELETE FROM temp_order";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement prepare = conn.prepareStatement(deleteSQL)) {
            int deletedRows = prepare.executeUpdate();
            System.out.println("Deleted rows: " + deletedRows); // Check how many rows were deleted
            transaksiList.clear();
            transaksi_tableView.setItems(transaksiList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, show an error dialog or log the exception to further diagnose the issue
        }
    }

    public void doTransaksi(){
        int totalHarga = Integer.parseInt(transaksi_labelTotal.getText().replace("$", ""));
        int jumlahBayar = Integer.parseInt(transaksi_jumlahBayar.getText());
        int kembalian = jumlahBayar - totalHarga;
        transaksi_labelKembalian.setText("$" + kembalian);

        // Retrieve customer ID based on the customer name entered
        String customerName = transaksi_NamaCustomer.getText();
        int customerId = getCustomerIdByName(customerName);

        // Retrieve selected payment method ID
        String selectedMethod = transaksi_metode.getSelectionModel().getSelectedItem();
        int methodId = getPaymentMethodIdByName(selectedMethod);

        Temp_order selectedOrder = transaksi_tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            Promo promo = getPromoForKategori(selectedOrder.getKategori_name());
            if (promo != null) {
                totalHarga -= (int) promo.getPromo_nominal();
                Alert promoAlert = new Alert(Alert.AlertType.INFORMATION);
                promoAlert.setTitle("Promotion Applied");
                promoAlert.setHeaderText(null);
                promoAlert.setContentText("Promotion '" + promo.getPromo_name() + "' applied! New total: $" + totalHarga);
                promoAlert.showAndWait();
            }
        }

        // Insert data into detail_order table
        String insertQuery = "INSERT INTO detail_order (harga_total, harga_bayar, kembalian, customer_id, method_id) VALUES (?, ?, ?, ?, ?)";

        try {
            connect = DatabaseConnection.getConnection();
            prepare = connect.prepareStatement(insertQuery);
            prepare.setInt(1, totalHarga);
            prepare.setInt(2, jumlahBayar);
            prepare.setInt(3, kembalian);
            prepare.setInt(4, customerId);
            prepare.setInt(5, methodId);
            prepare.executeUpdate();

            String query = "update \"order\"\n" +
                    "set checkclear='FINISH'\n" +
                    "where checkclear='TEMP'";
            this.prepare = this.connect.prepareStatement(query);
            this.prepare.executeUpdate();

            int pointEarned = totalHarga;

            //add point to customer//
            addPointsToCustomer(customerId,pointEarned);

            //clear//
            transaksiClearBtn();

            // Show success message
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Transaction Successful");
            alert.setHeaderText(null);
            alert.setContentText("The transaction was completed successfully!");
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Transaction Failed");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while processing the transaction. Please try again.");
            alert.showAndWait();
        }
        if (selectedMethod == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Payment Method Not Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a payment method!");
            alert.showAndWait();
            return;
        }

        if (methodId == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Payment Method Not Found");
            alert.setHeaderText(null);
            alert.setContentText("Please select a valid payment method!");
            alert.showAndWait();
            return;
        }


    }

    //promo (not done)
    private Promo getPromoForKategori(String kategoriName) {
        String query = "SELECT p.promo_name, p.promo_nominal, p.date_start, p.date_end, k.kategori_name\n" +
                "                FROM promo p\n" +
                "                JOIN kategori k ON p.kategori_id = k.kategori_id\n" +
                "where k.kategori_name=? and ? BETWEEN p.date_start AND p.date_end";
        Promo promo = null;
        try {
            connect = DatabaseConnection.getConnection();
            prepare = connect.prepareStatement(query);
            prepare.setString(1, kategoriName);
            prepare.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            result = prepare.executeQuery();
            if (result.next()) {
                promo = new Promo(
                        result.getString("promo_name"),
                        result.getDouble("promo_nominal"),
                        result.getDate("date_start"),
                        result.getDate("date_end"),
                        result.getString("kategori_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promo;
    }

    private Promo getPromoForMenu(String kategoriName, String menuName, String methodName) {
        String query = "SELECT p.promo_name, p.promo_nominal, p.date_start, p.date_end, k.kategori_name, m.menu_name, pm.method_name " +
                "FROM promo p " +
                "JOIN kategori k ON p.kategori_id = k.kategori_id " +
                "JOIN menu m ON p.menu_id = m.menu_id " +
                "JOIN payment_method pm ON p.method_id = pm.method_id " +
                "WHERE (k.kategori_name = ? OR m.menu_name = ? OR pm.method_name = ?) AND ? BETWEEN p.date_start AND p.date_end";
        Promo promo = null;
        try {
            connect = DatabaseConnection.getConnection();
            prepare = connect.prepareStatement(query);
            prepare.setString(1, kategoriName);
            prepare.setString(2, menuName);
            prepare.setString(3, methodName);
            prepare.setDate(4, Date.valueOf(java.time.LocalDate.now()));
            result = prepare.executeQuery();
            if (result.next()) {
                promo = new Promo(
                        result.getString("promo_name"),
                        result.getDouble("promo_nominal"),
                        result.getDate("date_start"),
                        result.getDate("date_end"),
                        result.getString("kategori_name"),
                        result.getString("menu_name"),
                        result.getString("method_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promo;
    }
    private Promo getPromoForMethod(String kategoriName, String menuName, String methodName) {
        String query = "SELECT p.promo_name, p.promo_nominal, p.date_start, p.date_end, k.kategori_name, m.menu_name, pm.method_name " +
                "FROM promo p " +
                "JOIN kategori k ON p.kategori_id = k.kategori_id " +
                "JOIN menu m ON p.menu_id = m.menu_id " +
                "JOIN payment_method pm ON p.method_id = pm.method_id " +
                "WHERE (k.kategori_name = ? OR m.menu_name = ? OR pm.method_name = ?) AND ? BETWEEN p.date_start AND p.date_end";
        Promo promo = null;
        try {
            connect = DatabaseConnection.getConnection();
            prepare = connect.prepareStatement(query);
            prepare.setString(1, kategoriName);
            prepare.setString(2, menuName);
            prepare.setString(3, methodName);
            prepare.setDate(4, Date.valueOf(java.time.LocalDate.now()));
            result = prepare.executeQuery();
            if (result.next()) {
                promo = new Promo(
                        result.getString("promo_name"),
                        result.getDouble("promo_nominal"),
                        result.getDate("date_start"),
                        result.getDate("date_end"),
                        result.getString("kategori_name"),
                        result.getString("menu_name"),
                        result.getString("method_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promo;
    }


    //method untuk mendapatkan Na,e
    private int getPaymentMethodIdByName(String methodName) {
        String query = "SELECT method_id FROM payment_method WHERE method_name = ?";
        int methodId = -1;
        try {
            connect = DatabaseConnection.getConnection();
            prepare = connect.prepareStatement(query);
            prepare.setString(1, methodName);
            result = prepare.executeQuery();
            if (result.next()) {
                methodId = result.getInt("method_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return methodId;
    }

    //Method untuk mendapatkan cust ID
    private int getCustomerIdByName(String customerName) {
        //
        String selectQuery = "SELECT customer_id FROM customer WHERE customer_name = ?";
        String insertQuery = "INSERT INTO customer (customer_name, jenis_customer, member_total_point) VALUES (?, ?, ?)";
        int customerId = -1;

        try {
            connect = DatabaseConnection.getConnection();

            // Check if customer exists
            prepare = connect.prepareStatement(selectQuery);
            prepare.setString(1, customerName);
            result = prepare.executeQuery();

            if (result.next()) {
                customerId = result.getInt("customer_id");
            } else {
                // Insert new customer if not found
                prepare = connect.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                prepare.setString(1, customerName);
                prepare.setString(2, "UMUM");
                prepare.setInt(3, 0);
                prepare.executeUpdate();

                // Retrieve the generated customer_id
                result = prepare.getGeneratedKeys();
                if (result.next()) {
                    customerId = result.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerId;
    }
    private void updateTotalLabel(int newTotal) {
        transaksi_labelTotal.setText("$" + newTotal);
    }

    //Method add points
    private void addPointsToCustomer(int customerId, int points) {
        String updateQuery = "UPDATE customer SET member_total_point = member_total_point + ? WHERE customer_id = ?";
        try {
            connect = DatabaseConnection.getConnection();
            prepare = connect.prepareStatement(updateQuery);
            prepare.setInt(1, points);
            prepare.setInt(2, customerId);
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void transaksiMetodeList() {
        List<String> metodeL = Arrays.asList(metodeList);
        ObservableList<String> listData = FXCollections.observableArrayList(metodeL);
        this.transaksi_metode.setItems(listData);
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
        try {
            transaksiShowData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.transaksiMetodeList();
    }
}
