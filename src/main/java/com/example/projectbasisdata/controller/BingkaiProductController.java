package com.example.projectbasisdata.controller;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import com.example.projectbasisdata.DatabaseConnection;
import com.example.projectbasisdata.model.DetailMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import com.example.projectbasisdata.controller.TransaksiScreenController;

public class BingkaiProductController implements Initializable {
    @FXML
    private VBox bingkai_vBox;
    @FXML
    private Label bingkai_namaProduk;
    @FXML
    private Label bingkai_hargaProduk;
    @FXML
    private Spinner<Integer> bingkai_addQuantity;
    @FXML
    private Button bingkai_addBtn;
    @FXML
    private AnchorPane bingkai_from;
    @FXML
    private Label bingkai_Size;

    private DetailMenu detailMenu;
    private Connection connect;
    private Statement statement;
    private ResultSet result;
    private Alert alert;
    private PreparedStatement prepare;
    private TransaksiScreenController transaksiScreen;

    //digunakan untuk setdata bingkai produk//
    public void setData(DetailMenu detailMenu){
        this.detailMenu = detailMenu;
        bingkai_namaProduk.setText(detailMenu.getMenu_name());
        String sizeName = detailMenu.getSize_name();
        if(sizeName.equals("Large")){
            sizeName="L";
        }else if(sizeName.equals("Medium")){
            sizeName = "M";
        }else{
            sizeName = "S";
        }
        bingkai_Size.setText(sizeName);
        bingkai_hargaProduk.setText(String.valueOf(detailMenu.getHarga_nominal()));
    }

    private SpinnerValueFactory<Integer> spin;

    private int jumblah;
    //spinner nya untuk mengatur quantity
    public void setJumblah(){
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
        bingkai_addQuantity.setValueFactory(spin);
    }
//menghubungkan dengan transaksi screen
    public void setTransaksiScreen(TransaksiScreenController transaksiScreen) {
        this.transaksiScreen = transaksiScreen;
    }
//button add menjalankan fungsi berikut
    public void addClick() throws SQLException {
        if (bingkai_addQuantity.getValue()!=0) {
            this.connect = DatabaseConnection.getConnection();
            try {
                this.statement = this.connect.createStatement();
                String insertData = "insert into \"order\"(order_date,quantity,detailmenu_id, checkclear) values (\n" +
                        "                        current_date,\n" +
                        "\t\t\t\t\t\t?,\n" +
                        "                        (select detailmenu_id from detail_menu where harga_nominal = ? and menu_id=(select menu_id from menu where menu_name= ?) and size_id=(select size_id from size where size_name= ?)),\n" +
                        "\t\t\t\t\t\t'TEMP'\n" +
                        "                        )";
                this.prepare = this.connect.prepareStatement(insertData);
                this.prepare.setInt(1, Integer.parseInt(String.valueOf(this.bingkai_addQuantity.getValue())));
                this.prepare.setInt(2, Integer.parseInt(this.bingkai_hargaProduk.getText()));
                this.prepare.setString(3, this.bingkai_namaProduk.getText());
                this.prepare.setString(4, detailMenu.getSize_name());
                this.prepare.executeUpdate();
                String insertData2 = "insert into temp_order (order_date,quantity,detailmenu_id) values (\n" +
                        "\tcurrent_date,\n" +
                        "\t?,\n" +
                        "\t(select detailmenu_id from detail_menu where harga_nominal = ? and menu_id=(select menu_id from menu where menu_name= ?) and size_id=(select size_id from size where size_name= ?))\n" +
                        ")";
                this.prepare = this.connect.prepareStatement(insertData2);
                this.prepare.setInt(1, Integer.parseInt(String.valueOf(this.bingkai_addQuantity.getValue())));
                this.prepare.setInt(2, Integer.parseInt(this.bingkai_hargaProduk.getText()));
                this.prepare.setString(3, this.bingkai_namaProduk.getText());
                this.prepare.setString(4, detailMenu.getSize_name());
                this.prepare.executeUpdate();
                transaksiScreen.transaksiShowData();
                this.clear();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.alert = new Alert(Alert.AlertType.ERROR);
            this.alert.setTitle("Message");
            this.alert.setHeaderText((String) null);
            this.alert.setContentText("Please enter quantity");
            this.alert.showAndWait();
        }
    }
    //fungsi clear menghapus semua
    public void clear() {
        this.bingkai_addQuantity.getValueFactory().setValue(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setJumblah();
    }
}
