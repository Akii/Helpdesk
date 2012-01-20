package Helpdesk.java.helpdesk.mvc.Model;

import Helpdesk.java.helpdesk.lib.db.Database;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

    public class Category extends MainModel{
        private Integer ID;
        private String descr;
    
    public Category(Integer ID, String descr) {
        this.ID         	= ID;
        this.descr         	= descr;
    }
    
    /*************************************** 
     * Generic
     * load table status from the database, 
     * save it into a arraylist and return it
     ***************************************/
    public static ArrayList<Category> showAll() {
        ArrayList<Category> category = new ArrayList<Category>();
        Database db = dbconnect();
            try {	
		db.prepare("SELECT * FROM problem_category ORDER BY CategoryID");
		ResultSet rs = db.executeQuery();
		while(rs.next()) {
			category.add(CategoryObject(rs));
		}

        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        } 
         return category;
	}
    
     private static Category CategoryObject(ResultSet rs) {
            Category newCategory = null;
        try {
            newCategory = new Category(rs.getInt(rs.getMetaData().getColumnName(1)),
                                     rs.getString(rs.getMetaData().getColumnName(2)));    
        } catch (SQLException e) {
            Error_Frame.Error(e.toString());
        }
        return newCategory;
    }
     
     public Integer getID () {
         return this.ID;
     }
     
     public String getDescr () {
         return this.descr;
     }
}
