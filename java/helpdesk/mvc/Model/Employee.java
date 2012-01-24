package Helpdesk.java.helpdesk.mvc.Model;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Helpdesk.java.helpdesk.lib.db.Database;

public class Employee extends MainModel{
    public String e_firstname;
    public String e_lastname;
    public String e_username;
    public Integer e_trb;
    public String e_pw;
    public Integer EID;
    
    public Employee(Integer EID, String firstname, String lastname, String username, Integer trb, String pw) {
        this.EID                 = EID;
        this.e_firstname         = firstname;
        this.e_lastname          = lastname;
        this.e_username          = username;
        this.e_trb       	 = trb;
        this.e_pw  		 = pw;

    }
    
    /*************************************** 
     * Generic
     * load table employee from the database, 
     * save it into a arraylist and return it
     ***************************************/
    public static ArrayList<Employee> showAll() {
            ArrayList<Employee> employees = new ArrayList<Employee>();
            Database db = dbconnect();
	try {
		db.prepare("SELECT * FROM employee WHERE bDeleted = 0 ORDER BY EID");
		ResultSet rs = db.executeQuery();         
		while(rs.next()) {
                   employees.add(EmployeeObject(rs));
		}
	           db.close();      

        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        } 
                return employees;
	}
    
       /*************************
        *  Create new employee
        *  Placeholder "?" - Using db.bind_param
        *  to prevent SQL injection
        ************************/
	public void newEmployee() {
		Database db = dbconnect();
		try {
			String query = "INSERT INTO employee (firstName, lastName, "
                                        + "username, password, bIsTroubleshooter) "
                                        + "VALUES (?,?,?,?,?)";

			db.prepare(query);
                        db.bind_param(1, this.e_firstname);
                        db.bind_param(2, this.e_lastname);
                        db.bind_param(3, this.e_username);
                        db.bind_param(4, this.e_pw);
                        db.bind_param(5, this.e_trb.toString());
			db.executeUpdate();
			db.close();
			
			} catch(SQLException e){
				Error_Frame.Error(e.toString()); 
			}  
	}
	
	/*******************
         * update employee
         *********************/
	public void updateEmployee(boolean equal) {
		Database db = dbconnect();
		try {
                    StringBuilder query = new StringBuilder ();
                     query.append("UPDATE employee SET firstName = ?, lastName = ?, username = ?, ");  
                     if (!equal) {
                          query.append("password = hash_pw(?),");   
                     } else {
                          query.append("password = ?,");
                     }
                     query.append("bIsTroubleshooter = ? WHERE EID = ? AND bDeleted = 0");
                           
                           
			db.prepare(query.toString());
                        db.bind_param(1, this.e_firstname);
                        db.bind_param(2, this.e_lastname);
                        db.bind_param(3, this.e_username);
                        db.bind_param(4, this.e_pw);
                        db.bind_param(5, this.e_trb.toString());
			db.bind_param(6, this.EID.toString());
                        db.executeUpdate();
			db.close();
			
			} catch(SQLException e){
				Error_Frame.Error(e.toString()); 
			}  
	}
	
        /*********************
         *  delete employee
         *********************/
	public static void deleteEmployee(Integer ID) {
		Database db = dbconnect();
		try {
                    String query = "UPDATE employee SET bDeleted = 1 "
                                 + "WHERE EID = ?";
            
			db.prepare(query);
                        db.bind_param(1, ID.toString());
			db.executeUpdate();
			db.close();
			
			} catch(SQLException e){
				Error_Frame.Error(e.toString()); 
			}  
	}
        
        /*****************************************
         * search Employee ID, save the information 
         * in a array and return it
         ******************************************/
        public static String[] searchEmployee(Integer ID) {
            Database db = dbconnect();
            String [] Array = null;
	try {
		String query = ("SELECT * FROM employee WHERE EID = ? AND bDeleted = 0");
                db.prepare(query);
                db.bind_param(1, ID.toString());
		ResultSet rs = db.executeQuery();    
                while(rs.next()) {
                   Array = new String[]{rs.getString(rs.getMetaData().getColumnName(2)), 
                                        rs.getString(rs.getMetaData().getColumnName(3)),
                                        rs.getString(rs.getMetaData().getColumnName(4)),
                                        rs.getString(rs.getMetaData().getColumnName(5)),
                                        rs.getString(rs.getMetaData().getColumnName(6))};
		}
	        db.close();      

            } catch (SQLException e) {
                Error_Frame.Error(e.toString());
             } 
         return Array;
         }
        
        /*******************************
         * set resultset to a Employee object
         *******************************/
        private static Employee EmployeeObject(ResultSet rs) {
        Employee newEmployee = null;
        try {
            newEmployee = new Employee(rs.getInt(rs.getMetaData().getColumnName(1)),
                                       rs.getString(rs.getMetaData().getColumnName(2)), 
                                       rs.getString(rs.getMetaData().getColumnName(3)),
                                       rs.getString(rs.getMetaData().getColumnName(4)),
                                       rs.getInt(rs.getMetaData().getColumnName(6)),
                                       rs.getString(rs.getMetaData().getColumnName(5)));   
        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        }
        return newEmployee;
    }
        
        /********************************
         *  save current employee into a 
         *  object for the JTable
         *********************************/
    public Object[] Array() {
       Object[] Array = {this.EID, this.e_firstname, this.e_lastname, this.e_username, this.e_trb,this.e_pw };
        return Array;
    }
        
}
