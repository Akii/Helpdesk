package mvc.View;
/******************
 * Imports
 ******************/
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

    /*
    *  Initialize
    */
  public  class ImageRenderer extends DefaultTableCellRenderer {
    JLabel lbl = new JLabel();
    int i = 1;
    ImageIcon icon_open = new ImageIcon(getClass().getResource("pics/green.png"));
    ImageIcon icon_inprocess = new ImageIcon(getClass().getResource("pics/yellow.png"));
    ImageIcon icon_closed = new ImageIcon(getClass().getResource("pics/red.png"));

    
    /******************
    * CellTableRenderer
    ******************/
    
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
      //Component cell = super.getTableCellRendererComponent(
     // table, value, isSelected, hasFocus, row, column);
      Object open = "Open";
      Object inprocess = "In process";
      lbl.setText((String) value);
      lbl.setOpaque(true);
      
      //set fore and background
        if(isSelected){           
	    lbl.setBackground(table.getSelectionBackground());
	    lbl.setForeground(table.getSelectionForeground());
	} else{
	    lbl.setBackground(table.getBackground());
            lbl.setForeground(table.getForeground());
	}
        
      //set icons for each status
      if (value.equals(open)) {
          lbl.setIcon(icon_open);
          return lbl;
      } else if (value.equals(inprocess)) {
          lbl.setIcon(icon_inprocess);
          return lbl;
      } else {
          lbl.setIcon(icon_closed);
          return lbl;
      }
      
   }
  }

