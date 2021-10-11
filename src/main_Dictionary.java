import java.io.IOException;

public class main_Dictionary {
    public static void main(String[] args) throws IOException {

        //Dictionary dict = new Dictionary();
        //input and show first
        //DictionaryManagement.insertFromCommandline();
        //DictionaryCommandline.showAllWords();

        //input and show second
        //DictionaryManagement.insertFromCommandline();
        //DictionaryCommandline.showAllWords();

        //add words from file and show
        //DictionaryManagement.insertFromFile();
        //DictionaryCommandline.showAllWords();

        //look up ad word and show
        //DictionaryManagement.dictionaryLookup();
        //DictionaryCommandline.showAllWords();

        //delete a word and show
        //DictionaryManagement.deleteWordInDictionary();
        //DictionaryCommandline.showAllWords();

        //Seacher a word and show recommend
        //DictionaryCommandline.dictionarySearcher();



        //String text = "i'm Testing translator system of Google";
        //System.out.println("Translate test:\n" + GoogleTranslateAPI.TranslateByGoogleAPI("en","vi",text));
        //SoundSystem.TurnOnSpeech(text);

        //add words from jsonfile and show
        DictionaryManagement.insertFromJSONFile();
        //DictionaryCommandline.showAllWordsUpdate();
        System.out.println(Dictionary.words.size());
        DictionaryManagement.dictionaryExportToFile();
    }
}
