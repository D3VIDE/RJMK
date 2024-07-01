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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BingkaiProductController implements Initializable {
    @FXML
    private VBox bingkai_vBox;
    @FXML
    private Label bingkai_namaProduk;
    @FXML
    private Label bingkai_hargaProduk;
    @FXML
    private Spinner<?> bingkai_addQuantity;
    @FXML
    private Button bingkai_addBtn;
    @FXML
    private AnchorPane bingkai_from;
    @FXML
    private Label bingkai_Size;

    private DetailMenu detailMenu;


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





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
