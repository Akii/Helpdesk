package Helpdesk.java.helpdesk.mvc.Model;

import Helpdesk.java.helpdesk.lib.db.Database;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

    public class Status extends MainModel{
        private Integer ID;
        private String descr;
    
    public Status(Integer ID, String descr) {
        this.ID         	= ID;
        this.descr         	= descr;
    }
    
    /*************************************** 
     * Generic
     * load table status from the database, 
     * save it into a arraylist and return it
     ***************************************/
    public static ArrayList<Status> showAll() {
        ArrayList<Status> status = new ArrayList<Status>();
        Database db = dbconnect();
            try {	
		db.prepare("SELECT * FROM ticket_status ORDER BY StatusID");
		ResultSet rs = db.executeQuery();
		while(rs.next()) {
			status.add(StatusObject(rs));
		}
                db.close();
        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        } 
         return status;
	}
    
     private static Status StatusObject(ResultSet rs) {
            Status newStatus = null;
        try {
            newStatus = new Status(rs.getInt(rs.getMetaData().getColumnName(1)),
                                   rs.getString(rs.getMetaData().getColumnName(2)));    
        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        }
        return newStatus;
    }
     
     public Integer getID () {
         return this.ID;
     }
     
     public String getDescr () {
         return this.descr;
     }
}
