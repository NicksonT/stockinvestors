package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.MainScreen;

import java.io.IOException;


public class newGameController {

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
            new MainScreen(username);
            stage = (Stage) submit.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("mainscreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        catch(NullPointerException e)
        {

        }

    }
}
