import java.io.FileNotFoundException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class DictionaryManagement {
    public static void insertFromCommandline() {

        //input word from commandLine
        Scanner input = new Scanner(System.in);
        System.out.println("Nhap tu moi:");
        String word_in = input.nextLine();
        System.out.println("Nhap nghia:");
        String meaning = input.nextLine();

        //add word to dictionary
        Word new_word = new Word(word_in, meaning, "", "");
        Dictionary.words.add(new_word);
        Dictionary.num++;
    }

    public static void insertFromFile() {
        try
        {
            //read file
            Path path = Path.of("dictionarytextdemo.txt");
            List<String> dictionary_file = Files.readAllLines(path);

            //add word in file to dictionary
            for (String wordData: dictionary_file) {
                String[] data = wordData.split(":");
                Word new_word = new Word(data[0], data[1],"", "");
                Dictionary.words.add(new_word);
                Dictionary.num++;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertFromJSONFile() {
        JSONParser parser = new JSONParser();
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader("dictionary_final.json"));

            for (Object o : a) {
                JSONObject person = (JSONObject) o;

                String word = (String) person.get("word");

                String word_type = (String) person.get("word_type");

                String word_explan = "";
                JSONArray eplanations = (JSONArray) person.get("eplanations");
                for (int i = 0; i < eplanations.size(); i++) {
                    String explanation = eplanations.get(i).toString();
                    word_explan += explanation + '\n';
                }

                JSONArray usages = (JSONArray) person.get("usages");
                for (int i = 0; i < usages.size(); i++) {
                    String explanation = usages.get(i).toString();
                    word_explan += explanation + '\n';
                }

                String pronounciation = (String) person.get("pronounciation");

                Word new_word = new Word(word, word_explan, pronounciation, word_type);
                Dictionary.words.add(new_word);
                Dictionary.num++;

            }
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void dictionaryLookup() {
        Scanner scanner = new Scanner(System.in);
        String wordLookup = scanner.nextLine();

        for (Word word : Dictionary.words) {
            if (word.word_target.equals(wordLookup)) {
                System.out.format("%-15s %-15s \n",
                        "English",
                        "Vietnamese");

                System.out.format("%-15s %-15s \n",
                        word.word_target,
                        word.word_explain);
            }
        }
    }

    public static void ChangeWordInDictionary() {
        Scanner sc = new Scanner(System.in);
        String wordChange = sc.nextLine();

        //change meaning of word
        for (Word word: Dictionary.words) {
            if (word.word_target.equals(wordChange)) {
                System.out.println("Change Meaning of " + word.word_target + ":");
                word.word_explain = sc.nextLine();
                break;
            }
        }
    }

    public static void deleteWordInDictionary() {
        Scanner sc = new Scanner(System.in);
        String wordDelete = sc.nextLine();

        //find word need to delete and remove
        for (Word word: Dictionary.words) {
            if (word.word_target.equals(wordDelete)) {
                Dictionary.words.remove(word);
                Dictionary.num--;
                break;
            }
        }
    }

    public static void dictionaryExportToFile() {
        try {
            FileWriter myWriter = new FileWriter("dictionaryoutput.txt");
            System.out.println("Successfully wrote to the file.");

            //print dictionary to file
            for (int i = 0; i < Dictionary.num; i++) {
                myWriter.write("Word: " + Dictionary.words.get(i).word_target + "\n");
                if (Dictionary.words.get(i).word_pronunciation != "") {
                    myWriter.write(Dictionary.words.get(i).word_pronunciation + "\n");
                }
                if (Dictionary.words.get(i).word_type != "") {
                    myWriter.write(Dictionary.words.get(i).word_type + "\n");
                }
                if (Dictionary.words.get(i).word_explain != "")
                    myWriter.write(Dictionary.words.get(i).word_explain + "\n");
            }

            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
