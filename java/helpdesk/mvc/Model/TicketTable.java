package mvc.Model;
/******************
 * Imports
 ******************/
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

     public class TicketTable extends AbstractTableModel {
        private String[] columnNames = {"TID", "Customer CID", "employee EID", "CategoryID", 
                                        "StatusID", "Topic", "Problem", "Note", "Solution",
                                        "created_on","last_update"};
        private Object[][] data      = {};
        private ArrayList<Ticket> arr_data;

        
        /**************************
        *  set data into jtable
        ****************************/
        public void showData() {
            arr_data = Ticket.showAll();
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
                    case 1: return Integer.class;
                    case 2: return Integer.class;
                    case 3: return Integer.class;
                    case 4: return Integer.class;
                    case 9: return Object.class;
                    case 10: return Object.class;    
                    default: return String.class;
                }
        }
}
        
