import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WallaRobot extends BaseRobot implements WordsMap {
    Map<String, Integer> map;
    ArrayList<String> sitesUrl;

    public WallaRobot() throws IOException {
        super("https://www.walla.co.il/");
        Document walla = Jsoup.connect(getRootWebsiteUrl()).get();
        for (Element teasers : walla.getElementsByClass("with-roof with-timer")) {
            sitesUrl.add(teasers.child(0).attributes().get("href"));
        }
        Element section = walla.getElementsByClass("css-1ugpt00 css-a9zu5q css-rrcue5 ").get(0);
        for (Element smallTeasers : section.getElementsByTag("a")) {
            sitesUrl.add(smallTeasers.attributes().get("href"));
        }
    }

    @Override
    public Map<String, Integer> getWordsStatistics() throws IOException {
        return null;
    }

    @Override
    public int countInArticlesTitles(String text) throws IOException {
        return 0;
    }

    @Override
    public String getLongestArticleTitle() throws IOException {
        return null;
    }

    @Override
    public String accessSite(String site) throws IOException {
        return null;
    }
}
