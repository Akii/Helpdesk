package Helpdesk.java.helpdesk.mvc.Model;

import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.sql.SQLException;
import Helpdesk.java.helpdesk.lib.db.Database;
import Helpdesk.java.helpdesk.lib.db.MysqlDatabase;

public abstract class MainModel {

    public static Database dbconnect() {
	Database db = null;
	try {
                // Get the db from the singleton class MysqlDatabase 
                // plus saved login information like host,port,db name ..
                // and then connect
		db = MysqlDatabase.getInstance();
                db.connect();
	} catch (SQLException e) {
		Error_Frame.Error(e.toString());
	} catch (Exception e) {
		Error_Frame.Error(e.toString());
	}
	return db;
    }  
}
