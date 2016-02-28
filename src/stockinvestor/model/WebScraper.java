package stockinvestor.model;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class WebScraper {
    public static List<String> getNewsHeadlines(String site) throws JauntException {
        List<String> newsHeadlines = new ArrayList<String>();
        UserAgent userAgent = new UserAgent();      //create new userAgent (headless browser)
        userAgent.settings.autoSaveAsHTML = true;
        if (site.equals("bloomberg")) {
            userAgent.visit("http://www.bloomberg.com");       //visit bloomberg
            Elements elements = userAgent.doc.findEvery("<h1 class=\"top-news-v3__story__headline\">").findEvery("<a href=");   //find search result links
            for(Element elementz : elements) newsHeadlines.add(elementz.getText());

        }
        return newsHeadlines;
    }
    public static Image getLogo(String name) throws JauntException {
        String[] splitName = name.split(" ");
        String requestName = String.join("+",splitName);
        UserAgent userAgent = new UserAgent();
        userAgent.visit("https://www.bing.com/images/search?q="+requestName+"+logo");
    String firstImage = userAgent.doc.findFirst("<div class=item>").findFirst("<a href=").getAt("href");
        Image image = null;
        try {
            URL url = new URL(firstImage);
            image = SwingFXUtils.toFXImage(ImageIO.read(url),null);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return image;
    }
}
