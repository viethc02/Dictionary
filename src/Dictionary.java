import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {
    public static ArrayList<Word> words = new ArrayList<Word>();
    public static int num;

    public static class TrieNode {
        Map<Character, TrieNode> children;
        boolean endOfWord;
        protected ArrayList<Word> listChildren = new ArrayList<>();

        TrieNode() {
            children = new HashMap<Character, TrieNode>();
            endOfWord = false;
        }
    }

    public static class Trie {

        private static final TrieNode root = new TrieNode();

        /**
         * add new word to trie.
         *
         * @param word
         */
        public static void insertWord(Word word) {
            String s = word.getWord_target();
            TrieNode current = root;
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);

                TrieNode node = current.children.get(ch);
                if (node == null) {
                    node = new TrieNode();
                    current.children.put(ch, node);
                }
                current = node;
            }
            current.endOfWord = true;
            current.listChildren.add(word);
        }

        /**
         * find suggestion of prefix.
         * @param prefix
         * @return list of suggestions
         */
        public static List<String> advanceSearch(String prefix) {
            List<String> autoCompWords = new ArrayList<String>();

            TrieNode currentNode = root;
            for (int i = 0; i < prefix.length(); i++) {
                currentNode = currentNode.children.get(prefix.charAt(i));
                if (currentNode == null) return autoCompWords;
            }

            searchWords(currentNode, autoCompWords, prefix);
            return autoCompWords;
        }

        /**
         * find end of word to add to list.
         *
         * @param currentNode
         * @param autoCompWords
         * @param word
         */
        private static void searchWords(TrieNode currentNode, List<String> autoCompWords, String word) {
            if (currentNode == null) return;

            if (currentNode.endOfWord) {
                autoCompWords.add(word);
            }

            Map<Character, TrieNode> map = currentNode.children;
            for (Character c : map.keySet()) {
                searchWords(map.get(c), autoCompWords, word + c);
            }
        }

        /**
         * find data of word in trie.
         *
         * @param prefix
         * @return
         */
        public static Word TrieLookUp(String prefix) {
            TrieNode currentNode = root;
            for (int i = 0; i < prefix.length(); i++) {
                currentNode = currentNode.children.get(prefix.charAt(i));
                if (currentNode == null) return null;
            }

            Word w = new Word();
            if (currentNode.endOfWord) {
                for (Word wo : currentNode.listChildren) {
                    if (wo.getWord_target().equals(prefix)) {
                        return wo;
                    }
                }
            }
            return null;
        }

        /**
         * delete word in trie.
         * @param word
         */
        public static void deleteWord(String word) {
            TrieNode currentNode = root;
            TrieNode deleteNode = new TrieNode();
            for (int i = 0; i < word.length(); i++) {
                currentNode = currentNode.children.get(word.charAt(i));
                if (currentNode == null) return ;
            }

            currentNode.endOfWord = false;
            for (Word w: currentNode.listChildren) {
                if (w.getWord_target().equals(word)) {
                    currentNode.listChildren.remove(w);
                    break;
                }
            }
        }

        /**
         * find and change detail of word.
         *
         * @param newWord
         */
        public static void EditWord(Word newWord) {
            String WordEdit = newWord.getWord_target();
            TrieNode currentNode = root;
            for (int i = 0; i < WordEdit.length(); i++) {
                currentNode = currentNode.children.get(WordEdit.charAt(i));
            }
            for (int i = 0; i < currentNode.listChildren.size(); i++) {
                if (currentNode.listChildren.get(i).getWord_target().equals(WordEdit)) {
                    currentNode.listChildren.get(i).setWord_explain(newWord.getWord_explain());
                    currentNode.listChildren.get(i).setWord_pronunciation(newWord.getWord_pronunciation());
                    currentNode.listChildren.get(i).setWord_type(newWord.getWord_type());
                    return ;
                }
            }
        }
    }
}
