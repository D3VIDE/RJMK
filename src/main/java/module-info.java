module com.example.projectbasisdata {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.projectbasisdata to javafx.fxml;
    exports com.example.projectbasisdata;
    opens com.example.projectbasisdata.controller to javafx.fxml;
    exports com.example.projectbasisdata.controller;
}