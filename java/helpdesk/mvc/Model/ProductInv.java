
package Helpdesk.java.helpdesk.mvc.Model;

import Helpdesk.java.helpdesk.lib.db.Database;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.sql.SQLException;


public class ProductInv extends MainModel {
        public Integer TID,PID;
    
        
     public ProductInv(Integer TID, Integer PID) {
        this.TID         	= TID;
        this.PID         	= PID;
    }
        
        /*************************
        * Create new involved product
        ************************/
	public void newInvProduct(){
            try {
                Database db = dbconnect();
			String query = "INSERT INTO products_involved (ticket_TID, product_PID) VALUES "
                                     + "(?,?)";
			db.prepare(query);
                        db.bind_param(1, this.TID.toString());
                        db.bind_param(2, this.PID.toString());
			db.executeUpdate();
            } catch (SQLException e) {
                Error_Frame.Error(e.getMessage());
            }
			
	}
	
	/*******************
         * update involved product
         *********************/
	public void updateInvProduct() throws SQLException {

			Database db = dbconnect();
                         String query = "UPDATE products_involved SET "
                                      + "product_PID = ? "
                                      + "WHERE ticket_TID = ?";
			db.prepare(query);
                        db.bind_param(1, this.PID.toString());
                        db.bind_param(2, this.TID.toString());
			db.executeUpdate();
	}
}
