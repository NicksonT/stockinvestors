package sample;


import com.jaunt.JauntException;
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
import sample.model.MainScreen;
import sample.model.User;
import sample.model.WebScraper;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;



public class MarketPanelController implements Initializable {
    @FXML
    private Label newYork;
    @FXML
    private Button lookupquote;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String nyTime = MainScreen.getTime("America/New_York");
        newYork.setText(newYork.getText() + nyTime);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                String nyTime = MainScreen.getTime("America/New_York");
                                newYork.setText("Local Time: " + nyTime);
                                //news.setText();
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
        stage.setTitle("My modal window");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(lookupquote.getScene().getWindow());
        stage.showAndWait();
    }
}
