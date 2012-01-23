package Helpdesk.java.helpdesk.mvc.Model;
/******************
 * Imports
 ******************/
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

     public class FullticketTable extends AbstractTableModel implements Runnable{
        private static FullticketTable instance = new FullticketTable();
        private String[] columnNames = {"TID", "Problem Category", "Ticket Status","EID", "Employee Firstname", 
                                        "Employee lastname","CID", "Customer Firstname", "Customer lastname",  
                                        "Telephone", "Email",  "Topic", "Problem", "Note", "Solution",
                                        "created_on","last_update"};
        private String status;
        private Object[][] data      = {};
        private ArrayList<FullTicket> arr_data;
        
        
        private FullticketTable() {}
        //Initialization-on-demand holder idiom - Singleton
        public static FullticketTable getInstance() {
		return instance;
	}
        
        public void setStatus (String status) {
            this.status = status;
        }
        
        /**************************
        *  set data into jtable
        ****************************/
    @Override
        public void run() {
            arr_data = FullTicket.showAll(status);
            data = new Object[arr_data.size()][];
            for (int i = 0; i < arr_data.size(); i++) {
                data[i] = arr_data.get(i).Array();
            }
            this.fireTableDataChanged();
        }

    @Override
        public int getColumnCount() {
            return columnNames.length;
        }

    @Override
        public int getRowCount() {
            return data.length;
        }

    @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

    @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
        
        @Override
        public Class<?> getColumnClass( int column ) {
                switch( column ){
                    case 0: return Integer.class;
                    case 3: return Integer.class;
                    case 6: return Integer.class;
                    case 9: return Integer.class;
                    case 15: return Object.class;
                    case 16: return Object.class;
                    default: return String.class;
                }
        }
}


        
