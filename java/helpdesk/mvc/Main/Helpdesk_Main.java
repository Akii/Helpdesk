package Helpdesk.java.helpdesk.mvc.Main;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.mvc.Model.MainModel; 
import Helpdesk.java.helpdesk.mvc.View.Loading_Frame;
import Helpdesk.java.helpdesk.mvc.Controller.MainController;
import Helpdesk.java.helpdesk.mvc.Model.CustomerTable;
import Helpdesk.java.helpdesk.mvc.Model.EmployeeTable;
import Helpdesk.java.helpdesk.mvc.Model.FullticketTable;
import Helpdesk.java.helpdesk.mvc.Model.HistoryTable;
import Helpdesk.java.helpdesk.mvc.Model.ProductTable;
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
        //   Create model, view, and controller.  They are
        //    created once here and passed to the parts that
        //    need them so there is only one copy of each.
        CustomerTable c_model = new CustomerTable();
        EmployeeTable e_model = new EmployeeTable();
        FullticketTable f_model = new FullticketTable();
        HistoryTable h_model = new HistoryTable();
        ProductTable p_model = new ProductTable();
        
             
        Main_Frame _view = new Main_Frame(c_model,e_model,f_model,h_model,p_model);
     
        MainController controller = new MainController(_view,c_model,e_model,f_model,
                                                    h_model,p_model);
        _view.showView();
    }
}