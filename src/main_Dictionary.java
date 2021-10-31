import java.io.IOException;

public class main_Dictionary {
    public static void main(String[] args) throws IOException {
        //add words from file and show
        //DictionaryManagement.insertFromFile();
        //DictionaryCommandline.showAllWords();

        //look up ad word and show
        //DictionaryManagement.dictionaryLookup();
        //DictionaryCommandline.showAllWords();

        //delete a word and show
        //DictionaryManagement.deleteWordInDictionary();
        //DictionaryCommandline.showAllWords();

        //add words from jsonfile
        DictionaryManagement.insertFromJSONFile();

        //insert word to trie
        DictionaryManagement.insertToTrie();

        //graphic interface
        DictionaryApplication.runApplication();
    }
}
