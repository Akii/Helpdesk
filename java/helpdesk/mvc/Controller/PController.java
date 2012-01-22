package Helpdesk.java.helpdesk.mvc.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import Helpdesk.java.helpdesk.lib.refreshTable;
import Helpdesk.java.helpdesk.mvc.Model.Product;
import Helpdesk.java.helpdesk.mvc.Model.ProductTable;
import Helpdesk.java.helpdesk.mvc.View.Product_Frame;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PController implements Runnable{
    private Integer ID;;
    private Product_Frame _view;
    
    public PController (Integer ID) {
          this.ID = ID;  
          this._view = new Product_Frame();
          addListener();
    }
    
    @Override
    public void run() {
        if (this.ID != null) {
            psearch (this.ID);
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
                    if (!Str.isEmpty()) {
                        psearch(Integer.parseInt (Str));  
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
    
    class btn_searchListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {  
            //Search ID        
            try {
                psearch(Integer.parseInt(_view.edt_ID.getText()));
            } catch (NullPointerException E){
                Error_Frame.Error("ID not found");
            } catch (NumberFormatException E) {
                Error_Frame.Error("Please use only number for ID");
            }
        }
    }
        
     class btn_saveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {  
        //Send error message if one of these textfields are empty
            try {
                if (_view.edt_name.getText().isEmpty() || _view.edt_description.getText().isEmpty()) {
                    Error_Frame.Error("Please fill out: name and description ");
                } else {
                    //Check checkbox then create or update product
                    if (_view.chb_new.getSelectedObjects() == null) {
                        ID = Integer.parseInt (_view.edt_ID.getText()); 
                        Product updateProduct = new Product (ID,_view.edt_name.getText(),_view.edt_description.getText());
                        updateProduct.updateProduct(ID);
                    } else {
                        Product newProduct = new Product (null,_view.edt_name.getText(),_view.edt_description.getText());
                        newProduct.newProduct();
                    }
                    //After update or create, refresh table 
                    new refreshTable(null, null, null, null, ProductTable.getInstance()).start();
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
      * Checkbox - ItemListener
      * only the db can create id number so we dont need 
      * ID textfield (db ID -> auto increment)
      **************************************/
     
    class chb_newListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent e) {
        //check checkbox and set visibility
            if (e.getStateChange() == ItemEvent.DESELECTED){
                _view.edt_ID.setVisible(true);
            } else {
                _view.edt_ID.setVisible(false);
                _view.edt_ID.setText("");
                _view.edt_name.setText("");
                _view.edt_description.setText("");
            }
        }
    }
    
     /*************************************
      * Search the ID from the db 
      * and fill the textfield with data
      **************************************/
    public void psearch (Integer ID) {
            _view.edt_ID.setText(ID.toString());
            String [] Array = Product.searchProduct(ID);
            _view.edt_name.setText(Array[0]);
            _view.edt_description.setText(Array[1]);
    }
}

