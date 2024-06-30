package com.example.projectbasisdata.controller;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;
import java.time.LocalDate;
import com.example.projectbasisdata.DatabaseConnection;
import com.example.projectbasisdata.MainApp;
import com.example.projectbasisdata.model.Promo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

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
    private ComboBox<String> promo_kategori;
    @FXML
    private ComboBox<String> promo_menu;
    @FXML
    private ComboBox<String> promo_metode;
    private ObservableList<Promo> promoList;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private String[] kategoriList = new String[]{"COFFEE", "CREAM", "ADD ONS"};
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

    public List<String> getData(String kategori) throws SQLException {
        List<String> data = new ArrayList<>();
        String query = null;
        switch (kategori) {
            case "COFFEE":
                query = "select m.menu_name from menu m where m.kategori_id = 1";
                break;
            case "CREAM":
                query = "select m.menu_name from menu m where m.kategori_id = 2";
                break;
            case "ADD ONS":
                query = "select m.menu_name from menu m where m.kategori_id = 3";
                break;
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
        String sql = "SELECT p.promo_id, p.promo_name, p.promo_nominal, p.date_start, p.date_end, k.kategori_name, m.menu_name, pm.method_name " +
                "FROM promo p " +
                "LEFT JOIN kategori k ON p.kategori_id = k.kategori_id " +
                "LEFT JOIN menu m ON p.menu_id = m.menu_id " +
                "LEFT JOIN payment_method pm ON p.method_id = pm.method_id";
        this.connect = DatabaseConnection.getConnection();
        try {
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();

            while (this.result.next()) {
                Promo promo = new Promo(this.result.getInt("promo_id"), this.result.getString("promo_name"), this.result.getDouble("promo_nominal"),
                        this.result.getDate("date_start"), this.result.getDate("date_end"), this.result.getString("kategori_name"),
                        this.result.getString("menu_name"), this.result.getString("method_name"));
                listData.add(promo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void promoKategoriList() {
        List<String> kategoriL = Arrays.asList(kategoriList);
        ObservableList<String> listData = FXCollections.observableArrayList(kategoriL);
        this.promo_kategori.setItems(listData);
    }

    public void promoMetodeList() {
        List<String> metodeL = Arrays.asList(metodeList);
        ObservableList<String> listData = FXCollections.observableArrayList(metodeL);
        this.promo_metode.setItems(listData);
    }

    public void promoMenuList() throws SQLException {
        String selectedKategori = promo_kategori.getSelectionModel().getSelectedItem();
        if (selectedKategori != null) {
            List<String> menuL = this.getData(selectedKategori);
            ObservableList<String> listData = FXCollections.observableArrayList(menuL);
            this.promo_menu.setItems(listData);
        }
    }
    @FXML
    void addPromo(ActionEvent event) {
        String promoId = promo_idPromo.getText();
        String promoName = promo_namaPromo.getText();
        String promoNominal = promo_nominal.getText();
        LocalDate dateStart = promo_tanggalBerlaku.getValue();
        LocalDate dateEnd = promo_tanggalBerakhir.getValue();
        String selectedKategori = promo_kategori.getSelectionModel().getSelectedItem();
        String selectedMenu = promo_menu.getSelectionModel().getSelectedItem();
        String selectedMethod = promo_metode.getSelectionModel().getSelectedItem();

        // Validate data
        if (promoId.isEmpty() || promoName.isEmpty() || promoNominal.isEmpty() || dateStart == null || dateEnd == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Form Incomplete");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields!");
            alert.showAndWait();
            return;
        }

        // Insert data into the database
        String sql = "INSERT INTO promo (promo_id, promo_name, promo_nominal, date_start, date_end, kategori_id, menu_id, method_id) " +
                "VALUES (?, ?, ?, ?, ?, " +
                "(SELECT kategori_id FROM kategori WHERE kategori_name = ?), " +
                "(SELECT menu_id FROM menu WHERE menu_name = ?), " +
                "(SELECT method_id FROM payment_method WHERE method_name = ?))";



        try {
            this.connect = DatabaseConnection.getConnection();
            this.prepare = this.connect.prepareStatement(sql);
            this.prepare.setInt(1, Integer.parseInt(promoId));
            this.prepare.setString(2, promoName);
            this.prepare.setDouble(3, Double.parseDouble(promoNominal));
            this.prepare.setDate(4, java.sql.Date.valueOf(dateStart));
            this.prepare.setDate(5, java.sql.Date.valueOf(dateEnd));
            this.prepare.setString(6, selectedKategori);
            this.prepare.setString(7, selectedMenu);
            this.prepare.setString(8, selectedMethod);

            this.prepare.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Promo Added");
            alert.setHeaderText(null);
            alert.setContentText("Promo has been added successfully!");
            alert.showAndWait();
            //Clear when complete
            promo_idPromo.clear();
            promo_namaPromo.clear();
            promo_nominal.clear();
            promo_tanggalBerlaku.setValue(null);
            promo_tanggalBerakhir.setValue(null);
            promo_kategori.getSelectionModel().clearSelection();
            promo_menu.getSelectionModel().clearSelection();
            promo_metode.getSelectionModel().clearSelection();

            promoShowData();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //delete
    @FXML
    void deletePromo(ActionEvent event) {
        Promo selectedPromo = promo_tableView.getSelectionModel().getSelectedItem();
        if (selectedPromo!= null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Promo");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this promo?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String sql = "DELETE FROM promo WHERE promo_id =?";
                try {
                    this.connect = DatabaseConnection.getConnection();
                    this.prepare = this.connect.prepareStatement(sql);
                    this.prepare.setInt(1, selectedPromo.getPromo_id());
                    this.prepare.executeUpdate();

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Promo Deleted");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Promo has been deleted successfully!");
                    successAlert.showAndWait();

                    promoShowData(); // refresh the table view
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Promo Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a promo to delete!");
            alert.showAndWait();
        }

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
    public void promoClearBtn() {
        this.promo_idPromo.setText("");
        this.promo_namaPromo.setText("");
        this.promo_nominal.setText("");
        this.promo_kategori.getSelectionModel().clearSelection();
        this.promo_menu.getSelectionModel().clearSelection();
        this.promo_metode.getSelectionModel().clearSelection();
        this.promo_tanggalBerlaku.setValue(null);
        this.promo_tanggalBerakhir.setValue(null);
    }
    @FXML
    void updatePromo(ActionEvent event) {
        Promo selectedPromo = promo_tableView.getSelectionModel().getSelectedItem();
        if (selectedPromo != null) {
            String promoId = promo_idPromo.getText();
            String promoName = promo_namaPromo.getText();
            String promoNominal = promo_nominal.getText();
            LocalDate dateStart = promo_tanggalBerlaku.getValue();
            LocalDate dateEnd = promo_tanggalBerakhir.getValue();
            String selectedKategori = promo_kategori.getSelectionModel().getSelectedItem();
            String selectedMenu = promo_menu.getSelectionModel().getSelectedItem();
            String selectedMethod = promo_metode.getSelectionModel().getSelectedItem();

            // Validate data
            if (promoId.isEmpty() || promoName.isEmpty() || promoNominal.isEmpty() || dateStart == null || dateEnd == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Form Incomplete");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all fields!");
                alert.showAndWait();
                return;
            }

            // Update data in the database
            String sql = "UPDATE promo SET promo_name = ?, promo_nominal = ?, date_start = ?, date_end = ?, kategori_id = (SELECT kategori_id FROM kategori WHERE kategori_name = ?), menu_id = (SELECT menu_id FROM menu WHERE menu_name = ?), method_id = (SELECT method_id FROM payment_method WHERE method_name = ?) WHERE promo_id = ?";

            try {
                this.connect = DatabaseConnection.getConnection();
                this.prepare = this.connect.prepareStatement(sql);
                this.prepare.setString(1, promoName);
                this.prepare.setDouble(2, Double.parseDouble(promoNominal));
                this.prepare.setDate(3, java.sql.Date.valueOf(dateStart));
                this.prepare.setDate(4, java.sql.Date.valueOf(dateEnd));
                this.prepare.setString(5, selectedKategori);
                this.prepare.setString(6, selectedMenu);
                this.prepare.setString(7, selectedMethod);
                this.prepare.setInt(8, selectedPromo.getPromo_id());

                this.prepare.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Promo Updated");
                alert.setHeaderText(null);
                alert.setContentText("Promo has been updated successfully!");
                alert.showAndWait();

                //Clear when complete
                promo_idPromo.clear();
                promo_namaPromo.clear();
                promo_nominal.clear();
                promo_tanggalBerlaku.setValue(null);
                promo_tanggalBerakhir.setValue(null);
                promo_kategori.getSelectionModel().clearSelection();
                promo_menu.getSelectionModel().clearSelection();
                promo_metode.getSelectionModel().clearSelection();

                promoShowData();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Promo Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a promo to update!");
            alert.showAndWait();
        }
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

        promo_kategori.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            try {
                promoMenuList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        promo_addBtn.setOnAction(this::addPromo);
        promo_updateBtn.setOnAction(this::updatePromo);
    }
}

