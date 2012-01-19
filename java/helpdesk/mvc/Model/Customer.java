package Helpdesk.java.helpdesk.mvc.Model;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import Helpdesk.java.helpdesk.lib.db.Database;
import java.util.ArrayList;

public class Customer extends MainModel{
    public String c_firstname;
    public String c_lastname;
    public String c_username;
    public String c_tel;
    public String c_email;
    public String c_pw;
    public Integer CID;
    
public Customer(Integer CID,String firstname, String lastname, String username,String pw ,String tel, String email) {
        this.CID                 = CID;
        this.c_firstname         = firstname;
        this.c_lastname          = lastname;
        this.c_username          = username;
        this.c_tel               = tel;
        this.c_email    	 = email;
        this.c_pw                = pw;
    }
    
    /*************************************** 
     * Generic
     * load table customer from the database, 
     * save it into a arraylist and return it
     ***************************************/
       public static ArrayList<Customer> showAll() {
            ArrayList<Customer> customers = new ArrayList<Customer>();
            Database db = dbconnect();
            try {
		db.prepare("SELECT * FROM customer WHERE bDeleted = 0 ORDER BY CID");
		ResultSet rs = db.executeQuery();   
		while(rs.next()) {
                   customers.add(CustomerObject(rs));
		}
	           db.close();      

            } catch (SQLException e) {
                Error_Frame.Error(e.toString());
            } 
                return customers;
	}
       
      /*************************
        *  Create new customer
        *  Placeholder "?" - Using db.bind_param
        *  to prevent SQL injection
        ************************/
        public void newCustomer() {
		Database db = dbconnect();
		try {
		String query = "INSERT INTO customer (firstName, lastName, username, password, telephone, email) VALUES "
                + "(?,?,?,?,?,?)";
			
			db.prepare(query);
                        db.bind_param(1, this.c_firstname);
                        db.bind_param(2, this.c_lastname);
                        db.bind_param(3, this.c_username);
                        db.bind_param(4, this.c_pw);
                        db.bind_param(5, this.c_tel);
                        db.bind_param(6, this.c_email);
			db.executeUpdate();
			db.close();
			} catch(SQLException e){	
			   Error_Frame.Error(e.toString());
			}  
        }
        
	/*******************
         * update customer
         *********************/
        public void updateCustomer(Integer ID, boolean equal) {
		Database db = dbconnect();
		try {
                    StringBuilder query = new StringBuilder ();
                     
                     query.append("UPDATE customer SET firstname = ?, "
                                    + "lastname = ?, username = ?");
                     if (!equal) {
                          query.append(",password = hash_pw(?),");   
                     } else {
                          query.append(",password = ?,");
                     }
                     query.append("telephone = ?, email = ? WHERE CID = ? AND bDeleted = 0");
			db.prepare(query.toString());
                        db.bind_param(1, this.c_firstname);
                        db.bind_param(2, this.c_lastname);
                        db.bind_param(3, this.c_username);
                        db.bind_param(4, this.c_pw);
                        db.bind_param(5, this.c_tel);
                        db.bind_param(6, this.c_email);
                        db.bind_param(7, ID.toString());
			db.executeUpdate();
			db.close();
			
			} catch(SQLException e){
				Error_Frame.Error(e.toString()); 
			}  
        }
	
        /*********************
         *  delete customer
         *********************/
        public static void deleteCustomer(Integer ID) {
		Database db = dbconnect();
		try {
                     String query = "UPDATE customer SET bDeleted = 1 "
                      + "WHERE CID = ?";

		db.prepare(query);
                db.bind_param(1, ID.toString());
		db.executeUpdate();
		db.close();
			
		} catch(SQLException e){
		   Error_Frame.Error(e.toString());
		}  
          }
        
        /*****************************************
         * search Customer ID, save the information 
         * in a array and return it
         ******************************************/
        public static String[] searchCustomer(Integer ID) {
            Database db = dbconnect();
            String [] Array = null;
            try {
		String query = ("SELECT * FROM customer WHERE CID = ? AND bDeleted = 0");
                db.prepare(query);
                db.bind_param(1, ID.toString());
		ResultSet rs = db.executeQuery();    
                while(rs.next()) {
                   Array = new String[]{rs.getString(rs.getMetaData().getColumnName(2)), 
                                        rs.getString(rs.getMetaData().getColumnName(3)),
                                        rs.getString(rs.getMetaData().getColumnName(4)),
                                        rs.getString(rs.getMetaData().getColumnName(5)),
                                        rs.getString(rs.getMetaData().getColumnName(6)),
                                        rs.getString(rs.getMetaData().getColumnName(7))};
		}
	        db.close();      

            } catch (SQLException e) {
                Error_Frame.Error(e.toString());
            } 
            return Array;
        }
        
        /*******************************
         * set resultset to a Customer object
         *******************************/
        private static Customer CustomerObject(ResultSet rs) {
            Customer newCustomer = null;
            try {
            newCustomer = new Customer(rs.getInt(rs.getMetaData().getColumnName(1)),
                                       rs.getString(rs.getMetaData().getColumnName(2)), 
                                       rs.getString(rs.getMetaData().getColumnName(3)),
                                       rs.getString(rs.getMetaData().getColumnName(4)),
                                       rs.getString(rs.getMetaData().getColumnName(5)),
                                       rs.getString(rs.getMetaData().getColumnName(6)),
                                       rs.getString(rs.getMetaData().getColumnName(7)));    
            } catch (SQLException e) {
                    Error_Frame.Error(e.toString());
            }
        return newCustomer;
        }
        
        /********************************
         *  save current customer into a 
         *  object for the JTable
         *********************************/
        public Object[] Array() {
          Object[] Array = {this.CID, this.c_firstname, this.c_lastname, 
                    this.c_username, this.c_email, this.c_tel,this.c_pw };
        return Array;
        }
        
    }
