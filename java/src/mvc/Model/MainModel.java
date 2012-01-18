package mvc.Model;

import lib.LoginData;
import mvc.View.Error_Frame;
import java.sql.ResultSet; 
import java.sql.SQLException;

import lib.IPagination;
import lib.Pagination;
import lib.db.Database;
import lib.db.MysqlDatabase;

public abstract class MainModel {

    protected static Database dbconnect() {
	   Database db = null;
	try {
		// working example of select with placeholders
		// using the more generic type of database here!
                String[] Array = LoginData.readSQL();
		db = MysqlDatabase.getInstance();
		db.connect(Array[0]+":"+Array[1], Array[2], Array[3], Array[4]);
		
	        /*
		db.prepare("SELECT * FROM customer WHERE CID=? OR CID=?");

		db.bind_param(1, "1");
		db.bind_param(2, "5");
		ResultSet rs = db.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getInt(1) + ": " + rs.getString("firstName")+" "+rs.getString("lastName"));
		}
		db.bind_param(1, "7");
		db.bind_param(2, "8");
		rs = db.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getInt(1) + ": " + rs.getString("firstName")+" "+rs.getString("lastName"));
		}
		
		/* INSERT/DELETE
		// numrows is evil on jdbc, keep that in mind
		// edit: not so evil anymore, still nasty
		System.out.println("Numrows: "+db.num_rows());
		// only returns valid data in case of update/insert
		System.out.println("Aff. rows: "+db.affected_rows());
		
		db.prepare("INSERT INTO product (name, description) VALUES (?,?)");
		// order is of importance here! (no named placeholders available)
		db.bind_param(1, "test product");
		db.bind_param(2, "test product");
		db.executeUpdate();
		System.out.println("insert id: "+db.insert_id());
		
		db.prepare("DELETE FROM product WHERE name LIKE ?");
		db.bind_param(1, "%test product%");
		System.out.println("Deleted: "+db.executeUpdate());

		System.out.println("Aff. rows: "+db.affected_rows());
		
		
		// PAGINATION
		String query = "SELECT * FROM customer";
		int per_page = 11;
		
		// working with the interface here!
		IPagination pag = new Pagination();
		pag.setPerPage(per_page);
		pag.setQuery(query);
		
		// this works
		//pag.setPerPage(2);
		// this too!
		//pag.setQuery("SELECT * FROM customer WHERE CID=1");
		// in case you want to reload the number of rows:
		//pag.reload();
		
		// fetch the paginated query
		String new_query = pag.paginate(2);
		
		// take a look at the diff
		System.out.println("Query: "+query);
		System.out.println("New Q: "+new_query);
		
		// same stuff from here on
		rs = db.executeQuery(new_query);
		while(rs.next()) {
			System.out.println(rs.getInt(1) + ": " + rs.getString("firstName")+" "+rs.getString("lastName"));
		} 
		*/
	} catch (SQLException e) {
		Error_Frame.Error(e.toString());
	} catch (Exception e) {
		Error_Frame.Error(e.toString());
	}
	return db;
}  
  
    
   public void Exit (){
	   System.exit(0);
   } 
}
