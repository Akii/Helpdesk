package Helpdesk.java.helpdesk.mvc.Model;
/******************
 * Imports
 ******************/
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
    
     public class EmployeeTable extends AbstractTableModel {
        private String[] columnNames = {"EID", "Firstname", "Lastname", "Username", "Troubleshooter", "Password"};
        private Object[][] data      = {};
        private ArrayList<Employee> arr_data;

        /**************************
        *  set data into jtable
        ****************************/
        public void showData() {
            arr_data = Employee.showAll();
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
                    case 4: return Integer.class;
                    default: return String.class;
                }
        }
}
        
        
    


