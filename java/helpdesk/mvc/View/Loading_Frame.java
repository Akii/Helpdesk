package Helpdesk.java.helpdesk.mvc.View;
/******************
 * Imports
 ******************/
import java.awt.*;
import javax.swing.*;

public class Loading_Frame extends JWindow {

    public Loading_Frame() {
        //set content and position
        JPanel content = (JPanel)getContentPane();
        this.setSize(230, 230);
        setLocation(
            (Toolkit.getDefaultToolkit().getScreenSize().width-getSize().width) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height-getSize().height) / 2
        );
        JLabel gif = new JLabel();
        //get image gif 
        gif.setIcon(new ImageIcon(getClass().getResource("pics/screen.gif")));
        //label below the gif
        JLabel copyright = new JLabel ("@ Philipp Maier & Gui-Rong Ko", JLabel.CENTER);
        copyright.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(gif, BorderLayout.CENTER);
        content.add(copyright, BorderLayout.SOUTH);
        this.setVisible(true);
    }
}