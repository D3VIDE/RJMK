package com.example.projectbasisdata;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFxml("mainScreen"),1100,600);
        stage.setTitle("Project Basis Data");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public static void setRoot(String fxml) throws IOException{
        scene.setRoot(loadFxml(fxml));
    }
    private static Parent loadFxml(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxml+".fxml"));
        return fxmlLoader.load();
    }
}

//add
