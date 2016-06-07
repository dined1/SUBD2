package sample;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by Adm on 28.03.2016.
 */
public class PerfomanceCreateController {


    @FXML Button cancelButton;
    @FXML Button okButton;
    @FXML TextField messName;
    @FXML TextField messY;
    @FXML TextField messBu;
    @FXML TextField messBe;
    @FXML TextField messEn;
    @FXML TextField messG;
    @FXML TextField messI;


    public void initialize() throws SQLException {
    }

    public void initializeF(Controller controller, Scene scene, Label signal) {
        okButton.setOnAction(event -> {
            Controller c = new Controller();
            c.Per(messName.getText(),Integer.parseInt(messY.getText()),Integer.parseInt(messBu.getText()),
                    messBe.getText(),messEn.getText(),messG.getText(),Integer.parseInt(messI.getText()));
            Stage stage = (Stage)cancelButton.getScene().getWindow();
            stage.close();
            signal.setText(" ");
        });
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage)cancelButton.getScene().getWindow();
            stage.close();
        });
    }
}
