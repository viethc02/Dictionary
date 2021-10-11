import java.util.Scanner;

public class DictionaryCommandline {
    public static void showAllWords() {
        System.out.format("%-15s %-15s %-10s \n",
                "No",
                "English",
                "Vietnamese");

        for (int i = 0; i < Dictionary.num; i++) {
            System.out.format("%-15s %-15s %-10s \n",
                    i,
                    Dictionary.words.get(i).word_target,
                    Dictionary.words.get(i).word_explain);
        }
    }

    public static void showAllWordsUpdate() {
        for (int i = 0; i < Dictionary.num; i++) {
            System.out.println("Word: " + Dictionary.words.get(i).word_target);
            if (Dictionary.words.get(i).word_pronunciation != "") {
                System.out.println(Dictionary.words.get(i).word_pronunciation);
            }
            if (Dictionary.words.get(i).word_type != "") {
                System.out.println(Dictionary.words.get(i).word_type);
            }
            if (Dictionary.words.get(i).word_explain != "")
            System.out.println(Dictionary.words.get(i).word_explain);
        }

    }

    public static void dictionaryBasic() {
        DictionaryManagement.insertFromCommandline();
        showAllWords();
    }

    public static void Advanced() {
        DictionaryManagement.insertFromFile();
        showAllWords();
        DictionaryManagement.dictionaryLookup();
    }

    public static void dictionarySearcher() {
        Scanner sc = new Scanner(System.in);
        String wordSearch = sc.nextLine();
        System.out.println("Recommend: ");
        for (Word word: Dictionary.words) {
            if (word.word_target.contains(wordSearch)) {
                System.out.println(word.word_target);
            }
        }
    }
}
