import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class main_Dictionary {
    public static void main(String[] args) throws IOException {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame window = new JFrame("Dictionary");
        window.setSize(800, 500);
        window.setLocation((d.width - window.getWidth()) / 2, (d.height - window.getHeight()) / 2);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel();
        window.add(panel);

        JTextField tieng_anh = new JTextField();
        tieng_anh.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //System.out.print(DictionaryManagement.dictionarySearcher(tieng_anh.getText()));
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        JTextField tieng_viet = new JTextField();

        JButton button1 = new JButton("Tiếng Anh");
        JButton button2 = new JButton("Tiếng Việt");

        JButton dich = new JButton("Dịch");
        dich.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = tieng_anh.getText();
                JOptionPane.showMessageDialog(window, DictionaryManagement.dictionaryLookup(data));
            }
        });

        JButton speak = new JButton("Phát âm");
        speak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundSystem.TurnOnSpeech(tieng_anh.getText());
            }
        });

        JButton add = new JButton("Thêm từ");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton delete = new JButton("Xóa từ");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (DictionaryManagement.deleteWordInDictionary(tieng_anh.getText()) == "Word is not exist.") {
                    JOptionPane.showMessageDialog(window, "Word is not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(window, "Word has been deleted.");
                }
            }
        });

        JButton show = new JButton("Tất cả từ");
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DictionaryApplication.runApplication();
            }
        });

        GroupLayout layout = new GroupLayout(window.getContentPane());
        window.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(tieng_anh, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(dich, 100, 100, 100)
                                                        .addComponent(speak, 100, 100, 100)
                                                        .addComponent(add, 100, 100, 100)
                                                        .addComponent(delete, 100, 100, 100)
                                                        .addComponent(show, 100, 100, 100)))
                                        .addComponent(button1))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 20, 20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(tieng_viet, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button2)))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(button1)
                                        .addComponent(button2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(tieng_anh,GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dich, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tieng_viet, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(speak, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(add, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(delete, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(show, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))))
        );

        window.setVisible(true);

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

        //String text = "i'm Testing translator system of Google";
        //System.out.println("Translate test:\n" + GoogleTranslateAPI.TranslateByGoogleAPI("en","vi",text));
        //SoundSystem.TurnOnSpeech(text);

        //add words from jsonfile and show

        DictionaryManagement.insertFromJSONFile();
        //DictionaryCommandline.showAllWordsUpdate();
        DictionaryManagement.insertToTrie();

        /*
        //insert word to trie
        DictionaryManagement.insertToTrie();
        System.out.println(Dictionary.words.size());

        //test lookup, suggestion
        Word word1 = new Word("apple","tao","","");
        System.out.println(DictionaryManagement.dictionaryLookup("apple"));
        System.out.println(DictionaryManagement.deleteWordInDictionary("apple"));
        System.out.println(DictionaryManagement.addNewWord(word1));
        System.out.println(DictionaryManagement.dictionaryLookup("apple"));
        //DictionaryManagement.dictionaryExportToFile();
        */
        //DictionaryApplication.runApplication();

    }
}
