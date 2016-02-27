package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection saveGame;
    public Database(String filename)
    {
        try {
            Class.forName("org.sqlite.JDBC");
            saveGame = DriverManager.getConnection("jdbc:sqlite:" + filename +  "");
            createTables();
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
                    "(ID INT PRIMARY KEY     NOT NULL," +
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
}
