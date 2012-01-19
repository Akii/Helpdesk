package Helpdesk.java.helpdesk.mvc.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import Helpdesk.java.helpdesk.lib.refreshTable;
import Helpdesk.java.helpdesk.mvc.Model.Customer;
import Helpdesk.java.helpdesk.mvc.Model.CustomerTable;
import Helpdesk.java.helpdesk.mvc.Model.Employee;
import Helpdesk.java.helpdesk.mvc.Model.EmployeeTable;
import Helpdesk.java.helpdesk.mvc.View.CE_Frame;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;

public class CEController implements Runnable{
    private Integer ID;
    private String EqualPW,choose;
    private CustomerTable c_model;
    private EmployeeTable e_model;
    private CE_Frame _view;
    
    public CEController (Integer ID,String choose, CE_Frame _view, CustomerTable c_model, EmployeeTable e_model) {
          this.ID = ID;  
          this.choose = choose;
          this.c_model = c_model;
          this.e_model = e_model;
          this._view = _view;
          addListener();
    }
    
    @Override
    public void run() {
        if (this.ID != null) {
            csearch (this.ID);
        }
        this._view.init();
    }
    
    private void addListener() {
        this._view.setbtn_cancelListener(new btn_cancelListener());
        this._view.setbtn_csearchListener(new btn_csearchListener());
        this._view.setbtn_saveListener(new btn_saveListener());
        this._view.setchb_newListener(new chb_newListener());
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
    
    class btn_csearchListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {  
            try {
                csearch(Integer.parseInt(_view.edt_ID.getText()));         
            } catch (NumberFormatException evt) {
                Error_Frame.Error("Please use only number for ID");
        }
        }
    }
        
     class btn_saveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {  
        boolean equal = false;
        ID = null;
        //send error message if one of these textfields are empty
        try {
            if ("".equals(_view.edt_cfirstname.getText()) || "".equals(_view.edt_clastname.getText())
            || "".equals(_view.edt_cusername.getText()) || "".equals(_view.edt_cpassword.getText())) {
                Error_Frame.Error("Please fill out: Firstname, Lastname, Username, Email and Password"); 
            } else {
                //don't hash password string if it is already hashed
                if (EqualPW != null) {
                    if (EqualPW.equals(_view.edt_cpassword.getText())) {
                        equal = true;
                    }
                }
                //check Checkbox then create or update customer/employee
                if (_view.chb_new.getSelectedObjects() == null) {
                    ID = Integer.parseInt (_view.edt_ID.getText());  
                    if ("Customer".equals(choose)) {
                        Customer updateCustomer = new Customer (ID,         
                        _view.edt_cfirstname.getText(),
                        _view.edt_clastname.getText(),
                        _view.edt_cusername.getText(),
                        _view.edt_cpassword.getText(),
                        _view.edt_ctelephone.getText(),
                        _view.edt_cemail.getText());
                        updateCustomer.updateCustomer (ID, equal);
                    } else { 
                        Employee updateEmployee = new Employee (ID,         
                        _view.edt_cfirstname.getText(),
                        _view.edt_clastname.getText(),
                        _view.edt_cusername.getText(),
                        Integer.parseInt (_view.edt_cemail.getText()),
                        _view.edt_cpassword.getText());
                        updateEmployee.updateEmployee (ID, equal);
                    }
                } else {
                    if ("Customer".equals(choose)) {
                        Customer newCustomer = new Customer (ID,         
                        _view.edt_cfirstname.getText(),
                        _view.edt_clastname.getText(),
                        _view.edt_cusername.getText(),
                        _view.edt_cpassword.getText(),
                        _view.edt_ctelephone.getText(),
                        _view.edt_cemail.getText());
                        newCustomer.newCustomer();
                    } else { 
                        Employee newEmployee = new Employee (ID,         
                        _view.edt_cfirstname.getText(),
                        _view.edt_clastname.getText(),
                        _view.edt_cusername.getText(),
                        Integer.parseInt (_view.edt_cemail.getText()),
                        _view.edt_cpassword.getText());
                        newEmployee.newEmployee();
                    }
                }
                //after update or create, refresh table 
                refreshTable A1 = new refreshTable(c_model, e_model, null, null, null);
                A1.start();
                _view.dispose();
            }
         } catch (NumberFormatException evt) {
                Error_Frame.Error("Please use only number for ID / Troubleshooter");
         } catch (NullPointerException evt) {
                Error_Frame.Error("Wrong ID\n" + evt.toString()); 
         }
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
                _view.edt_cfirstname.setText("");
                _view.edt_clastname.setText("");
                _view.edt_cusername.setText("");
                _view.edt_cemail.setText("");
                _view.edt_ctelephone.setText("");
                _view.edt_cpassword.setText("");
            }
        }
    }
    

     /**************************
     *  
     *  User defined functions
     *  
     ***************************/
    
    /*
    *  search ID and fill textfield with data
    */   
    public void csearch (Integer ID) {
        try {
            _view.edt_ID.setText(ID.toString());
            if ("Customer".equals(choose)) {
                String [] Array = Customer.searchCustomer(ID);
                _view.edt_cfirstname.setText(Array[0]);
                _view.edt_clastname.setText(Array[1]);
                _view.edt_cusername.setText(Array[2]);
                _view.edt_cpassword.setText(Array[3]);
                _view.edt_ctelephone.setText(Array[4]);
                _view.edt_cemail.setText(Array[5]);
                this.EqualPW = Array[3];
            } else {
                String [] Array = Employee.searchEmployee(ID);
                _view.edt_cfirstname.setText(Array[0]);
                _view.edt_clastname.setText(Array[1]);
                _view.edt_cusername.setText(Array[2]);
                _view.edt_cpassword.setText(Array[3]);
                _view.edt_cemail.setText(Array[4]);
                this.EqualPW = Array[3];
            } 
        } catch (NullPointerException E){
            Error_Frame.Error("ID not found");
        } catch (NumberFormatException E) {
            Error_Frame.Error("Please use only number for ID");
        }
    }
}
