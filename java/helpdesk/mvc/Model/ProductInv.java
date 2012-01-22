
package Helpdesk.java.helpdesk.mvc.Model;

import Helpdesk.java.helpdesk.lib.db.Database;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.sql.SQLException;


public class ProductInv extends MainModel {
        private Integer  TID;
        private Object[] PID;
        
     public ProductInv(Integer TID, Object[] PID) {
        this.TID         	= TID;
        this.PID         	= PID;
    }
        
        /*************************
        * Create new involved product
        ************************/
	public void newInvProduct(){
            int arr[] = new int [PID.length];
            try {
                Database db = dbconnect();
                String query = "INSERT INTO products_involved (ticket_TID, product_PID) VALUES "
                                     + "(?,?)";
		db.prepare(query);
                for (int i=0; i<= arr.length-1;i++) {
                    db.bind_param(1, this.TID.toString());
                    db.bind_param(2, this.PID[i].toString());
                    db.addBatch();
                }
                    db.executeBatch();
                    db.close();
            } catch (SQLException e) {
                    Error_Frame.Error(e.getMessage());
            }
	}
	
        
         /*******************
         * delete involved product
         *********************/
	public void deleteInvProduct() {
            try {
			Database db = dbconnect();
                         String query = "DELETE FROM products_involved "
                                      + "WHERE ticket_TID = ?";
			db.prepare(query);
                        db.bind_param(1, this.TID.toString());
			db.executeUpdate();
                        db.close();
            } catch (SQLException e) {
                Error_Frame.Error(e.getMessage());
            }
	}    
}
