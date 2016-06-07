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
 * Created by Adm on 10.04.2016.
 */
public class CreatorCreate {
    public TextField messFio;
    public ChoiceBox messPos;
    public Button cancelButton;
    public Button okButton;

    private ObservableList<String> Positions = FXCollections.observableArrayList();

    public void initialize() throws SQLException {}
    public void initializeF(Controller controller, Scene scene, Label signal) throws SQLException {

        Controller c = new Controller();
        try {
            String SQL = "SELECT NAME FROM POSITION";
            ResultSet rs = c.st.executeQuery(SQL);
            while (rs.next()){
                Positions.add(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        messPos.setItems(Positions);
        okButton.setOnAction(event -> {
            c.Pos(messFio.getText(),messPos.getSelectionModel().getSelectedItem().toString());
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
