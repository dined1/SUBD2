package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Adm on 04.04.2016.
 */
public class ActorCreateController {

    public TextField messName;
    public TextField messY;
    public TextField messEx;
    public TextField messCo;
    public TextField messRa;
    public TextField messGe;
    public TextField messAdr;
    public Button cancelButton;
    public Button okButton;
    public ChoiceBox messUs;

    private ObservableList<String> Users = FXCollections.observableArrayList();



    public void initialize() throws SQLException {
    }

    public void initializeF(Controller controller, Scene scene, Label signal) {
        Controller c = new Controller();
        
        messUs.setItems(Users);
        okButton.setOnAction(event -> {
            c.Act(messName.getText(),Integer.parseInt(messY.getText()),Integer.parseInt(messEx.getText()),Integer.parseInt(messCo.getText()),
                    messRa.getText(), messGe.getText(),messAdr.getText(),messUs.getSelectionModel().getSelectedItem().toString());
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