import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Locale;

public class DictionaryApplication {
    public static void runApplication() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        //create main window
        DictionaryBackground window = new DictionaryBackground();
        window.setTitle("Dictionary");
        window.setSize(800, 500);
        window.setLocation((d.width - window.getWidth()) / 2, (d.height - window.getHeight()) / 2);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        window.setBackground(new ImageIcon("Image\\main_background.png"));

        JPanel panel = new JPanel();
        window.add(panel);

        //display the meaning of the word
        JTextArea tieng_viet = new JTextArea();
        tieng_viet.setEditable(false);
        tieng_viet.setFont(new Font("Monospaced", Font.TYPE1_FONT, 14));
        tieng_viet.setLineWrap(true);
        tieng_viet.setWrapStyleWord(true);

        JScrollPane scrollPane1 = new JScrollPane(tieng_viet);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane1);

        //create word list
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        for (int i = 0; i < Dictionary.num; i++) {
            listModel.addElement(Dictionary.words.get(i).getWord_target());
        }
        JList<String> list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        panel.add(scrollPane);

        //enter the word
        JTextArea tieng_anh = new JTextArea();
        tieng_anh.setFont(new Font("Monospaced", Font.TYPE1_FONT, 14));
        tieng_anh.setLineWrap(true);
        tieng_anh.setWrapStyleWord(true);
        tieng_anh.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                listModel.removeAllElements();
                for (int i = 0; i < Dictionary.num; i++) {
                    if (Dictionary.words.get(i).getWord_target().startsWith(tieng_anh.getText().trim())) {
                        listModel.addElement(Dictionary.words.get(i).getWord_target());
                    }
                }

                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        tieng_anh.setText(list.getSelectedValue());
                        tieng_viet.setText(DictionaryManagement.dictionaryLookup(tieng_anh.getText().trim()));
                    }
                });
                tieng_viet.setText(DictionaryManagement.dictionaryLookup(tieng_anh.getText().trim()));
            }
        });

        JScrollPane scrollPane2 = new JScrollPane(tieng_anh);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane2);

        JButton button1 = new JButton("Tiếng Anh");
        JButton button2 = new JButton("Tiếng Việt");

        //create button to translate word
        JButton dich = new JButton("Dịch");
        dich.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = tieng_anh.getText().trim();
                tieng_viet.setText(DictionaryManagement.dictionaryLookup(data));
            }
        });

        //create English pronunciation button
        JButton speak = new JButton("Phát âm");
        speak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundSystem.TurnOnSpeech(tieng_anh.getText().trim());
            }
        });

        //create edit button
        JButton change = new JButton("Sửa");
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tieng_anh.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(window, "Hãy nhập từ cần sửa!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    //create window to edit word
                    DictionaryBackground frame = new DictionaryBackground();
                    frame.setTitle("Sửa");
                    frame.setBackground(new ImageIcon("Image\\edit_background.jpg"));
                    frame.setSize(400, 400);
                    frame.setLocation((d.width - frame.getWidth()) / 2, (d.height - frame.getHeight()) / 2);

                    JPanel jPanel = new JPanel();
                    frame.add(jPanel);

                    JLabel label1 = new JLabel(tieng_anh.getText().toUpperCase(Locale.ROOT));

                    JLabel label2 = new JLabel("Phiên âm:");
                    JTextArea phienam = new JTextArea();
                    phienam.setFont(new Font("Monospaced", Font.TYPE1_FONT, 14));
                    phienam.setToolTipText("Phiên âm");
                    phienam.setLineWrap(true);

                    JLabel label3 = new JLabel("Từ loại:");
                    JTextArea tuloai = new JTextArea();
                    tuloai.setFont(new Font("Monospaced", Font.TYPE1_FONT, 14));
                    tuloai.setToolTipText("Từ loại");
                    tuloai.setLineWrap(true);

                    JLabel label4 = new JLabel("Nghĩa:");
                    JTextArea nghia = new JTextArea();
                    nghia.setFont(new Font("Monospaced", Font.TYPE1_FONT, 14));
                    nghia.setToolTipText("Nhập nghĩa");
                    nghia.setLineWrap(true);
                    JScrollPane jScrollPane = new JScrollPane(nghia);
                    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                    JButton ok = new JButton("OK");
                    ok.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (nghia.getText().trim().equals("") && tuloai.getText().trim().equals("") && phienam.getText().trim().equals("")) {
                                JOptionPane.showMessageDialog(frame, "Bạn chưa nhập thay đổi!", "Error", JOptionPane.ERROR_MESSAGE);
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

                    //set layout for edit word window
                    GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
                    frame.getContentPane().setLayout(groupLayout);
                    groupLayout.setHorizontalGroup(
                            groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(label1)
                                    .addGroup(groupLayout.createSequentialGroup()
                                            .addGap(10,10,10)
                                            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                    .addComponent(label2)
                                                    .addComponent(phienam, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label3)
                                                    .addComponent(tuloai, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
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

        //create add button
        JButton add = new JButton("Thêm từ");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //create window to add word
                DictionaryBackground frame = new DictionaryBackground();
                frame.setTitle("Thêm từ");
                frame.setSize(400, 400);
                frame.setLocation((d.width - frame.getWidth()) / 2, (d.height - frame.getHeight()) / 2);
                frame.setBackground(new ImageIcon("Image\\add_background.jpg"));

                JPanel jPanel = new JPanel();
                frame.add(jPanel);

                JLabel label1 = new JLabel("Từ:");
                JTextArea tu = new JTextArea();
                tu.setFont(new Font("Monospaced", Font.TYPE1_FONT, 14));
                tu.setToolTipText("Nhập từ Tiếng Anh (bắt buộc)");
                tu.setLineWrap(true);
                tu.setWrapStyleWord(true);

                JLabel label2 = new JLabel("Phiên âm:");
                JTextArea phienam = new JTextArea();
                phienam.setFont(new Font("Monospaced", Font.TYPE1_FONT, 14));
                phienam.setToolTipText("Phiên âm");
                phienam.setLineWrap(true);
                tu.setWrapStyleWord(true);

                JLabel label3 = new JLabel("Từ loại:");
                JTextArea tuloai = new JTextArea();
                tuloai.setFont(new Font("Monospaced", Font.TYPE1_FONT, 14));
                tuloai.setToolTipText("Từ loại");
                tuloai.setLineWrap(true);
                tuloai.setWrapStyleWord(true);

                JLabel label4 = new JLabel("Nghĩa:");
                JTextArea nghia = new JTextArea();
                nghia.setFont(new Font("Monospaced", Font.TYPE1_FONT, 14));
                nghia.setToolTipText("Nhập nghĩa (bắt buộc)");
                nghia.setLineWrap(true);
                nghia.setWrapStyleWord(true);
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

                //set layout for add word window
                GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
                frame.getContentPane().setLayout(groupLayout);
                groupLayout.setHorizontalGroup(
                        groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addGap(30,30,30)
                                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(label1)
                                                .addComponent(tu, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label2)
                                                .addComponent(phienam, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label3)
                                                .addComponent(tuloai, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
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
                                .addComponent(label4)
                                .addComponent(jScrollPane)
                                .addGap(20, 20, 20)
                                .addComponent(ok, 30, 30, 30)
                                .addGap(5, 5, 5)
                );
                frame.setVisible(true);
            }
        });

        //create delete button
        JButton delete = new JButton("Xóa từ");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tieng_anh.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(window, "Hãy nhập từ cần xóa!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int result = JOptionPane.showConfirmDialog(window, "Are you sure want to delete this word?",
                            "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(window, DictionaryManagement.deleteWordInDictionary(tieng_anh.getText()));
                    }
                }
            }
        });

        //create button to use google translate API
        JButton show = new JButton("Dịch văn bản");
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tieng_viet.setText(GoogleTranslateAPI.TranslateByGoogleAPI("en", "vi", tieng_anh.getText().trim()));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(window, "Check your connection!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //set layout for main window
        GroupLayout layout = new GroupLayout(window.getContentPane());
        window.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
                                                .addGap(5, 5, 5)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(dich)
                                                        .addComponent(show)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(button1)
                                                .addGap(20, 20, 20)
                                                .addComponent(add, 100, 100, 100))
                                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE))
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
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
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
    }
}
