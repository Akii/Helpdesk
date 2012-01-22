package Helpdesk.java.helpdesk.mvc.Main;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.lib.LoginData;
import Helpdesk.java.helpdesk.lib.db.Database;
import Helpdesk.java.helpdesk.lib.db.MysqlDatabase;
import Helpdesk.java.helpdesk.mvc.Model.MainModel; 
import Helpdesk.java.helpdesk.mvc.View.Loading_Frame;
import Helpdesk.java.helpdesk.mvc.Controller.MainController;
import Helpdesk.java.helpdesk.mvc.View.Main_Frame;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Helpdesk_Main extends MainModel {
    public static void main(String[] args)  {
       // Loading SplashScreen
       Loading_Frame splash = new Loading_Frame(2400);
    }
    

    /**************************
    * Model - View - Controller
    **************************/
    public static void setMVC () {
        try {
            Main_Frame _view = new Main_Frame();
            MainController controller = new MainController(_view);
            _view.showView();
            String[] Array = LoginData.readSQL();
            Database db = MysqlDatabase.getInstance();
            db.first(Array[0]+":"+Array[1], Array[2], Array[3], Array[4]);
        } catch (Exception ex) {
            Logger.getLogger(Helpdesk_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}