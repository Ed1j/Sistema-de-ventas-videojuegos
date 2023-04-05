module com.juegos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.juegos to javafx.fxml;
    exports com.juegos;
}
