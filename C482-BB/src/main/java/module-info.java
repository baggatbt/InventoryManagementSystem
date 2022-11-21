module baggatta.c482bb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;


    exports baggatta.c482bb.controller;
    opens baggatta.c482bb.controller to javafx.fxml;
    exports baggatta.c482bb.model;
    opens baggatta.c482bb.model to javafx.fxml;

}