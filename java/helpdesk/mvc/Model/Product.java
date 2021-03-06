package Helpdesk.java.helpdesk.mvc.Model;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Helpdesk.java.helpdesk.lib.db.Database;

public class Product extends MainModel{
    private Integer ID;
    private String name;
    private String description;
    
    public Product(Integer ID, String p_name, String p_descrription) {
        this.ID         	= ID;
        this.name         	= p_name;
        this.description        = p_descrription;
    }
    
    /*************************************** 
     * Generic
     * load table product from the database, 
     * save it into a arraylist and return it
     ***************************************/
    public static ArrayList<Product> showAll() {
        ArrayList<Product> products = new ArrayList<Product>();
        Database db = dbconnect();
            try {	
                db.prepare("SELECT * FROM product ORDER BY PID");
		ResultSet rs = db.executeQuery();
		while(rs.next()) {
			products.add(ProductObject(rs));
		}
                db.close();
        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        } 
         return products;
	}
    
      /*************************
        * Create new product
        * Placeholder "?" - Using db.bind_param
        * to prevent SQL injection
        ************************/
	public void newProduct() {
		try {
			Database db = dbconnect();
			String query = "INSERT INTO product (name, description) VALUES "
                                     + "(?,?)";
			db.prepare(query);
                        db.bind_param(1, this.name);
                        db.bind_param(2, this.description);
			db.executeUpdate();
			db.close();
			} catch(SQLException e){	
				Error_Frame.Error(e.toString());
			} 
	}
	
	/*******************
         * update product
         *********************/
	public void updateProduct() {
		try {
			Database db = dbconnect();
                         String query = "UPDATE product SET name = ?, "
                                      + "description = ? "
                                      + "WHERE PID = ?";
			db.prepare(query);
                        db.bind_param(1, this.name);
                        db.bind_param(2, this.description);
                        db.bind_param(3, this.ID.toString());
			db.executeUpdate();
			db.close();
			} catch(SQLException e){
				Error_Frame.Error(e.toString()); 
			}  
	}
	
        /*********************
         *  delete product
         *********************/
	public static void deleteProduct(Integer ID) {
		try {
			Database db = dbconnect();
                        String query = "DELETE FROM product "
                        + "WHERE PID = ?";
			db.prepare(query);
                        db.bind_param(1, ID.toString());
			db.executeUpdate();
			db.close();
			} catch(SQLException e){
				Error_Frame.Error(e.toString()); 
			}  
	}
        
        /*****************************************
         * search Product ID, save the information 
         * in a array and return it
         ******************************************/
        public static String[] searchProduct(Integer ID) {
            Database db = dbconnect();
            String [] Array = null;
            try {
		String query = ("SELECT * FROM product WHERE PID = ?");
                db.prepare(query);
                db.bind_param(1, ID.toString());
		ResultSet rs = db.executeQuery();    
                while(rs.next()) {
                   Array = new String[]{rs.getString(rs.getMetaData().getColumnName(2)), 
                                        rs.getString(rs.getMetaData().getColumnName(3))};
		}
	        db.close();      

            } catch (SQLException e) {
                Error_Frame.Error(e.toString());
            } 
            return Array;
        }
          
        
    public static ArrayList<String> showName() {
        ArrayList<String> products = new ArrayList<String>();
        Database db = dbconnect();
            try {	
                db.prepare("SELECT name FROM product ORDER BY PID");
		ResultSet rs = db.executeQuery();
		while(rs.next()) {
			products.add(rs.getString(rs.getMetaData().getColumnName(1)));
		}
                db.close();
        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        } 
         return products;
    }
        
        
        
        /*******************************
         * set resultset to a Product object
         *******************************/
      private static Product ProductObject(ResultSet rs) {
            Product newProduct = null;
        try {
            newProduct = new Product(rs.getInt(rs.getMetaData().getColumnName(1)),
                                     rs.getString(rs.getMetaData().getColumnName(2)), 
                                     rs.getString(rs.getMetaData().getColumnName(3)));    
        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        }
        return newProduct;
    }
      
        /********************************
         *  save current product into a 
         *  object for the JTable
         *********************************/
    public Object[] Array() {
       Object[] Array = {this.ID, this.name, this.description};
        return Array;
    }
}
