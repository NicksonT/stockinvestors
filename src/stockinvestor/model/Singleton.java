package stockinvestor.model;

import sql.Database;

/**
 * Created by nt305 on 27/02/16.
 */
public class Singleton {
    private final static Singleton instance = new Singleton();

    public static Singleton getInstance() {
        return instance;
    }
    private sql.Database database = new Database();
    private StockInfo stockInfo = new StockInfo();
    public StockInfo currentStockInfo() { return stockInfo;}
    public void setCurrentStockInfo(StockInfo stockInfo){this.stockInfo = stockInfo;}
    public sql.Database currentDatabase() {
        return database;
    }
}