package sample.model;

/**
 * Created by nick on 16/08/15.
 */
public class User {
    private String name;
    private double balance;

    public User(String name) {
        this.name = name;
        this.balance = 20000;
    }
    public String getName() {
        return name;
    }

    public double getBalance(){
        return balance;
    }
    public void withdraw(double money){
        this.balance -= money;

    }
    public void deposit(double money){
        this.balance += money;
    }
}
