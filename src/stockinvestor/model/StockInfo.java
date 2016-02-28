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

    private static Stock stock;
    private String stockName;

    public StockInfo()
    {
        stockName = "";
    }
    public StockInfo(String StockName) {
        this.stockName = StockName;
        try {
            stock = YahooFinance.get(stockName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Internet connection, please try again later");
        }

    }

    public String getStockName() {
        return stock.getName();
    }
    public String getStockTicker() {return stock.getSymbol();}
    public Double getStockPrice() {
        return stock.getQuote().getPrice().doubleValue();

    }
    public void printStock() {
            try
            {

                Stock stock = YahooFinance.get(stockName);

                BigDecimal price = stock.getQuote().getPrice();
                BigDecimal change = stock.getQuote().getChangeInPercent();
                BigDecimal peg = stock.getStats().getPeg();
                BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
                stock.print();
            }
            catch(IOException e) {

            }
        }
}

