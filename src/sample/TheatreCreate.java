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
 * Created by Adm on 09.04.2016.
 */
public class TheatreCreate {

    public Button cancelButton;
    public Button okButton;
    public TextField messName;
    public TextField messAd;
    public TextField messTe;
    public ChoiceBox messTy;

    private ObservableList<String> Types = FXCollections.observableArrayList();


    public void initialize() throws SQLException {
    }

    public void initializeF(Controller controller, Scene scene, Label signal) {

        Controller c = new Controller();
        try {
            String SQL = "SELECT TYPE FROM TYPE";
            ResultSet rs = c.st.executeQuery(SQL);
            while (rs.next()){
                Types.add(rs.getString("TYPE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        messTy.setItems(Types);
        okButton.setOnAction(event -> {
            c.The(messTy.getSelectionModel().getSelectedItem().toString(),messName.getText(),
                    messAd.getText(), messTe.getText());
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
