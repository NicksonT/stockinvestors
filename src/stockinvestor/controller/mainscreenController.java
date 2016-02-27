package stockinvestor.controller;

import com.jaunt.JauntException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import stockinvestor.model.MainScreen;
import stockinvestor.model.Singleton;
import stockinvestor.model.WebScraper;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class mainscreenController implements Initializable {

    @FXML
    private Label london;
    @FXML
    private Label newYork;
    @FXML
    private Label tokyo;
    @FXML
    private Label news;
    @FXML
    private Label name;
    @FXML
    private Label money;
    @FXML
    private VBox rightPane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(Singleton.getInstance().currentDatabase().getName());
        money.setText("Balance: " +Singleton.getInstance().currentDatabase().getCash() +"GBP");
        String londonTime = MainScreen.getTime("Europe/London");
        String nyTime = MainScreen.getTime("America/New_York");
        String tokyoTime = MainScreen.getTime("Asia/Tokyo");
        try {
            List<String> bloomberg = (WebScraper.getNewsHeadlines("bloomberg"));
            news.setText(bloomberg.get(1));
        }
        catch (JauntException e) {
        }

        london.setText(london.getText() + londonTime);
        newYork.setText(newYork.getText() + nyTime);
        tokyo.setText(tokyo.getText() + tokyoTime);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String londonTime = MainScreen.getTime("Europe/London");
                        String nyTime = MainScreen.getTime("America/New_York");
                        String tokyoTime = MainScreen.getTime("Asia/Tokyo");
                        london.setText("Time in London: " + londonTime);
                        newYork.setText("Time in New York: " + nyTime);
                        tokyo.setText("Time in Tokyo: " + tokyoTime);
                        //news.setText();
                    }
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    @FXML
    private void buyButton(ActionEvent event) throws IOException {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(FXMLLoader.load(getClass().getResource("../marketpanels/fxml/NYSEPanel.fxml")));


        }

    @FXML
    private void sellStockAction(ActionEvent event) throws IOException {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(FXMLLoader.load(getClass().getResource("../marketpanels/fxml/LSEPanel.fxml")));


    }
}
