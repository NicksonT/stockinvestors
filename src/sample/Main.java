package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.model.StockInfo;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Nick's Stock Investor Simulation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        //launch(arsgs);
        StockInfo.printStock("AAPL");
    }
}
