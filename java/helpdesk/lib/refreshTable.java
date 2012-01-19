package Helpdesk.java.helpdesk.lib;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.mvc.Model.CustomerTable;
import Helpdesk.java.helpdesk.mvc.Model.EmployeeTable;
import Helpdesk.java.helpdesk.mvc.Model.FullticketTable;
import Helpdesk.java.helpdesk.mvc.Model.HistoryTable;
import Helpdesk.java.helpdesk.mvc.Model.ProductTable;

public class refreshTable extends Thread {
    CustomerTable c_model;
    EmployeeTable e_model;
    FullticketTable f_model;
    HistoryTable h_model;
    ProductTable p_model;
    
    public refreshTable(CustomerTable c_model, EmployeeTable e_model,
                               FullticketTable f_model, HistoryTable h_model,
                               ProductTable p_model) {
      if (c_model != null)
      this.c_model = c_model;
      if (e_model != null)
      this.e_model = e_model;
      if (f_model != null)
      this.f_model = f_model;
      if (h_model != null)
      this.h_model = h_model;
      if (p_model != null)
      this.p_model = p_model;
    }
   
    /*****************
    *  JTable refresh
    ********************/
    @Override
    public void run() {
      if (this.c_model != null)
        c_model.showData();
      if (this.e_model != null)
        e_model.showData();
      if (this.f_model != null)
        f_model.showData("");
      if (this.h_model != null)
        h_model.showData();
      if (this.p_model != null)
        p_model.showData();
    }
}
