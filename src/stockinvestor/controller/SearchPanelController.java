package stockinvestor.controller;


import com.jaunt.JauntException;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import stockinvestor.model.MainScreen;
import stockinvestor.model.Singleton;
import stockinvestor.model.StockInfo;
import stockinvestor.model.WebScraper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class SearchPanelController implements Initializable {

@FXML
private TextField searchInput;
@FXML
private StackPane lowerpane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchInput.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    createStockObject();
                }
            }
        });



    }

    private void createStockObject()
    {
        Singleton.getInstance().setCurrentStockInfo(new StockInfo(searchInput.getText().toUpperCase()));
        if(Singleton.getInstance().currentStockInfo().getStockName().equals("N/A"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Stock not found");
            alert.setContentText("The stock Ticker that you have entered does not exist, please try again.");

            alert.showAndWait();
        }
        else {
            lowerpane.getChildren().clear();
            try {
                lowerpane.getChildren().add(FXMLLoader.load(getClass().getResource("../fxml/QuoteInfo.fxml")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        createStockObject();
    }
}
