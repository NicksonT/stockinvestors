package stockinvestor.controller;

import com.jaunt.JauntException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;
import stockinvestor.model.MainScreen;
import stockinvestor.model.Singleton;
import stockinvestor.model.StockInfo;
import stockinvestor.model.WebScraper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class MainScreenController implements Initializable {

    @FXML
    private Label london;
    @FXML
    private Label newYork;
    @FXML
    private Label tokyo;
    @FXML
    private Label news;
    @FXML
    private Label name;
    @FXML
    private Label money;
    @FXML
    private VBox rightPane;
    @FXML
    private TableView stockPortfolio;
    @FXML
    private TableColumn stockTicker;
    @FXML
    private TableColumn stockName;
    @FXML
    private TableColumn stockQuantity;
    @FXML
    private TableColumn stockPrice;

    private ObservableList<ObservableList> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(Singleton.getInstance().currentDatabase().getName());
        money.setText("Balance: " + round(Singleton.getInstance().currentDatabase().getCash(),2) + "USD");
        String londonTime = MainScreen.getTime("Europe/London");
        String nyTime = MainScreen.getTime("America/New_York");
        String tokyoTime = MainScreen.getTime("Asia/Tokyo");
        refreshTable();
        try {
            List<String> bloomberg = (WebScraper.getNewsHeadlines("bloomberg"));
            news.setText(bloomberg.get(1));
        } catch (JauntException e) {
        }

        london.setText(london.getText() + londonTime);
        newYork.setText(newYork.getText() + nyTime);
        tokyo.setText(tokyo.getText() + tokyoTime);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                money.setText("Balance: " + round(Singleton.getInstance().currentDatabase().getCash(), 2) + "USD");
                                String londonTime = MainScreen.getTime("Europe/London");
                                String nyTime = MainScreen.getTime("America/New_York");
                                String tokyoTime = MainScreen.getTime("Asia/Tokyo");
                                london.setText("Time in London: " + londonTime);
                                newYork.setText("Time in New York: " + nyTime);
                                tokyo.setText("Time in Tokyo: " + tokyoTime);
                                refreshTable();
                            }
                        })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void buyButton(ActionEvent event) throws IOException {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(FXMLLoader.load(getClass().getResource("../fxml/SearchPanel.fxml")));


    }

    @FXML
    private void sellStockAction(ActionEvent event) throws IOException {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(FXMLLoader.load(getClass().getResource("../marketpanels/fxml/LSEPanel.fxml")));


    }
    @FXML
    private void refreshTable()
    {
        ResultSet rs;
        stockPortfolio.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        stockPortfolio.setEditable(false);
        data = FXCollections.observableArrayList();
        try {
            rs = Singleton.getInstance().currentDatabase().getStockData();
            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                if(rs.getInt(3) > 0) {
                    for (int i = 1; i <= 3; i++) {
                        row.add(rs.getString(i));
                    }

                    data.add(row);
                }

            }
            stockPortfolio.setItems(data);
            rs.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            System.out.println("Error whilst traversing ResultSet");
        }
        stockTicker.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(0).toString());
            }


        });
        stockName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(1).toString());
            }


        });
        stockQuantity.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(2).toString());
            }

        });
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
