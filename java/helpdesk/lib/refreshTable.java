package Helpdesk.java.helpdesk.lib;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.mvc.Model.FacadeModel;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import Helpdesk.java.helpdesk.mvc.View.Main_Frame;

public class refreshTable extends Thread {
    private String c_model;
    private String e_model;
    private String f_model;
    private String h_model;
    private String p_model;
    private Main_Frame _view;
    private FacadeModel fa;
    
    public refreshTable(String c_model, String e_model, String f_model, String h_model,
                               String p_model, Main_Frame _view) {
      this.c_model = c_model;
      this.e_model = e_model;
      this.f_model = f_model;
      this.h_model = h_model;
      this.p_model = p_model;
      this.fa = new FacadeModel ();
      this._view = _view;
    }
   
    /*****************
    *  JTable refresh
    ********************/
    @Override
    public void run() {
      try {  
            if (this.c_model.equals("Customer")) {
                Thread t = new Thread (fa.getCustomerTable());
                t.start();
                t.join(400);
            }
            if (this.e_model.equals("Employee")){
                Thread t = new Thread (fa.getEmployeeTable());
                t.start();
                t.join(400);
            }
            if (this.f_model.equals("Fullticket")) {
                fa.getFullticketTable().setStatus("");
                Thread t = new Thread (fa.getFullticketTable());
                t.start();
                t.join(400);
            }
            if (this.h_model.equals("History")) {
                Thread t = new Thread (fa.getHistoryTable());
                t.start();
                t.join(400);
            }
            if (this.p_model.equals("Product")) {
                Thread t = new Thread (fa.getProductTable());
                t.start();
                t.join(400);
            }
            if (_view != null) {
                Thread t = new Thread (fa.CounterRun(_view));
                t.start();
                t.join(400);
            }
      } catch (InterruptedException e) {
          Error_Frame.Error(e.toString());
      }
    }
    
}
