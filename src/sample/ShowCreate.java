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
 * Created by Adm on 25.04.2016.
 */
public class ShowCreate {
    public ChoiceBox messTh;
    public ChoiceBox messPe;
    public TextField messTi;
    public TextField messFs;
    public Button cancelButton;
    public Button okButton;

    private ObservableList<String> Theatre = FXCollections.observableArrayList();
    private ObservableList<String> Performances = FXCollections.observableArrayList();


    public void initialize() throws SQLException {
    }

    public void initializeF(Controller controller, Scene scene, Label signal) {

        Controller c = new Controller();
        try {
            String SQL = "SELECT NAME FROM THEATRE";
            ResultSet rs = c.st.executeQuery(SQL);
            while (rs.next()){
                Theatre.add(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String SQL = "SELECT NAME FROM PERFOMANCE";
            ResultSet rs = c.st.executeQuery(SQL);
            while (rs.next()){
                Performances.add(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        messPe.setItems(Performances);
        messTh.setItems(Theatre);
        okButton.setOnAction(event -> {
            c.Sho(messTh.getSelectionModel().getSelectedItem().toString(),messPe.getSelectionModel().getSelectedItem().toString(),
                    messTi.getText(), Integer.parseInt(messFs.getText()));
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
