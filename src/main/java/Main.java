import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static final int MAKO_SITE = 1;
    public static final int YNET_SITE = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the site game!");
        int score = 0;
        BaseRobot site = userSelection(scanner);

        if (site != null) {
            String userGuess = " ";
            score = score + TheMostRepeatWord(site , scanner, userGuess);
            String userInput = guessArticleHeadlines(scanner);
            int QTY = scanner.nextInt();
            try {
                score = score + chuckWordsInText(QTY, userInput, site);
            } catch (IOException e) {
                System.out.println(e);
            }
            System.out.println("congratulations on winning " + score + " points!");
        } else {
            System.out.println("Error! no site access, sorry");
        }

    }
    private static BaseRobot userSelection(Scanner scanner) {
        int selection = 0;
        while (selection < 1 || selection > 3) {
            System.out.println("Witch site do you choose?");
            System.out.println("1.Mako" + "\n" + "2.Ynet" + "\n" + "3.Walla");
            System.out.println("Your choice is:");
            selection = scanner.nextInt();
        }
        scanner.nextLine();
        try {
            switch (selection) {
                case MAKO_SITE:_SITE:
                    return new MakoRobot();
                case YNET_SITE:
                    return new YnetRobot();
                default:
                    return new WallaRobot();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
    private static int TheMostRepeatWord(BaseRobot site , Scanner scanner , String userGuess) {
        int score = 0;
        try {
            String longestText = site.getLongestArticleTitle();
            System.out.println("Enter the word you think will repeats itself the most on the site?");
            System.out.println("clue:\n" + longestText);
            Map<String, Integer> wordsInSite = site.getWordsStatistics();
            int size = 6;
            for (int i = 1; i < size; i++) {
                System.out.println("Enter a word: " + i + ":");
                userGuess = scanner.nextLine();
                score = score + wordsInSite.getOrDefault(score, 0);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return score;
    }

    private static String guessArticleHeadlines(Scanner scanner) {
        String userInput = "";
        while (userInput.length() < 1 || userInput.length() > 20) {
            System.out.println("Please Enter any text (between 1 and 20 chars) for guess which title do you think will appear on the site");
            userInput = scanner.nextLine();
            System.out.println("please guess how many time it will appear in the titles?:");
        }
        return userInput;
    }
    private static int chuckWordsInText(int QTY, String userInput, BaseRobot site) throws IOException {
        int realQTY = site.countInArticlesTitles(userInput);
        if (Math.abs(QTY - realQTY) <= 2) {
            return 250;
        }
        return 0;
    }

}