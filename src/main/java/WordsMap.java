import java.io.IOException;
import java.util.Map;

public interface WordsMap {
    String accessSite(String site) throws IOException;

    default java.util.Map<String, Integer> getWordsIntoMap(String[] wordsOfArticle, java.util.Map<String, Integer> map) {
        int value;
        for (String word : wordsOfArticle) {
            if (map.containsKey(word)) {
                value = map.get(word) + 1;
            } else {
                value = 1;
            }
            map.put(word, value);
        }
        return map;
    }

    default String correctWords(String siteText) {
        siteText = siteText.replaceAll(" ", " ");
        siteText = siteText.replaceAll(" ", " ");
        siteText = siteText.replaceAll(" ", " ");
        return siteText;
    }

}