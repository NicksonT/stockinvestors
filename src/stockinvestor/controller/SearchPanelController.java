package stockinvestor.controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import stockinvestor.model.Singleton;
import stockinvestor.model.StockInfo;
import yahoofinance.Stock;

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
        searchInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    createStockObject();
                }
            }
        });


    }

    private void createStockObject() {
        String textInput = searchInput.getText().toUpperCase();
        StockInfo searchStock = new StockInfo(textInput);
        if (searchStock.getStockName().equals("N/A")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Stock not found");
            alert.setContentText("The stock Ticker that you have entered does not exist, please try again.");

            alert.showAndWait();
        } else {

            try {
                FXMLLoader a =new FXMLLoader(getClass().getResource("../fxml/QuoteInfo.fxml"));
                a.setController(new QuoteInfoController());
                QuoteInfoController controller = a.<QuoteInfoController>getController();
                controller.initData(textInput);
                lowerpane.getChildren().clear();
                lowerpane.getChildren().add(a.load());



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
