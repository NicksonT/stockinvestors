package stockinvestor.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import stockinvestor.model.StockInfo;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Nick on 23/10/2015.
 */
public class quoteInfoController implements Initializable {
    @FXML
    private Label name;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(StockInfo.getStockName());
    }

}
