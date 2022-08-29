
package model;

import javafx.scene.control.Alert;

/**
 *
 * @author GRupo 6
 */
public class Alerts {
    public static void makeAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alerta!");
        alert.setHeaderText(title);
        alert.setContentText(header);
        alert.showAndWait();
    }
}
