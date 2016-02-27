package stockinvestor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import stockinvestor.model.Singleton;

import java.io.File;
import java.io.IOException;

public class MainMenuController {
    @FXML
    private Button newGame;
    @FXML
    private Button loadGame;

    @FXML
    private void handleNewGameButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == newGame) {
            //get reference to the button's stage
            stage = (Stage) newGame.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("../fxml/newgame.fxml"));
            //create a new scene with root and set the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    private void handleLoadGameButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) loadGame.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Database File");
        File saveFile = fileChooser.showOpenDialog(stage);
        if(saveFile == null)
        {
            root = FXMLLoader.load(getClass().getResource("../fxml/MainMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Singleton.getInstance().currentDatabase().initDatabase(saveFile.getName());
            root = FXMLLoader.load(getClass().getResource("../fxml/mainscreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
}
