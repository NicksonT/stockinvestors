package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import sample.model.MainScreen;
import sample.model.StockInfo;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockInfoController implements Initializable {
    @FXML
    private Label mainmessage;

    @FXML
    private TextField stockname;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {

                                StockInfo.printStock("AAPL");
                            }
                        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    }



