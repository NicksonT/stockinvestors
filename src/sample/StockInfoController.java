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

import java.io.IOException;

public class StockInfoController {
    @FXML
    private Label mainmessage;

    @FXML
    private TextField stockname;

    @FXML
    private Button submit;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        String username = stockname.getText();
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage = (Stage) submit.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("QuoteInfo.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}



