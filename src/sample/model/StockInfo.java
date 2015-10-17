package sample.model;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Nick on 22/08/2015.
 */
public class StockInfo {

        public static void printStock(String StockName) {
            try
            {
                Stock stock = YahooFinance.get(StockName);

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

