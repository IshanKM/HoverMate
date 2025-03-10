module com.example.hovermate {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hovermate to javafx.fxml;
    exports com.example.hovermate;
}