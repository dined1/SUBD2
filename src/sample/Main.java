package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(1280);
        stage.setHeight(768);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("ComplexApplication_css.fxml"));
        scene.setRoot(loader.load());

        /*Controller controller = loader.<Controller>getController();
        controller.initialize();*/

        stage.setScene(scene);
        stage.show();

/*
        table.setItems(data);
        table.getColumns().addAll(tabname, tabY, tabBu);

        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(tabname.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(tabY.getPrefWidth());
        addLastName.setPromptText("Last Name");
        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(tabBu.getPrefWidth());
        addEmail.setPromptText("Email");

        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            data.add(new Perfomance(
                    addName.getText(),
                    addLastName.getText(),
                    addEmail.getText()));
            addFirstName.clear();
            addLastName.clear();
            addEmail.clear();
        });*/

    }
}