package stockinvestor.model;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Nick on 22/08/2015.
 */
public class StockInfo {

    private Stock stock;

    public StockInfo() {
    }

    public StockInfo(String stockName) {
        try {
            stock = YahooFinance.get(stockName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Internet connection, please try again later");
        }

    }

    public String getStockName() {
        return stock.getName();
    }

    public String getStockTicker() {
        return stock.getSymbol();
    }

    public Double getStockPrice() {
        Double price = 0.0;
           price = stock.getQuote().getPrice().doubleValue();
        return price;
    }


}

