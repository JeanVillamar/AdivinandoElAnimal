module Interfaz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens Interfaz to javafx.fxml;
    exports Interfaz;
}
