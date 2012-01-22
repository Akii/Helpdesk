package Helpdesk.java.helpdesk.mvc.Model;
/******************
 * Imports
 ******************/
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

     public class CustomerTable extends AbstractTableModel {
        private static CustomerTable instance = new CustomerTable();
        private String[] columnNames = {"CID","Firstname", "Lastname", "Username","Email" , "Telephone", "Password"};
        private Object[][] data      = {};
        private ArrayList<Customer> arr_data;
        
        private CustomerTable() {}
        //Singleton class - Thread secure
        public static CustomerTable getInstance() {
		return instance;
	}
        
        /**************************
        *  set data into jtable
        ****************************/
        public void showData() { 
            arr_data = Customer.showAll();
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
                    case 5: return Integer.class;
                    default: return String.class;
                }
    }
}
        
        
    

