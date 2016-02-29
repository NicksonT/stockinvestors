package stockinvestor.controller;

import com.jaunt.JauntException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import stockinvestor.model.MainScreen;
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
    
    private StockInfo currentStock;

    void initData(String currentStock)
    {
        this.currentStock= new StockInfo(currentStock.toUpperCase());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stockName.setText(currentStock.getStockName());
        disableButtonIfStockNotInPortfolio();

        stockPrice.setText(String.valueOf(currentStock.getStockPrice()));

        try {
             stockImage.setImage(WebScraper.getLogo(currentStock.getStockName(),0));
        } catch (JauntException e) {
            e.printStackTrace();
            System.out.println("Exception caught");
        }


        Timeline timeline = new Timeline(

        new KeyFrame(Duration.seconds(1),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                stockPrice.setText(String.valueOf(currentStock.getStockPrice()));
                                disableButtonIfStockNotInPortfolio();
                            }
                        })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }



    @FXML
    private void handleBuyButtonAction(ActionEvent event) throws IOException {
        int quantity = buyStockForPortfolio();
        if (quantity > 0) {
            boolean enoughCash = checkIfEnoughCash(quantity);
            if (enoughCash) {
                putStockIntoPortfolio(quantity);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not enough money!");
                alert.setContentText("You don't have enough money to buy the stocks");

                alert.showAndWait();
            }
        }
    }
    @FXML
    private void handleSellButtonAction(ActionEvent event) throws IOException {
        int quantity = sellStockForPortfolio();
        if(quantity > 0) {
            boolean enoughStocks = checkIfEnoughStocks(quantity);
            if(enoughStocks) {
                takeStocksOutOfPortfolio(quantity);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not enough stocks");
                alert.setContentText("You don't have enough stocks");

                alert.showAndWait();
            }
        }
    }

    public boolean checkIfEnoughStocks(int quantity)
    {
        final String stockTicker = currentStock.getStockTicker();
        boolean enoughStocks = false;
        final int QUANTITY_OF_STOCKS_IN_PORTFOLIO = Singleton.getInstance().currentDatabase().getQuantityOfStock(stockTicker);
        if (quantity <= QUANTITY_OF_STOCKS_IN_PORTFOLIO) {
            return true;
        }
        return enoughStocks;
    }

    public void takeStocksOutOfPortfolio( int quantity) {
        final String STOCKTICKER = currentStock.getStockTicker();
        final double TOTAL_PRICE = quantity * currentStock.getStockPrice();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("You are looking to sell " + quantity + " of " + currentStock.getStockName()
                + " stock for a total of " + TOTAL_PRICE + "USD, is this correct?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            double cash = Singleton.getInstance().currentDatabase().getCash();
            Singleton.getInstance().currentDatabase().setCash((cash + TOTAL_PRICE));
            int currentQuantity = Singleton.getInstance().currentDatabase().getQuantityOfStock(STOCKTICKER);
            Singleton.getInstance().currentDatabase().setQuantityOfStock(STOCKTICKER, currentQuantity-quantity);

        }
    }


    public void disableButtonIfStockNotInPortfolio() {
        String stockTicker = currentStock.getStockTicker();
        if (!(Singleton.getInstance().currentDatabase().checkIfStockInPortfolio(stockTicker))) {
            sellStock.setDisable(true);
        }
        else{
            if(Singleton.getInstance().currentDatabase().getQuantityOfStock(stockTicker) == 0)
            {
                sellStock.setDisable(true);
            }
            else{
                sellStock.setDisable(false);
            }
        }
    }

    public int sellStockForPortfolio(){
        final int DEFAULT = 0;
        final String STOCK_NAME = currentStock.getStockName();
        final String STOCK_TICKER = currentStock.getStockTicker();
        final int STOCK_QUANTITY = Singleton.getInstance().currentDatabase().getQuantityOfStock(STOCK_TICKER);
        TextInputDialog dialog = new TextInputDialog(String.valueOf(DEFAULT));
        dialog.setTitle("Sell stock");
        dialog.setContentText("You currently hold " + STOCK_QUANTITY + " stocks in " + STOCK_NAME + "  Please enter the quantity of " + STOCK_NAME + " stock you would like to sell");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return Integer.parseInt(result.get());
        } else {
            return DEFAULT;
        }
    }
    public int buyStockForPortfolio() {
        final int DEFAULT = 0;
        TextInputDialog dialog = new TextInputDialog(String.valueOf(DEFAULT));
        dialog.setTitle("Buy stock");
        dialog.setContentText("Please enter the quantity of " + currentStock.getStockName() + " stock you would like to purchase");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return Integer.parseInt(result.get());
        } else {
            return DEFAULT;
        }
    }

    public boolean checkIfEnoughCash(int quantity) {
        boolean enoughCash = false;
        final double TOTAL_PRICE = quantity * currentStock.getStockPrice();
        final double CASH = Singleton.getInstance().currentDatabase().getCash();
        if (TOTAL_PRICE < CASH) {
            return true;
        }
        return enoughCash;
    }

    public void putStockIntoPortfolio(int quantity) {
        final String STOCKTICKER = currentStock.getStockTicker();
        final String STOCKNAME = currentStock.getStockName();
        final double STOCKPRICE = currentStock.getStockPrice();
        final double TOTAL_PRICE = quantity * currentStock.getStockPrice();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("You are looking to purchase " + quantity + " of " + currentStock.getStockName()
                + " stock for a total of " + TOTAL_PRICE + "USD, is this correct?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            double cash = Singleton.getInstance().currentDatabase().getCash();
            Singleton.getInstance().currentDatabase().setCash((cash - TOTAL_PRICE));
            if (Singleton.getInstance().currentDatabase().checkIfStockInPortfolio(STOCKTICKER)) {
                int currentQuantity = Singleton.getInstance().currentDatabase().getQuantityOfStock(STOCKTICKER);
                Singleton.getInstance().currentDatabase().setQuantityOfStock(STOCKTICKER, currentQuantity + quantity);
            } else {
                Singleton.getInstance().currentDatabase().insert("'" + STOCKTICKER + "','" + STOCKNAME + "'," + quantity + "," + STOCKPRICE, "PORTFOLIO");
            }
        }
    }
}