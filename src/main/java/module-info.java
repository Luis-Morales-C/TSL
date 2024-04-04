module com.example.tsl {
    requires javafx.controls;
    requires javafx.fxml;

    opens TSLG.controller to javafx.fxml;
    exports TSLG;
}