package Helpdesk.java.helpdesk.mvc.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import Helpdesk.java.helpdesk.lib.ComboBox;
import Helpdesk.java.helpdesk.lib.refreshTable;
import Helpdesk.java.helpdesk.mvc.Model.CategoryModel;
import Helpdesk.java.helpdesk.mvc.Model.Counter;
import Helpdesk.java.helpdesk.mvc.Model.FullticketTable;
import Helpdesk.java.helpdesk.mvc.Model.HistoryTable;
import Helpdesk.java.helpdesk.mvc.Model.Product;
import Helpdesk.java.helpdesk.mvc.Model.ProductInv;
import Helpdesk.java.helpdesk.mvc.Model.StatusModel;
import Helpdesk.java.helpdesk.mvc.Model.Ticket;
import Helpdesk.java.helpdesk.mvc.View.Ticket_Frame;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import Helpdesk.java.helpdesk.mvc.View.Main_Frame;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;

public class TController implements Runnable{
    private Integer ID;
    private FullticketTable f_model;
    private HistoryTable h_model;
    private Main_Frame main;
    private Ticket_Frame _view;
    private String sol,note,pname;
    private StatusModel s_model;
    private CategoryModel ca_model;
    
    public TController(Integer ID, FullticketTable f_model,HistoryTable h_model, Main_Frame main, Ticket_Frame frame) {
        this.ID = ID;  
        this.f_model = f_model;
        this.h_model = h_model;
        this.main = main;
        this._view = frame;
        addListener();
    }
    
    @Override
    public void run() {
        if (this.ID != null) {
            searching (this.ID);
        }
        this._view.init();
    }
    
    private void addListener() {
        this._view.setbtn_cancelListener(new btn_cancelListener());
        this._view.setbtn_saveListener(new btn_saveListener());
        this._view.setchb_newListener(new chb_newListener());
        this._view.setFocusListener(new IDFocusListener());
        this.s_model = new StatusModel();
        s_model.StatusModel();
        this.ca_model = new CategoryModel();
        ca_model.CategoryModel();
        init();
    }
    
    private void init() {
        _view.cmb_status.setModel(new javax.swing.DefaultComboBoxModel(Ticket.Ticket_ComboBox(4).toArray(
                                  new Object[Ticket.Ticket_ComboBox(4).size()])));
        _view.cmb_category.setModel(new javax.swing.DefaultComboBoxModel(Ticket.Ticket_ComboBox(3).toArray(
                                    new Object[Ticket.Ticket_ComboBox(3).size()])));
        _view.cmb_eID.setModel(new javax.swing.DefaultComboBoxModel(Ticket.Ticket_ComboBox(2).toArray(
                               new Object[Ticket.Ticket_ComboBox(2).size()])));
        _view.cmb_cID.setModel(new javax.swing.DefaultComboBoxModel(Ticket.Ticket_ComboBox(1).toArray(
                               new Object[Ticket.Ticket_ComboBox(1).size()])));
        _view.cmb_product.setModel(new javax.swing.DefaultComboBoxModel(Product.Product_ComboBox().toArray(
                               new Object[Product.Product_ComboBox().size()])));
    }
    
      /*************************************
      * 
      *     ButtonListener
      * 
      **************************************/

    class btn_cancelListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {  
            _view.dispose();
        }
    }
    
      private class IDFocusListener extends FocusAdapter {
    @Override
    public void focusLost(FocusEvent arg0) {
           try {
            String Str = _view.edt_ID.getText();
            if (!"".equals(Str)) { 
                searching(Integer.parseInt (Str));  
            }
          } catch (NullPointerException E){
            Error_Frame.Error("ID not found");
          } catch (NumberFormatException E) {
            Error_Frame.Error("Please use only number for ID");
          }
    }
  }
    
      /*************************************
      *   what a nice code..!! 
      *   TODO - change that f... s...
      **************************************/
    class btn_saveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {  
           try {
            //set timestamp for "create tickets" and "update tickets"
            Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            //send Error frame if one of these textfield are empty
            if ("".equals(_view.edt_topic.getText()) || "".equals(_view.edt_problem.getText())){
                Error_Frame.Error("Please fill out: Topic and Problem"); 
            } else {
                    Integer noEm = null;
                    ID = null;
                    if (note != null || sol != null) {
                        if (!"".equals(_view.edt_solution.getText()) && !"NULL".equals(_view.edt_solution.getText()) &&
                            !" ".equals(_view.edt_solution.getText()) && !"null".equals(_view.edt_solution.getText()) &&
                            _view.edt_solution.getText() != null && !sol.equals(_view.edt_solution.getText())) {
                            sol = _view.edt_solution.getText();
                        }
                        if (!"".equals(_view.edt_note.getText()) && !"NULL".equals(_view.edt_note.getText()) &&
                            !" ".equals(_view.edt_note.getText()) && !"null".equals(_view.edt_note.getText()) &&
                            _view.edt_note.getText() != null && !note.equals(_view.edt_note.getText())) {
                            note = _view.edt_note.getText();
                        }
                    }
                    if (_view.cmb_eID.getSelectedItem() != "") {
                        noEm = ComboBox.getEID((String)_view.cmb_eID.getSelectedItem());
                    }
                //check checkbox "new Ticket"
                if (_view.chb_new.getSelectedObjects() == null) {
                    ID = Integer.parseInt (_view.edt_ID.getText());  
                    //get timestamp and string from textfield and update ticket
                    Ticket updateTicket = new Ticket (ID,
                    (Integer)_view.cmb_cID.getSelectedItem(),noEm,
                    ca_model.getCategoryObjectID((String)_view.cmb_category.getSelectedItem()),
                    s_model.getStatusObjectID((String)_view.cmb_status.getSelectedItem()),
                    _view.edt_topic.getText(), _view.edt_problem.getText(),
                    note, sol, _view.edt_created.getText(),
                    currentTimestamp.toString());
                    updateTicket.updateTicket(ID);
                        if (!"".equals((String)_view.cmb_product.getSelectedItem())) {
                            ProductInv Productinv = new ProductInv(ID,
                            ComboBox.getPID(null,(String)_view.cmb_product.getSelectedItem()));
                            if ("".equals(pname) || pname == null) {
                                Productinv.newInvProduct(); 
                            } else {
                                Productinv.updateInvProduct();
                            }
                        }
                } else {
                    //get timestamp and string from textfield and create ticket
                    Ticket newTicket = new Ticket (null,
                    (Integer)_view.cmb_cID.getSelectedItem(),noEm,
                    ca_model.getCategoryObjectID((String)_view.cmb_category.getSelectedItem()),
                    s_model.getStatusObjectID((String)_view.cmb_status.getSelectedItem()),
                    _view.edt_topic.getText(), _view.edt_problem.getText(),
                    note,sol,currentTimestamp.toString(),
                    currentTimestamp.toString());
                    Integer tid = newTicket.newTicket();
                    if (!"".equals((String)_view.cmb_product.getSelectedItem())) {
                        ProductInv Productinv = new ProductInv(tid,
                        ComboBox.getPID(null,(String)_view.cmb_product.getSelectedItem()));
                        Productinv.newInvProduct();
                    }
                }
                //refresh jtable
                refreshTable A1 = new refreshTable(null, null, f_model, h_model, null);
                A1.start();
                //count ticket status for fullticket control buttons
                //timer to prevent connection link lost
                Timer timer = new Timer();
                timer.schedule  (new Count(), 600);
                _view.dispose();
            }
        } catch (NumberFormatException ev) {
              Error_Frame.Error("Please use only number for ID");
        } catch (Exception ev) {
              Error_Frame.Error(e.toString()); 
        }
        }
    }
    
    
      /*************************************
      * 
      *     Timer
      * 
      **************************************/
    class Count extends TimerTask {
        @Override
        public void run() {
                Counter A2 = new Counter(main);
                A2.start();
        }
    }
    
    
      /*************************************
      * 
      *     Checkbox - ItemListener
      * 
      **************************************/
     
    class chb_newListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent e) {
         if (e.getStateChange() == ItemEvent.DESELECTED){
            _view.edt_ID.setVisible(true);
         } else {
            _view.edt_ID.setVisible(false);
            _view.edt_ID.setText("");
            _view.edt_problem.setText("");
            _view.edt_topic.setText("");
            _view.edt_note.setText("");
            _view.edt_solution.setText("");
            _view.edt_created.setText("");
            _view.edt_update.setText("");
        }
        }
    }
    
    
     /**************************
     *  
     *  User defined functions
     *  
     ***************************/
    
    /*
    *  search ticket and fill textfield/combobox
    */
    public void searching (Integer ID) {
        try {
            String [] Array = Ticket.searchTicket(ID);
            _view.edt_ID.setText(ID.toString());
            _view.edt_topic.setText(Array[4]);
            _view.edt_problem.setText(Array[5]);
            _view.edt_note.setText(Array[6]);
            _view.edt_solution.setText(Array[7]);
            _view.edt_created.setText(Array[8]);
            _view.edt_update.setText(Array[9]);
            this.sol = Array[7];
            this.note = Array[6];
            
            for (int i=0; i< _view.cmb_cID.getItemCount();i++) {
                if (Array[0].equals(_view.cmb_cID.getItemAt(i).toString())) {
                    _view.cmb_cID.setSelectedIndex(i);
                 }
            }
            
            if (Array[1]!=null) {
               String username = ComboBox.getEUsername(Integer.parseInt(Array[1]));
                for (int i=0; i< _view.cmb_eID.getItemCount();i++) {
                    if (username.equals(_view.cmb_eID.getItemAt(i).toString())) {
                        _view.cmb_eID.setSelectedIndex(i);
                    }
                }
            }
            
            try {
               this.pname = ComboBox.getProductName(ComboBox.getPID(ID,null));  
            } catch (SQLException evt) {
            } catch (NullPointerException evt) {}
            if (this.pname != null && !"".equals(this.pname)) {
               for (int i=0; i< _view.cmb_product.getItemCount();i++) {
                   if (this.pname.equals(_view.cmb_product.getItemAt(i).toString())) {
                       _view.cmb_product.setSelectedIndex(i);
                   }
               }
            }
                
           _view.cmb_category.setSelectedIndex(Integer.parseInt(Array[2])-1);
           _view.cmb_status.setSelectedIndex(Integer.parseInt(Array[3])-1);
        } catch (NullPointerException E){
            Error_Frame.Error("ID not found");
        } catch (NumberFormatException E) {
            Error_Frame.Error(E.toString());
        } catch (Exception E) {
            Error_Frame.Error(E.toString());
        }
    }
    
    
      /*****************************************************************
     *  Count ticket status like open,in process and closed in fulltickettable
     *  and set counts in fullticket control buttons
     ********************************************************************/
    public void getStatusCount () {
        Integer openCount=0, processCount=0, closedCount=0;
        Object [] A2 = Counter.getCount();
               for (int i=0;i<A2.length;i++) {
                if ("Open".equals(A2[i])) {
                    openCount++;
                } else if ("In process".equals(A2[i])) {
                    processCount++;
                } else if ("Closed".equals(A2[i])) {
                    closedCount++;
                }
               }
        main.btn_setopen.setText("Open [" + openCount+"]");
        main.btn_setprocess.setText("In Process [" + processCount +"]");
        main.btn_setclosed.setText("Closed [" + closedCount +"]");
    }   
}