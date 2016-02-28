package stockinvestor.controller;

import com.jaunt.JauntException;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import stockinvestor.model.Singleton;
import stockinvestor.model.StockInfo;
import stockinvestor.model.WebScraper;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Nick on 23/10/2015.
 */
public class quoteInfoController implements Initializable {
    @FXML
    private Label stockName;
    @FXML
    private ImageView stockImage;
    @FXML
    private Label stockPrice;
    @FXML
    private Button sellStock;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        int quantity = buyStockForPortfolio();
        boolean enoughCash = checkIfEnoughCash(quantity);
        if (enoughCash)
        {
            putStockIntoPortfolio(quantity);
        }
        else {
            //Put warning alert
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        disableButtonIfStockNotInPortfolio();
        try {

            stockImage.setImage(WebScraper.getLogo(Singleton.getInstance().currentStockInfo().getStockName()));
        }
        catch(JauntException e)
        {
            e.printStackTrace();
            System.out.println("Exception caught");
        }
        stockName.setText(Singleton.getInstance().currentStockInfo().getStockName());
        stockPrice.setText(String.valueOf(Singleton.getInstance().currentStockInfo().getStockPrice()));
    }

    public void disableButtonIfStockNotInPortfolio()
    {
        String stockTicker = Singleton.getInstance().currentStockInfo().getStockTicker();
        if(!(Singleton.getInstance().currentDatabase().checkIfStockInPortfolio(stockTicker)))
        {
            sellStock.setDisable(true);
        }
    }

    public int buyStockForPortfolio()
    {
        final int DEFAULT = 0;
        TextInputDialog dialog = new TextInputDialog(String.valueOf(DEFAULT));
        dialog.setTitle("Buy stock");
        dialog.setContentText("Please enter the quantity of "+Singleton.getInstance().currentStockInfo().getStockName() +" stock you would like to purchase");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
         return Integer.parseInt(result.get());
        }
        else{
        return DEFAULT;
        }
    }
    public boolean checkIfEnoughCash(int quantity)
    {
        boolean enoughCash = false;
        final double TOTAL_PRICE= quantity * Singleton.getInstance().currentStockInfo().getStockPrice();
        final double CASH = Singleton.getInstance().currentDatabase().getCash();
        if(TOTAL_PRICE < CASH)
        {
            return true;
        }
        return enoughCash;
    }
    public void putStockIntoPortfolio(int quantity)
    {
        final String STOCKTICKER = Singleton.getInstance().currentStockInfo().getStockTicker();
        final String STOCKNAME = Singleton.getInstance().currentStockInfo().getStockName();
        final double STOCKPRICE = Singleton.getInstance().currentStockInfo().getStockPrice();
        final double TOTAL_PRICE= quantity * Singleton.getInstance().currentStockInfo().getStockPrice();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("You are looking to purchase "+quantity+" of " + Singleton.getInstance().currentStockInfo().getStockName()
                + " stock for a total of "+ TOTAL_PRICE + "USD, is this correct?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            double cash = Singleton.getInstance().currentDatabase().getCash();
            Singleton.getInstance().currentDatabase().setCash((cash - TOTAL_PRICE));
            if(Singleton.getInstance().currentDatabase().checkIfStockInPortfolio(STOCKTICKER))
            {
                quantity = Singleton.getInstance().currentDatabase().getQuantityOfStock(STOCKTICKER);
                Singleton.getInstance().currentDatabase().setQuantityOfStock(STOCKTICKER,quantity);
            }
            else{
                Singleton.getInstance().currentDatabase().insert("'"+STOCKTICKER+"','"+STOCKNAME+"',"+quantity+","+STOCKPRICE,"PORTFOLIO");
            }
        }
    }
}