package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Adm on 02.04.2016.
 */
public class UserCreate {
    @FXML public TextField loginReg;
    @FXML public PasswordField passwordReg;
    @FXML public ChoiceBox choiseTheatre;
    @FXML public Button okButton;
    @FXML public Button cancelButton;


    private ObservableList<String> Theatres = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
    }
    public void initializeF(Controller controller, Scene scene, Label signal) throws SQLException {
        Controller c = new Controller();
        try {
            String SQL = "SELECT NAME FROM THEATRE";
            ResultSet rs = c.st.executeQuery(SQL);
            while (rs.next()){
                Theatres.add(rs.getString("NAME"));
            }
            Theatres.add("null");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        choiseTheatre.setItems(Theatres);
        okButton.setOnAction(event -> {
            c.Register(loginReg.getText(),passwordReg.getText(),choiseTheatre.getSelectionModel().getSelectedItem().toString());
            Stage stage = (Stage)okButton.getScene().getWindow();
            stage.close();
            signal.setText(" ");
        });
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage)cancelButton.getScene().getWindow();
            stage.close();
        });
    }
}
