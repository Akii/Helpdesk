package mvc.Model;
/******************
 * Imports
 ******************/
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

     public class HistoryTable extends AbstractTableModel {
        private String[] columnNames = {"Ticket ID", "Changed on", "Column name", 
                                        "Column value"};
        private Object[][] data      = {};
        private ArrayList<History> arr_data;

        /**************************
        *  set data into jtable
        ****************************/
        public void showData() {
            arr_data = History.showHistory();
            data = new Object[arr_data.size()][];
            for (int i = 0; i < arr_data.size(); i++) {
                data[i] = arr_data.get(i).toJTableArray();
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
                    case 2: return Object.class;
                    default: return String.class;
                }
        }
}
