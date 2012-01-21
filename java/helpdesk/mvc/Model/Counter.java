package Helpdesk.java.helpdesk.mvc.Model;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Helpdesk.java.helpdesk.lib.db.Database;
import Helpdesk.java.helpdesk.mvc.View.Main_Frame;

public class Counter extends Thread {
    private Main_Frame main;
    public Counter (Main_Frame main) {
        this.main = main;
    }
    /******************************
     * get ticket status from db
     *******************************/
    public static Object[] getCount() {
        Object[] Array;
        ArrayList<Object> ArrayList = new ArrayList<>();
        Database db = MainModel.dbconnect();
    try {
        String query = "SELECT ticket_status FROM full_ticket";
        db.prepare(query);
	ResultSet rs = db.executeQuery();         
	while(rs.next()) {
               Array = new String[]{rs.getString(rs.getMetaData().getColumnName(1))};
               ArrayList.add(Array[0]); 
	}
	db.close();      
        } catch (SQLException e) {
            Error_Frame.Error("There are too many connections to the Database");
        } 
        return ArrayList.toArray(); 
    }
    
      /**
     * get ticket status count and set button text
     */
    @Override
        public void run () {
        Integer openCount=0,processCount=0,closedCount=0;
        Object [] A2 = Counter.getCount();
               for (int i=0;i<=A2.length-1;i++) {
                if ("Open".equals(A2[i])) {
                    openCount++;
                } else if ("In process".equals(A2[i])) {
                    processCount++;
                } else if ("Closed".equals(A2[i])) {
                    closedCount++;
                }
               }
        main.btn_setopen.setText("Open [" + openCount+"]");
        main.btn_setprocess.setText("In Process [" + processCount +"]");
        main.btn_setclosed.setText("Closed [" + closedCount +"]");
    }
}

