import javax.speech.Word;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

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
        tieng_anh.setFont(new Font("Arial", Font.TYPE1_FONT, 14));
        //tieng_anh.setBackground(Color.pink);
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
                //System.out.print(DictionaryManagement.dictionarySearcher(tieng_anh.getText()));
            }
        });

        JTextArea tieng_viet = new JTextArea();
        tieng_viet.setEditable(false);
        tieng_viet.setFont(new Font("Time New Roman", Font.TYPE1_FONT, 14));
        JScrollPane scrollPane = new JScrollPane(tieng_viet, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane);

        JButton button1 = new JButton("Tiếng Anh");
        JButton button2 = new JButton("Tiếng Việt");

        JButton dich = new JButton("Dịch");
        dich.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = tieng_anh.getText();
                //JOptionPane.showMessageDialog(window, DictionaryManagement.dictionaryLookup(data));
                tieng_viet.setText(DictionaryManagement.dictionaryLookup(data));
            }
        });

        JButton speak = new JButton("Phát âm");
        speak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundSystem.TurnOnSpeech(tieng_anh.getText());
            }
        });

        JButton change = new JButton("Sửa");
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton add = new JButton("Thêm từ");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Thêm từ");
                frame.setSize(400, 400);
                frame.setLocation((d.width - window.getWidth()) / 2, (d.height - window.getHeight()) / 2);

                JPanel panel1 = new JPanel();
                frame.add(panel1);

                JTextArea tu = new JTextArea();
                tu.setToolTipText("Nhập Từ Tiếng Anh");
                panel1.add(tu);

                JTextArea phienam = new JTextArea();
                phienam.setToolTipText("Phiên âm");
                panel1.add(phienam);

                JTextArea tuloai = new JTextArea();
                tuloai.setToolTipText("Từ loại");
                panel1.add(tuloai);

                JTextArea nghia = new JTextArea();
                nghia.setToolTipText("Nhập nghĩa");
                panel1.add(nghia);

                JButton ok = new JButton("OK");
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (tu.getText() == null) {
                            JOptionPane.showMessageDialog(frame, "Bạn cần nhập từ Tiếng Anh", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (nghia.getText() == null) {
                                JOptionPane.showMessageDialog(frame, "Bạn cần nhập nghĩa", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                Word newWord = new Word(tu.getText(), nghia.getText(), tuloai.getText(), phienam.getText());
                                //JOptionPane.showMessageDialog(frame, DictionaryManagement.addNewWord(newWord));
                            }
                        }
                    }
                });
                panel1.add(ok);

                GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
                frame.getContentPane().setLayout(groupLayout);
                groupLayout.setHorizontalGroup(
                        groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addGap(50,50,50)
                                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(tu, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(phienam, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(tuloai, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(nghia, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)))
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addGap(150, 150, 150)
                                        .addComponent(ok, 100, 100, 100))
                );

                groupLayout.setVerticalGroup(
                        groupLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(tu, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(phienam, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(tuloai, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(nghia, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(ok, 30, 30, 30)

                );
                frame.setVisible(true);
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
                                                        .addComponent(change, 100, 100, 100)
                                                        .addComponent(add, 100, 100, 100)
                                                        .addComponent(delete, 100, 100, 100)
                                                        .addComponent(show, 100, 100, 100)))
                                        .addComponent(button1))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 20, 20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        //.addComponent(scrollPane)
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
                                        .addGroup(layout.createSequentialGroup()
                                        .addComponent(dich, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(speak, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(change, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(add, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(delete, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(show, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                                        //.addComponent(scrollPane)
                                        .addComponent(tieng_viet, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
//                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                                        .addGap(6, 6, 6)
//                                        .addGroup(layout.createSequentialGroup()
//                                                .addGap(10, 10, 10)
//                                                .addComponent(speak, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
//                                                .addGap(30, 30, 30)
//                                                .addComponent(add, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
//                                                .addGap(30, 30, 30)
//                                                .addComponent(delete, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
//                                                .addGap(30, 30, 30)
//                                                .addComponent(show, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))))
                        ));

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
