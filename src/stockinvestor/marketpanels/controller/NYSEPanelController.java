package stockinvestor.marketpanels.controller;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import stockinvestor.model.MainScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class NYSEPanelController implements Initializable {
    @FXML
    private Label newYork;
    @FXML
    private Button lookupquote;
    @FXML
    private Label london;
    @FXML
    private Label tokyo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {



        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                String nyTime = MainScreen.getTime("America/New_York");
                                newYork.setText("Local Time: " + nyTime);
                            }
                        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("LookUpQuote.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Nick's Stock Investor");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(lookupquote.getScene().getWindow());
        stage.showAndWait();
    }
}
