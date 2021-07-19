import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MakoRobot extends BaseRobot implements WordsMap {
    private Map<String, Integer> map = new HashMap<>();
    private final ArrayList<String> webUrl;

    public MakoRobot() throws IOException {
        super("https://www.mako.co.il/");
        String url, begging = "https://www.mako.co.il/";
        webUrl = new ArrayList<>();
        Document mako = Jsoup.connect(getRootWebsiteUrl()).get();
        for (Element elements : mako.getElementsByClass("teasers")) {
            for (Element element : elements.children()) {
                url = element.child(0).child(0).attributes().get("href");
                if (webUrl.add(begging + url)){
                } else {
                    url.contains(begging);
                    webUrl.add(url);
                }
            }
        }
        for (Element editionNews : mako.getElementsByClass("neo_ordering scale_image horizontal news")) {
            for (Element tagName : editionNews.getElementsByTag("h5")) {
                url = tagName.child(0).attributes().get("href");
                if (webUrl.add(begging + url)) {
                } else {
                    url.contains(begging);
                    webUrl.add(url);
                }
            }
        }
    }

    @Override
    public Map<String, Integer> getWordsStatistics() throws IOException {
        for (String url : webUrl) {
            String articleWeb;
            articleWeb = accessSite(url);
            articleWeb = correctWords(articleWeb);
            String[] words = articleWeb.split(" ");
            map = getWordsIntoMap(words, map);
        }
        return map;
    }


    @Override
    public int countInArticlesTitles(String text) throws IOException {
        Document mako = Jsoup.connect(getRootWebsiteUrl()).get();
        int count = 0;
        for (Element tagElements : mako.getElementsByTag("span")) {
            for (Element articleTitle : tagElements.getElementsByAttributeValue("data-type", "title")) {
                if (articleTitle.text().contains(text)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String getLongestArticleTitle() throws IOException {
        Document article;
        String longestArticleTitle = " ";
        int textLength = 0;
        for (String site : webUrl) {
            article = Jsoup.connect(site).get();
            String title = article.getElementsByTag("h1").get(0).text();
            StringBuilder builder = new StringBuilder();
            Element articleBody = article.getElementsByClass("article-body").get(0);
            for (Element element : articleBody.getElementsByTag("p")) {
                builder.append(element.text());
            }
            if (textLength < builder.length()) {
                textLength = builder.length();
                longestArticleTitle = title;
            }
        }
        return longestArticleTitle;
    }

    public String accessSite(String site) throws IOException {
        Document webArticle;
        StringBuilder siteText = new StringBuilder();
        webArticle = Jsoup.connect(site).get();
        siteText.append(webArticle.getElementsByTag("h1").get(0).text());
        siteText.append(" ");
        siteText.append(webArticle.getElementsByTag("h2").text());
        Element articleBody = webArticle.getElementsByClass("article-body").get(0);
        for (Element body : articleBody.getElementsByTag("p")) {
            siteText.append(" ");
            siteText.append(body.text());
        }
        return siteText.toString();
    }
}