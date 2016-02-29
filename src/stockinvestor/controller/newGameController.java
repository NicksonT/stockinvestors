package stockinvestor.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import stockinvestor.model.Singleton;

import java.io.File;
import java.io.IOException;


public class NewGameController {

    @FXML
    private Label mainmessage;
    @FXML
    private TextField userid;
    @FXML
    private Button submit;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        try {
            Stage stage;
            Parent root;
            String username = userid.getText();
            stage = (Stage) submit.getScene().getWindow();
            File newSaveFile = saveGame(username, stage);
            if (newSaveFile == null) {
                root = FXMLLoader.load(getClass().getResource("../fxml/MainMenu.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

            Singleton.getInstance().currentDatabase().initDatabase(newSaveFile.getAbsolutePath());
            Singleton.getInstance().currentDatabase().createTables();
            Singleton.getInstance().currentDatabase().insert("'" + username + "',20000", "USER");
            root = FXMLLoader.load(getClass().getResource("../fxml/MainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (NullPointerException e) {

        }

    }

    private File saveGame(String username, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("New Game");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Database file", "*.db"));
        fileChooser.setInitialFileName(username + "'s Stock Portfolio - Stock Investor Simulation.db");
        File file = fileChooser.showSaveDialog(stage);
        return file;
    }
}
