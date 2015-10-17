package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Button btn1;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == btn1) {
            //get reference to the button's stage
            stage = (Stage) btn1.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("newgame.fxml"));
        } else {
            stage = (Stage) btn1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        }
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
