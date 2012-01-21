package Helpdesk.java.helpdesk.mvc.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import Helpdesk.java.helpdesk.mvc.Model.Comp;
import Helpdesk.java.helpdesk.lib.refreshTable;
import Helpdesk.java.helpdesk.mvc.Model.CategoryModel;
import Helpdesk.java.helpdesk.mvc.Model.Counter;
import Helpdesk.java.helpdesk.mvc.Model.FullticketTable;
import Helpdesk.java.helpdesk.mvc.Model.HistoryTable;
import Helpdesk.java.helpdesk.mvc.Model.Product;
import Helpdesk.java.helpdesk.mvc.Model.ProductInv;
import Helpdesk.java.helpdesk.mvc.Model.ProductTable;
import Helpdesk.java.helpdesk.mvc.Model.StatusModel;
import Helpdesk.java.helpdesk.mvc.Model.Ticket;
import Helpdesk.java.helpdesk.mvc.View.Ticket_Frame;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import Helpdesk.java.helpdesk.mvc.View.Main_Frame;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;

public class TController implements Runnable{
    private Integer ID,noEm,runtime=0;
    private FullticketTable f_model;
    private HistoryTable h_model;
    private ProductTable p_model;
    private Main_Frame main;
    private Ticket_Frame _view;
    private String sol="",note="";
    private StatusModel s_model;
    private CategoryModel ca_model;
    private DefaultListModel<String> model;

    public TController(Integer ID, FullticketTable f_model,HistoryTable h_model,ProductTable p_model,
                       Main_Frame main, Ticket_Frame frame) {
        this.ID = ID;  
        this.f_model = f_model;
        this.h_model = h_model;
        this.p_model = p_model;
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
        ListSelection();
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
        ArrayList <String> list = Product.showName();
        model = new DefaultListModel();
        for (int i=0; i <= list.size()-1; i++) {
            model.addElement((String)list.get(i));
        }
        _view.ls_products.setModel(model);
    }
    
    
      /*************************************
      * Multiple listselection (without pressing ctrl)
      **************************************/
    public void ListSelection () {
        _view.ls_products.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(_view.ls_products.isSelectedIndex(index0)) {
                    _view.ls_products.removeSelectionInterval(index0, index1);
                } else {
                    _view.ls_products.addSelectionInterval(index0, index1);
                }
            } 
         });
    }
    
    
      /*************************************
      * ButtonListener
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
       if (runtime==0 || runtime==1) runtime++;
           try {
            String Str = _view.edt_ID.getText();
            if (!Str.isEmpty() && runtime >=1) { 
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
      *   Ticket edit save button
      *   TODO - reduce the count of if clause
      **************************************/
    class btn_saveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {  
            
            if (ID != null) {
                ProductInv inv_model = new ProductInv(ID,null);
                inv_model.deleteInvProduct();
            }
            
           try {
            //set timestamp for "create tickets" and "update tickets"
            Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            //send Error frame if one of these textfield are empty
            if (_view.edt_topic.getText().isEmpty() || (_view.edt_problem.getText().isEmpty())){
                Error_Frame.Error("Please fill out: Topic and Problem"); 
            } else {

                    if (!_view.edt_solution.getText().isEmpty() && !sol.equals(_view.edt_solution.getText())) {
                        sol = _view.edt_solution.getText();
                    }
                    if (!_view.edt_note.getText().isEmpty() && !note.equals(_view.edt_note.getText())) {
                        note = _view.edt_note.getText();
                    }
                    if (_view.cmb_eID.getSelectedItem() != "") {
                        noEm = Comp.getEID((String)_view.cmb_eID.getSelectedItem());
                    }
                    
                //check checkbox "new Ticket"
                if (_view.chb_new.getSelectedObjects() == null) {
                    ID = Integer.parseInt (_view.edt_ID.getText());  
                    
                    //Update Ticket block ##############################################################
                    Ticket updateTicket = new Ticket (ID,
                    (Integer)_view.cmb_cID.getSelectedItem(),noEm,
                    ca_model.getCategoryObjectID((String)_view.cmb_category.getSelectedItem()),
                    s_model.getStatusObjectID((String)_view.cmb_status.getSelectedItem()),
                    _view.edt_topic.getText(), _view.edt_problem.getText(),
                    note, sol, _view.edt_created.getText(), currentTimestamp.toString());
                    updateTicket.updateTicket(ID);
                    //##################################################################################
                    
                    //Set products  ##################################################################################
                    if (_view.ls_products.getSelectedIndices().length != 0) {
                        Integer a = -1;
                        Object Array [] = _view.ls_products.getSelectedValuesList().toArray();
                        Object _intarr[] = new Object [Array.length];
                        for (int i=0; i<= Array.length-1; i++) {
                             do {
                                 a++;
                             } while (!p_model.getValueAt(a, 1).equals(Array[i]));
                             _intarr [i] = p_model.getValueAt(a, 0);
                         }
                        ProductInv Productinv = new ProductInv (ID, _intarr);
                        Productinv.newInvProduct();
                    }
                    //################################################################################################
                    
                } else {
                    
                    //Create Ticket block #########################################################################
                    Ticket newTicket = new Ticket (null,(Integer)_view.cmb_cID.getSelectedItem(),noEm,
                    ca_model.getCategoryObjectID((String)_view.cmb_category.getSelectedItem()),
                    s_model.getStatusObjectID((String)_view.cmb_status.getSelectedItem()),
                    _view.edt_topic.getText(),_view.edt_problem.getText(),note,sol,currentTimestamp.toString(),
                    currentTimestamp.toString());
                    Integer _int = newTicket.newTicket();
                    //#############################################################################################
                    
                    //Set products  ##################################################################################
                    if (_view.ls_products.getSelectedIndices().length != 0) {
                        Integer a = -1;
                        Object Array [] = _view.ls_products.getSelectedValuesList().toArray();
                        Object _intarr[] = new Object [Array.length];
                        for (int i=0; i<= Array.length-1; i++) {
                             do {
                                 a++;
                             } while (!p_model.getValueAt(a, 1).equals(Array[i]));
                             _intarr [i] = p_model.getValueAt(a, 0);
                         }
                        ProductInv Productinv = new ProductInv (_int, _intarr);
                        Productinv.newInvProduct();
                    }
                    //################################################################################################
                    
                }
                //refresh jtable
                new refreshTable(null, null, f_model, h_model, null).start();
                //count ticket status for fullticket control buttons
                //timer to prevent connection link lost
                new Timer().schedule(new Count(), 600);
                _view.dispose();
            }
        } catch (NumberFormatException ev) {
              Error_Frame.Error("Please use only number for ID");
        } catch (Exception ev) {
              Error_Frame.Error(ev.toString()); 
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
                new Counter(main).start();
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
               String username = Comp.getEUsername(Integer.parseInt(Array[1]));
                for (int i=0; i< _view.cmb_eID.getItemCount();i++) {
                    if (username.equals(_view.cmb_eID.getItemAt(i).toString())) {
                        _view.cmb_eID.setSelectedIndex(i);
                    }
                }
            }
            
            Object [] product = Comp.searchTicketProduct(ID).toArray();
            System.out.println (Arrays.toString(product));
            int [] indices = new int [product.length];
            int a=-1;
            for (int i=0; i<product.length; i++) {
               do {
                   a++;
                } while (!_view.ls_products.getModel().getElementAt(a).equals(product[i]));
                   indices [i] = a;  
            }
            _view.ls_products.setSelectedIndices(indices);
            
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
}