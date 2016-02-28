package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection saveGame;
    public Database()
    {

    }
    public void initDatabase(String filename)
    {
        try {
            Class.forName("org.sqlite.JDBC");
            saveGame = DriverManager.getConnection("jdbc:sqlite:" + filename +  "");
        }
        catch(Exception e){
            System.err.println(e.getClass().getName());
            System.exit(0);
        }

    }
    public void createTables() {

        Statement stmt = null;
        try {
            stmt = saveGame.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS USER " +
                    "(NAME CHAR PRIMARY KEY  NOT NULL," +
                    " CASH FLOAT             NOT NULL);" +
                    " CREATE TABLE IF NOT EXISTS PORTFOLIO " +
                    "(STOCKTICKER VARCHAR PRIMARY KEY     NOT NULL," +
                    " COMPANYNAME VARCHAR       NOT NULL," +
                    " QUANTITY INT           NOT NULL," +
                    " CURRENTPRICE FLOAT     NOT NULL);" +
                    " CREATE TABLE IF NOT EXISTS TRANSACTIONS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " TRANSACTIONDATE DATE   NOT NULL," +
                    " QUANTITY INT           NOT NULL," +
                    " TRANSACTIONPAID DOUBLE NOT NULL);";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error creating tables for save game");
        }
    }
    public void insert(String value, String table)
    {
        Statement stmt = null;
        try {
            stmt = saveGame.createStatement();
            String sql = "INSERT INTO'"+table+"'" +
                    "VALUES ("+value+")";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error inserting values for save game");
        }

    }
    public String getName()
    {
        Statement stmt = null;
        String user ="User";
        try {
            stmt = saveGame.createStatement();
            String sql = "SELECT NAME FROM USER;";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            user = rs.getString("NAME");
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error getting the name for save game");
        }
        return user;
    }

    public double getCash()
    {
        Statement stmt = null;
        double cash =20000;
        try {
            stmt = saveGame.createStatement();
            String sql = "SELECT CASH FROM USER;";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            cash = rs.getDouble("CASH");
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error getting the cash for save game");
        }
        return cash;
    }

    public void setCash(double cash)
    {
        Statement stmt = null;
        try {
            stmt = saveGame.createStatement();
            String sql = "UPDATE USER SET CASH ='"+cash+"';";
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error getting the cash for save game");
        }
    }
    public int getQuantityOfStock(String ticker)
    {
        Statement stmt = null;
        int quantity = 0;
        try {
            stmt = saveGame.createStatement();
            String sql = "SELECT QUANTITY FROM PORTFOLIO WHERE STOCKTICKER ='"+ticker+"';";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            quantity = rs.getInt("QUANTITY");
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error setting the quantity of stock for save game");
        }
        return quantity;
    }

    public void setQuantityOfStock(String ticker, int quantity)
    {
        Statement stmt = null;
        try {
            stmt = saveGame.createStatement();
            String sql = "UPDATE PORTFOLIO SET QUANTITY ='"+quantity+"'WHERE STOCKTICKER ='"+ticker+"';";
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error setting the quantity of stock for save game");
        }
    }

    public boolean checkIfStockInPortfolio(String stockTicker)
    {
        Boolean exists = false;
        Statement stmt = null;
        try{
            stmt = saveGame.createStatement();
            String sql = "SELECT * FROM PORTFOLIO WHERE STOCKTICKER ='"+stockTicker+"'";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next() ) {
                exists = false;
            }
            else{
                exists = true;
            }

        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            System.out.println("Error getting values from database");
        }
        return exists;
    }
}
