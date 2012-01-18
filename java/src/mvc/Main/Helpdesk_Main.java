package mvc.Main;
/******************
 * Imports
 ******************/
import mvc.Model.MainModel; 
import mvc.View.Login_Frame;
import mvc.View.Loading_Frame;
import mvc.Controller.MainController;
import mvc.Model.CustomerTable;
import mvc.Model.EmployeeTable;
import mvc.Model.FullticketTable;
import mvc.Model.HistoryTable;
import mvc.Model.ProductTable;
import mvc.View.Main_Frame;

public class Helpdesk_Main extends MainModel {
    public static void main(String[] args)  {
       // Loading SplashScreen
       Loading_Frame splash = new Loading_Frame();
       //Thread Login = new Thread (new Login_Frame());
       Login_Frame Login = new Login_Frame();
       Login.run();
       //dispose SplashScreen
       splash.dispose();   
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