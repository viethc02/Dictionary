import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class DictionaryApplication {
    public static void runApplication() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame window = new JFrame("Dictionary");
        window.setSize(600, 500);
        window.setLocation((d.width - window.getWidth()) / 2, (d.height - window.getHeight()) / 2);

        JPanel panel = new JPanel();
        window.add(panel);

        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(500, 500));
        label.setVerticalTextPosition(JLabel.TOP);

        String[] word_from_words = new String[Dictionary.num];
        for (int i = 0; i < Dictionary.num; i++) {
            word_from_words[i] = Dictionary.words.get(i).getWord_target();
        }

        JList<String> list = new JList<String>(word_from_words);
        JScrollPane scrollPane = new JScrollPane(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                label.setText("<html>" + list.getSelectedValue()
                        + "<br>" + "<i>" + Dictionary.words.get(list.getSelectedIndex()).getWord_pronunciation()
                        + "<br>" + Dictionary.words.get(list.getSelectedIndex()).getWord_type()
                        + "<br>" + Dictionary.words.get(list.getSelectedIndex()).getWord_explain()
                        + "</html>");
            }
        });

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(scrollPane);
        panel.add(label);

        window.setVisible(true);
    }
}
