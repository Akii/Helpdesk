package mvc.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import lib.refreshTable;
import mvc.Model.Product;
import mvc.Model.ProductTable;
import mvc.View.Product_Frame;
import mvc.View.Error_Frame;

public class PController implements Runnable{
    private Integer ID;
    private ProductTable p_model;
    private Product_Frame _view;
    
    public PController (Integer ID, ProductTable p_model, Product_Frame _view) {
          this.ID = ID;  
          this.p_model = p_model;
          this._view = _view;
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
        this._view.setbtn_searchListener(new btn_searchListener());
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
    
    class btn_searchListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {  
            //search ID        
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
        //send error message if one of these textfields are empty
            try {
                if ("".equals(_view.edt_name.getText()) || "".equals(_view.edt_description.getText())) {
                    Error_Frame.Error("Please fill out: name and description ");
                } else {
                    //check checkbox then create or update product
                    if (_view.chb_new.getSelectedObjects() == null) {
                        ID = Integer.parseInt (_view.edt_ID.getText()); 
                        Product updateProduct = new Product (ID,_view.edt_name.getText(),_view.edt_description.getText());
                        updateProduct.updateProduct(ID);
                    } else {
                        Product newProduct = new Product (null,_view.edt_name.getText(),_view.edt_description.getText());
                        newProduct.newProduct();
                    }
                    //after update or create, refresh table 
                    refreshTable A1 = new refreshTable(null, null, null, null, p_model);
                    A1.start();
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
      *     Checkbox - ItemListener
      * 
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
    

     /**************************
     *  
     *  User defined functions
     *  
     ***************************/
    
     /*
    *  search ID and fill textfield with data
    */
    public void psearch (Integer ID) {
            _view.edt_ID.setText(ID.toString());
            String [] Array = Product.searchProduct(ID);
            _view.edt_name.setText(Array[0]);
            _view.edt_description.setText(Array[1]);
    }
}

