package stockinvestor.model;

import sql.Database;

/**
 * Created by nt305 on 27/02/16.
 */
public class Singleton {
    private final static Singleton instance = new Singleton();
    private sql.Database database = new Database();

    public static Singleton getInstance() {
        return instance;
    }


    public sql.Database currentDatabase() {
        return database;
    }
}