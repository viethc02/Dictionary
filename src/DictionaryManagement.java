import java.io.FileNotFoundException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
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


public class DictionaryManagement extends Dictionary {
    /**
     * insert word from commandline.
     */
    public static void insertFromCommandline() {

        //input word from commandLine
        Scanner input = new Scanner(System.in);
        System.out.println("Nhap tu moi:");
        String word_in = input.nextLine();
        System.out.println("Nhap nghia:");
        String meaning = input.nextLine();

        //add word to dictionary
        Word new_word = new Word(word_in, meaning, "", "");
        addNewWord(new_word);
    }

    /**
     * insert word from file txt.
     */
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
                words.add(new_word);
                num++;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * insert word from JSON file.
     */
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
                words.add(new_word);
                num++;

            }
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void insertToTrie() {
        for (Word word: words) {
            Trie.insertWord(word);
        }
    }

    public static String addNewWord(Word newWord) {
        if (words.contains(newWord)) {
            return "Existing word.";
        }
        words.add(newWord);
        num++;
        Trie.insertWord(newWord);
        return "Word has been added.";
    }

    /**
     * look up word by trie.
     * @param prefix
     * @return detail of word.
     */
    public static String dictionaryLookup(String prefix) {
        Word word = Trie.TrieLookUp(prefix);
        if (word == null) {
            return "No has result.";
        }
        String detail = word.getWord_target() + "\n" + word.getWord_pronunciation() + "\n"
                + word.getWord_type() + "\n" + word.getWord_explain();
        return detail;
    }

    /**
     * suggest word from prefix
     * @param prefix
     * @return list of suggestions.
     */
    public static List<String> dictionarySearcher(String prefix) {
        List<String> suggestionWords = new ArrayList<>();
        suggestionWords = Trie.advanceSearch(prefix);
        return suggestionWords;
    }

    /**
     * change detail of word in 
     * @param newWord
     * @return
     */
    public static String ChangeWordInDictionary(Word newWord) {
        //change meaning of word
        String WordEdit = newWord.getWord_target();
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWord_explain().equals(WordEdit)) {
                words.get(i).setWord_explain(newWord.getWord_explain());
                words.get(i).setWord_pronunciation(newWord.getWord_pronunciation());
                words.get(i).setWord_type(newWord.getWord_type());
            }
        }
        Trie.EditWord(newWord);
        return "Word has been changed.";
    }

    /**
     * delete word in 
     * @param wordDelete
     * @return
     */
    public static String deleteWordInDictionary(String wordDelete) {
        //find word need to delete and remove
        for (Word word: words) {
            if (word.getWord_target().equals(wordDelete)) {
                words.remove(word);
                num--;
                Trie.deleteWord(wordDelete);
                return "Word has been deleted.";
            }
        }
        return "Word is not exist.";
    }

    /**
     * add new word to json file to save word.
     */
    public static void addNewWordToJSONFile(Word newWord) {
        JSONParser jsonParser = new JSONParser();

        try {
            Object obj = jsonParser.parse(new FileReader("dictionary_final.json"));
            JSONArray jsonArray = (JSONArray)obj;

            //System.out.println(jsonArray);

            JSONObject word = new JSONObject();
            word.put("word", newWord.getWord_target());
            word.put("word_type", newWord.getWord_type());

            JSONArray explanations = new JSONArray();
            explanations.add(newWord.getWord_explain());
            word.put("eplanations", explanations);

            JSONArray usages = new JSONArray();
            word.put("usages", usages);
            word.put("pronounciation", newWord.getWord_pronunciation());

            jsonArray.add(word);

            FileWriter file = new FileWriter("dictionary_final.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * print dictionary to txt file.
     */
    public static void dictionaryExportToFile() {
        try {
            FileWriter myWriter = new FileWriter("dictionaryoutput.txt");
            System.out.println("Successfully wrote to the file.");

            //print dictionary to file
            for (int i = 0; i < num; i++) {
                myWriter.write("Word: " + words.get(i).getWord_target() + "\n");
                if (words.get(i).getWord_pronunciation() != "") {
                    myWriter.write(words.get(i).getWord_pronunciation() + "\n");
                }
                if (words.get(i).getWord_type() != "") {
                    myWriter.write(words.get(i).getWord_type() + "\n");
                }
                if (words.get(i).getWord_explain() != "")
                    myWriter.write(words.get(i).getWord_explain() + "\n");
            }

            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
