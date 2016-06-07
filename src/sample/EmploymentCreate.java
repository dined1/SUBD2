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
public class EmploymentCreate {
    public ChoiceBox messAc;
    public ChoiceBox messPe;
    public TextField messRo;
    public TextField messCo;
    public Button cancelButton;
    public Button okButton;

    private ObservableList<String> Actors = FXCollections.observableArrayList();
    private ObservableList<String> Performances = FXCollections.observableArrayList();


    public void initialize() throws SQLException {
    }

    public void initializeF(Controller controller, Scene scene, Label signal) {

        Controller c = new Controller();
        try {
            String SQL = "SELECT FIO FROM ACTOR";
            ResultSet rs = c.st.executeQuery(SQL);
            while (rs.next()){
                Actors.add(rs.getString("FIO"));
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
        messAc.setItems(Actors);
        okButton.setOnAction(event -> {
            c.Emp(messAc.getSelectionModel().getSelectedItem().toString(),messPe.getSelectionModel().getSelectedItem().toString(),
                    messRo.getText(), Integer.parseInt(messCo.getText()));
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
