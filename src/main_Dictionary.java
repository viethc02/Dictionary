import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
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
        //window.getContentPane().setBackground(Color.lightGray);

        JPanel panel = new JPanel();
        window.add(panel);

        JTextArea tieng_viet = new JTextArea();
        tieng_viet.setEditable(false);
        tieng_viet.setFont(new Font("Monospaced", Font.TYPE1_FONT, 14));

        JScrollPane scrollPane1 = new JScrollPane(tieng_viet);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane1);

        JTextArea tieng_anh = new JTextArea();
        tieng_anh.setFont(new Font("Monospaced", Font.TYPE1_FONT, 14));
        tieng_anh.setLineWrap(true);
        //tieng_anh.setBackground(Color.pink);
        tieng_anh.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.print(DictionaryManagement.dictionarySearcher(tieng_anh.getText()));
            }
        });

        JScrollPane scrollPane2 = new JScrollPane(tieng_anh);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane2);

        JButton button1 = new JButton("Tiếng Anh");
        JButton button2 = new JButton("Tiếng Việt");

        JButton dich = new JButton("Dịch");
        dich.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = tieng_anh.getText().trim();
                tieng_viet.setText(DictionaryManagement.dictionaryLookup(data));
            }
        });

        JButton speak = new JButton("Phát âm");
        speak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundSystem.TurnOnSpeech(tieng_anh.getText().trim());
            }
        });

        JButton change = new JButton("Sửa");
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tieng_anh.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(window, "Hãy nhập từ cần sửa!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JFrame frame = new JFrame("Sửa");
                    frame.setSize(400, 400);
                    frame.setLocation((d.width - frame.getWidth()) / 2, (d.height - frame.getHeight()) / 2);

                    JPanel jPanel = new JPanel();
                    frame.add(jPanel);

                    JLabel label1 = new JLabel(tieng_anh.getText());

                    JLabel label2 = new JLabel("Phiên âm:");
                    JTextArea phienam = new JTextArea();
                    phienam.setToolTipText("Phiên âm");
                    phienam.setLineWrap(true);

                    JLabel label3 = new JLabel("Từ loại:");
                    JTextArea tuloai = new JTextArea();
                    tuloai.setToolTipText("Từ loại");
                    tuloai.setLineWrap(true);

                    JLabel label4 = new JLabel("Nghĩa:");
                    JTextArea nghia = new JTextArea();
                    nghia.setToolTipText("Nhập nghĩa (bắt buộc)");
                    nghia.setLineWrap(true);
                    JScrollPane jScrollPane = new JScrollPane(nghia);
                    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                    JButton ok = new JButton("OK");
                    ok.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (nghia.getText().trim().equals("")) {
                                JOptionPane.showMessageDialog(frame, "Bạn cần nhập nghĩa!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                Word newWord = new Word(tieng_anh.getText(), nghia.getText(), tuloai.getText(), phienam.getText());
                                int result = JOptionPane.showConfirmDialog(frame, "Are you sure want to change this word?",
                                        "Edit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (result == JOptionPane.YES_OPTION) {
                                    JOptionPane.showMessageDialog(frame, DictionaryManagement.ChangeWordInDictionary(newWord));
                                }
                            }
                        }
                    });

                    GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
                    frame.getContentPane().setLayout(groupLayout);
                    groupLayout.setHorizontalGroup(
                            groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(groupLayout.createSequentialGroup()
                                            .addComponent(label1)
                                            .addGap(10,10,10)
                                            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                    .addComponent(label2)
                                                    .addComponent(phienam, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label3)
                                                    .addComponent(tuloai, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                    //.addComponent(nghia, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label4)
                                                    .addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(groupLayout.createSequentialGroup()
                                            .addGap(150, 150, 150)
                                            .addComponent(ok, 100, 100, 100))
                    );

                    groupLayout.setVerticalGroup(
                            groupLayout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(label1)
                                    .addGap(10, 10, 10)
                                    .addComponent(label2)
                                    .addComponent(phienam, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(label3)
                                    .addComponent(tuloai, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    //.addComponent(nghia, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label4)
                                    .addComponent(jScrollPane)
                                    .addGap(20, 20, 20)
                                    .addComponent(ok, 30, 30, 30)
                                    .addGap(5, 5, 5)
                    );

                    frame.setVisible(true);
                }
            }
        });

        JButton add = new JButton("Thêm từ");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Thêm từ");
                frame.setSize(400, 400);
                frame.setLocation((d.width - frame.getWidth()) / 2, (d.height - frame.getHeight()) / 2);

                JPanel jPanel = new JPanel();
                frame.add(jPanel);

                JLabel label1 = new JLabel("Từ:");
                JTextArea tu = new JTextArea();
                tu.setToolTipText("Nhập từ Tiếng Anh (bắt buộc)");
                tu.setLineWrap(true);

                JLabel label2 = new JLabel("Phiên âm:");
                JTextArea phienam = new JTextArea();
                phienam.setToolTipText("Phiên âm");
                phienam.setLineWrap(true);

                JLabel label3 = new JLabel("Từ loại:");
                JTextArea tuloai = new JTextArea();
                tuloai.setToolTipText("Từ loại");
                tuloai.setLineWrap(true);

                JLabel label4 = new JLabel("Nghĩa:");
                JTextArea nghia = new JTextArea();
                nghia.setToolTipText("Nhập nghĩa (bắt buộc)");
                nghia.setLineWrap(true);
                JScrollPane jScrollPane = new JScrollPane(nghia);
                jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                JButton ok = new JButton("OK");
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (tu.getText().trim().equals("")) {
                            JOptionPane.showMessageDialog(frame, "Bạn cần nhập từ Tiếng Anh!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (nghia.getText().trim().equals("")) {
                                JOptionPane.showMessageDialog(frame, "Bạn cần nhập nghĩa!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                Word newWord = new Word(tu.getText(), nghia.getText(), tuloai.getText(), phienam.getText());
                                int result = JOptionPane.showConfirmDialog(frame, "Are you sure want to add this word?",
                                        "Add", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (result == JOptionPane.YES_OPTION) {
                                    JOptionPane.showMessageDialog(frame, DictionaryManagement.addNewWord(newWord));
                                }
                            }
                        }
                    }
                });

                GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
                frame.getContentPane().setLayout(groupLayout);
                groupLayout.setHorizontalGroup(
                        groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addGap(10,10,10)
                                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(label1)
                                                .addComponent(tu, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label2)
                                                .addComponent(phienam, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label3)
                                                .addComponent(tuloai, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                //.addComponent(nghia, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label4)
                                                .addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE)))
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addGap(150, 150, 150)
                                        .addComponent(ok, 100, 100, 100))
                );

                groupLayout.setVerticalGroup(
                        groupLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(label1)
                                .addComponent(tu, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(label2)
                                .addComponent(phienam, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(label3)
                                .addComponent(tuloai, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                //.addComponent(nghia, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label4)
                                .addComponent(jScrollPane)
                                .addGap(20, 20, 20)
                                .addComponent(ok, 30, 30, 30)
                                .addGap(5, 5, 5)
                );
                frame.setVisible(true);
            }
        });

        JButton delete = new JButton("Xóa từ");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tieng_anh.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(window, "Hãy nhập từ cần xóa!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (DictionaryManagement.deleteWordInDictionary(tieng_anh.getText()) == "Word is not exist.") {
                        JOptionPane.showMessageDialog(window, "Word is not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int result = JOptionPane.showConfirmDialog(window, "Are you sure want to delete this word?",
                                "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (result == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(window, "Word has been deleted.");
                        }
                    }
                }
            }
        });

        JButton show = new JButton("Dịch văn bản");
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //DictionaryApplication.runApplication();
                try {
                    tieng_viet.setText(GoogleTranslateAPI.TranslateByGoogleAPI("en", "vi", tieng_anh.getText().trim()));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(window, "Check your connect!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        GroupLayout layout = new GroupLayout(window.getContentPane());
        window.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                //.addComponent(tieng_anh, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
                                                .addGap(5, 5, 5)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(dich)
                                                        .addComponent(show)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(button1)
                                                .addGap(20, 20, 20)
                                                .addComponent(add, 100, 100, 100)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 20, 20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(button2)
                                                .addGap(150, 150, 150)
                                                .addComponent(speak))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(change)
                                                .addGap(190, 190, 190)
                                                .addComponent(delete))))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(10, 10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(button1)
                                        .addComponent(add)
                                        .addComponent(button2)
                                        .addComponent(speak))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollPane2)
                                        //.addComponent(tieng_anh,GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(dich, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(show, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(scrollPane1))
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(change, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(delete, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5))
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
        //SoundSystem.TurnOnSpeech("test");

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
