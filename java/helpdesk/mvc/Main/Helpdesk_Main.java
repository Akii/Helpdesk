package Helpdesk.java.helpdesk.mvc.Main;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.mvc.Model.MainModel; 
import Helpdesk.java.helpdesk.mvc.View.Loading_Frame;
import Helpdesk.java.helpdesk.mvc.Controller.MainController;
import Helpdesk.java.helpdesk.mvc.View.Main_Frame;

public class Helpdesk_Main extends MainModel {
    public static void main(String[] args)  {
       // Loading SplashScreen
       Loading_Frame splash = new Loading_Frame(2400);
    }
    

    /**************************
    * Model - View - Controller
    **************************/
    public static void setMVC () {
            Main_Frame _view = new Main_Frame();
            MainController controller = new MainController(_view);
            _view.showView();
    }
}