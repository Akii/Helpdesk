package Helpdesk.java.helpdesk.mvc.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import Helpdesk.java.helpdesk.lib.refreshTable;
import Helpdesk.java.helpdesk.mvc.Model.Customer;
import Helpdesk.java.helpdesk.mvc.Model.Employee;
import Helpdesk.java.helpdesk.mvc.View.CE_Frame;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CEController implements Runnable{
    private Integer ID,runtime=0;
    private String EqualPW,choose;
    private CE_Frame _view;
    
    public CEController (Integer ID,String choose) {
          this.ID = ID;  
          this.choose = choose;
          this._view =  new CE_Frame(choose);
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
        this._view.setbtn_saveListener(new btn_saveListener());
        this._view.setchb_newListener(new chb_newListener());
        this._view.setFocusListener(new IDFocusListener());
    }
    
    
      /*************************************
      * FocusListener
      * if the focus of ID textfield lose, 
      * it will automatically search the ID 
      **************************************/
    private class IDFocusListener extends FocusAdapter {
    @Override
        public void focusLost(FocusEvent arg0) {
                try {
                    String Str = _view.edt_ID.getText();
                    if (!Str.isEmpty() && runtime >=0) { 
                        if (runtime==0) runtime++;
                        csearch(Integer.parseInt (Str));  
                }
            } catch (NullPointerException E){
                Error_Frame.Error("ID not found");
            } catch (NumberFormatException E) {
                Error_Frame.Error("Please use only number for ID");
            }
        }
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
    
        
     class btn_saveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {  
        boolean equal = false;
        ID = null;
        //send error message if one of these textfields are empty
        try {
            if (_view.edt_cfirstname.getText().isEmpty() || _view.edt_clastname.getText().isEmpty()
            || _view.edt_cusername.getText().isEmpty() || _view.edt_cpassword.getText().isEmpty()) {
                Error_Frame.Error("Please fill out: Firstname, Lastname, Username, Email and Password"); 
            } else {
                //check if the password were already hashed
                if (EqualPW != null) {
                    if (EqualPW.equals(_view.edt_cpassword.getText())) {
                        equal = true;
                    }
                }
                //check Checkbox if we want to create or update
                if (_view.chb_new.getSelectedObjects() == null) {
                    ID = Integer.parseInt (_view.edt_ID.getText());  
                    //check which one we have chosen
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
                //after update or create - refresh table and dispose frame
                new refreshTable("Customer", "Employee", "", "", "", null).start();
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
      * Checkbox - ItemListener
      * only the db can create id number so we dont need 
      * ID textfield (db ID -> auto increment)
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
    
    
     /*************************************
      * Search the ID from the db 
      * and fill the textfield with data
      **************************************/
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
            //set testposition to 0 (completly left)
            //usefull if strings are too long
            _view.edt_cfirstname.setCaretPosition(0);
            _view.edt_clastname.setCaretPosition(0);
            _view.edt_cusername.setCaretPosition(0);
            _view.edt_cpassword.setCaretPosition(0);
            _view.edt_cemail.setCaretPosition(0);
            
        } catch (NullPointerException E){
            Error_Frame.Error("ID not found");
        } catch (NumberFormatException E) {
            Error_Frame.Error("Please use only number for ID");
        }
    }
}
