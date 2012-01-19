package Helpdesk.java.helpdesk.lib;

import java.sql.ResultSet;
import java.sql.SQLException;
import Helpdesk.java.helpdesk.lib.db.Database;
import Helpdesk.java.helpdesk.mvc.Model.MainModel;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;

public class ComboBox {
        /****************
         *  Get EID 
         ****************/
        public static Integer getEID (String txt) {
            Database db = MainModel.dbconnect();
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
            Database db = MainModel.dbconnect();
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
        
         /****************
         *  Get Productname 
         ****************/
        public static String getProductName (Integer _int) throws SQLException{
            Database db = MainModel.dbconnect();
            String query;
            String txt = null;

                query = ("SELECT name FROM product WHERE PID = ?");
                db.prepare(query);
                db.bind_param(1, _int.toString());
		ResultSet rs = db.executeQuery(); 
                while(rs.next()) {
                    txt = (rs.getString(1));
                }
	        db.close();      

        return txt;
    }
        
         /****************
         *  Get PID 
         ****************/
        public static Integer getPID (Integer _int, String _str) throws SQLException{
            Database db = MainModel.dbconnect();
            String query;
            Integer _pint = null;

                if (_int == null) {
                    query = ("SELECT PID FROM product WHERE name = ?");
                    db.prepare(query);
                    db.bind_param(1, _str);
                } else {
                    query = ("SELECT product_PID FROM products_involved WHERE ticket_TID = ?");
                    db.prepare(query);
                    db.bind_param(1, _int.toString());
                }

		ResultSet rs = db.executeQuery(); 
                while(rs.next()) {
                    _pint = (rs.getInt(1));
                }
	        db.close();      

        return _pint;
    }
} 
    