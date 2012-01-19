package Helpdesk.java.helpdesk.mvc.View;
/******************
 * Imports
 ******************/
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.*;

public class Loading_Frame extends JWindow {
private Timer timer;

    public Loading_Frame(Integer millis) {
        //set content and position
        JPanel content = (JPanel)getContentPane();
        this.setSize(230, 230);
        //this.setOpacity(0.88f);
        
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
        
        timer = new Timer();
        timer.schedule(new ExitSplashTask(this), millis);
    }
    
    
    class ExitSplashTask extends TimerTask {
        private JWindow frm;
        public ExitSplashTask(JWindow frm) {
            this.frm = frm;
        }
        @Override
        public void run() {
            frm.setVisible(false);
            new Login_Frame().run();
            frm.dispose();
        }
 
    }
}