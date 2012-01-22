package Helpdesk.java.helpdesk.mvc.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import Helpdesk.java.helpdesk.lib.db.Database;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.util.ArrayList;

public class Comp extends MainModel{
        /****************
         *  Get EID 
         ****************/
        public static Integer getEID (String txt) {
            Database db = dbconnect();
            String query;
            Integer _int = null;
	try {
                query = ("SELECT EID FROM employee WHERE username = ? AND bDeleted = 0");
                db.prepare(query);
                db.bind_param(1, txt);
		ResultSet rs = db.executeQuery(); 
                while(rs.next()) {
                    _int = rs.getInt(1);
                }
	        db.close();      
                
        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        } 
        return _int;
    }
    
        
        /****************
         *  Get Username 
         ****************/
        public static String getEUsername (Integer _int) {
            Database db = dbconnect();
            String query;
            String txt = null;
	try {

                query = ("SELECT username FROM employee WHERE bDeleted = 0 AND EID = ?");
                db.prepare(query);
                db.bind_param(1, _int.toString());
		ResultSet rs = db.executeQuery(); 
                while(rs.next()) {
                    txt = (rs.getString(1));
                }
	        db.close();      
                
        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        } 
        return txt;
    }
        
         /*************************
         *  Get ticket product name
         **************************/
        public static ArrayList<String> searchTicketProduct(Integer ID) {
            Database db = dbconnect();
            ArrayList<String> Array = new ArrayList<String>();
            try {
		String query = ("SELECT name FROM ticket_products WHERE TID = ?");
                db.prepare(query);
                db.bind_param(1, ID.toString());
		ResultSet rs = db.executeQuery();    
                while(rs.next()) {
                   Array.add(rs.getString(rs.getMetaData().getColumnName(1)));
		}
	        db.close();      

            } catch (SQLException e) {
                Error_Frame.Error(e.toString());
            } 
            return Array;
        }
} 
    