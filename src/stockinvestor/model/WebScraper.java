package stockinvestor.model;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

import java.util.ArrayList;
import java.util.List;



public class WebScraper {
    public static List<String> getNewsHeadlines(String site) throws JauntException {
        List<String> newsHeadlines = new ArrayList<String>();
        UserAgent userAgent = new UserAgent();      //create new userAgent (headless browser)
        userAgent.settings.autoSaveAsHTML = true;
        if (site.equals("bloomberg")) {
            userAgent.visit("http://www.bloomberg.com");       //visit google
            Elements elements = userAgent.doc.findEvery("<h1 class=\"top-news-v3__story__headline\">").findEvery("<a href=");   //find search result links
            for(Element elementz : elements) newsHeadlines.add(elementz.getText());

        }
        return newsHeadlines;
    }
}
