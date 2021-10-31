import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DictionaryBackground extends JFrame {
    ImageIcon background;
    JPanel jpanel;
    public DictionaryBackground() {
        background = null;
        jpanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(background != null) {
                    g.drawImage(background.getImage(), 0,0, getWidth(), getHeight(),null);
                }
            }
        };
        setContentPane(jpanel);
    }

    public void setBackground(ImageIcon img)
    {
        this.background = img;
    }
}

//references
//https://congdongjava.com/forum/threads/java-s%E1%BB%AD-d%E1%BB%A5ng-%E1%BA%A3nh-l%C3%A0m-background-cho-jframe.2585/
