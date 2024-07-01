package com.example.projectbasisdata.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.projectbasisdata.model.DetailMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    public void setJumblah(){
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
        bingkai_addQuantity.setValueFactory(spin);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setJumblah();
    }
}
