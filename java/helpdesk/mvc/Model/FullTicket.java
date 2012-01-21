package Helpdesk.java.helpdesk.mvc.Model;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Helpdesk.java.helpdesk.lib.db.Database;

public class FullTicket extends MainModel {
	
    public Integer TID;
    public Integer CID;
    public Integer EID;
    public String Problem_category;
    public String Ticket_status;
    public String E_fn;
    public String E_ln;
    public String C_fn;
    public String C_ln;
    public String tel;
    public String email;
    public String Topic;
    public String Problem;
    public String Note;
    public String Solution;
    public String created_on;
    public String last_update;
    
    public FullTicket(Integer TID, String Problem_category, String Ticket_status,
                      Integer EID, String E_fn, String E_ln, Integer CID, String C_fn, String C_ln,
                      String tel, String email, String Topic, String Problem,String Note, 
                      String Solution, String created_on, String last_update) {
        
        this.TID    =  TID;
        this.Problem_category = Problem_category;
        this.Ticket_status = Ticket_status;
        this.CID = CID;
        this.EID = EID;
        this.E_fn = E_fn;
        this.E_ln = E_ln;
        this.C_fn = C_fn;
        this.C_ln = C_ln;
        this.tel = tel;
        this.email = email;
        this.Topic = Topic;
        this.Problem = Problem;
        this.Note = Note;
        this.Solution = Solution;
        this.created_on = created_on;
        this.last_update = last_update;
    }
	
    /*************************************** 
     * Generic
     * load view Fullticket from the database, 
     * save it into a arraylist and return it
     ***************************************/
        public static ArrayList<FullTicket> showAll(String status) {
            ArrayList<FullTicket> fulltickets = new ArrayList<>();
            StringBuilder query = new StringBuilder();
            Database db = dbconnect();
	try {
                query.append ("SELECT * FROM full_ticket ");
                if ("Open".equals(status)) {
                query.append("WHERE ticket_status = 'Open'");
                db.prepare(query.toString());
                } else if ("In process".equals(status)) {
                query.append("WHERE ticket_status = 'In process'");
                db.prepare(query.toString());
                } else if ("Closed".equals(status)) {
                query.append("WHERE ticket_status = 'Closed'");
                db.prepare(query.toString());
                } else {
                query.append("ORDER BY TID");
		db.prepare(query.toString());
                }
		ResultSet rs = db.executeQuery();         
		while(rs.next()) {
                   fulltickets.add(FullTicketObject(rs));
		}
	           db.close();      

        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        } 
                return fulltickets;
	}
        
        /*****************************************
         * search Fullticket ID, save the information 
         * in a array and return it
         *  Placeholder "?" - Using db.bind_param
         *  to prevent SQL injection
         ******************************************/
         public static String[] searchFullTicket(Integer ID) {
            Database db = dbconnect();
            String [] Array = null;
            try {
		String query = ("SELECT * FROM full_ticket WHERE TID = ?");
                db.prepare(query);
                db.bind_param(1, ID.toString());
		ResultSet rs = db.executeQuery();    
                while(rs.next()) {
                   Array = new String[]{rs.getString(rs.getMetaData().getColumnName(2)), 
                                        rs.getString(rs.getMetaData().getColumnName(3)),
                                        rs.getString(rs.getMetaData().getColumnName(4)),
                                        rs.getString(rs.getMetaData().getColumnName(5)),
                                        rs.getString(rs.getMetaData().getColumnName(6)),
                                        rs.getString(rs.getMetaData().getColumnName(7)),
                                        rs.getString(rs.getMetaData().getColumnName(8)),
                                        rs.getString(rs.getMetaData().getColumnName(9)),
                                        rs.getString(rs.getMetaData().getColumnName(10)),
                                        rs.getString(rs.getMetaData().getColumnName(11)),
                                        rs.getString(rs.getMetaData().getColumnName(12)),
                                        rs.getString(rs.getMetaData().getColumnName(13)),
                                        rs.getString(rs.getMetaData().getColumnName(14)),
                                        rs.getString(rs.getMetaData().getColumnName(15)),
                                        rs.getString(rs.getMetaData().getColumnName(16)),
                                        rs.getString(rs.getMetaData().getColumnName(17))}; 
		}
	        db.close();      

            } catch (SQLException e) {
                Error_Frame.Error(e.toString());
            } 
            return Array;
        }
        
        /*******************************
         * set resultset to a Fullticket object
         *******************************/
        private static FullTicket FullTicketObject(ResultSet rs) {
            FullTicket newfullTicket = null;
        try {
            newfullTicket = new FullTicket(rs.getInt(rs.getMetaData().getColumnName(1)),
                                        rs.getString(rs.getMetaData().getColumnName(2)), 
                                        rs.getString(rs.getMetaData().getColumnName(3)),
                                        rs.getInt(rs.getMetaData().getColumnName(4)),
                                        rs.getString(rs.getMetaData().getColumnName(5)),
                                        rs.getString(rs.getMetaData().getColumnName(6)),
                                        rs.getInt(rs.getMetaData().getColumnName(7)),
                                        rs.getString(rs.getMetaData().getColumnName(8)),
                                        rs.getString(rs.getMetaData().getColumnName(9)),
                                        rs.getString(rs.getMetaData().getColumnName(10)),
                                        rs.getString(rs.getMetaData().getColumnName(11)),
                                        rs.getString(rs.getMetaData().getColumnName(12)),
                                        rs.getString(rs.getMetaData().getColumnName(13)),
                                        rs.getString(rs.getMetaData().getColumnName(14)),
                                        rs.getString(rs.getMetaData().getColumnName(15)),
                                        rs.getString(rs.getMetaData().getColumnName(16)),
                                        rs.getString(rs.getMetaData().getColumnName(17))); 

        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        }
        return newfullTicket;
         }
        
        /********************************
         *  save current Fullticket into a 
         *  object for the JTable
         *********************************/
    public Object[] Array() {
       Object[] Array = {this.TID, this.Problem_category, this.Ticket_status,this.EID, this.E_fn,this.E_ln, this.CID,
                                 this.C_fn,this.C_ln,this.tel,this.email,this.Topic, this.Problem, this.Note, this.Solution, 
                                 this.created_on, this.last_update};
        return Array;
    }
}
