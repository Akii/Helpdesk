package Helpdesk.java.helpdesk.lib;
/******************
 * Imports
 ******************/
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

  public  class ImageRenderer extends DefaultTableCellRenderer {
    JLabel lbl = new JLabel();
    ImageIcon icon_open = new ImageIcon(getClass().getResource("/Helpdesk/java/helpdesk/mvc/View/pics/green.png"));
    ImageIcon icon_inprocess = new ImageIcon(getClass().getResource("/Helpdesk/java/helpdesk/mvc/View/pics/yellow.png"));
    ImageIcon icon_closed = new ImageIcon(getClass().getResource("/Helpdesk/java/helpdesk/mvc/View/pics/red.png"));

    
    /******************
    * CellTableRenderer
    ******************/
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {

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

