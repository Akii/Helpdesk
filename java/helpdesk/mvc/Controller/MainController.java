package Helpdesk.java.helpdesk.mvc.Controller;
/******************
 * Imports
 ******************/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.PatternSyntaxException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.RowFilter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import Helpdesk.java.helpdesk.lib.refreshTable;
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import Helpdesk.java.helpdesk.lib.ImageRenderer;
import Helpdesk.java.helpdesk.mvc.Model.FacadeModel;
import Helpdesk.java.helpdesk.mvc.View.Main_Frame;

public class MainController {
    private FacadeModel fa;
    private Main_Frame _view;
    //Deactivate refresh button for n sec to prevent DB connection overflow
    private Integer Refreshbtn_Timer = 2000; 
   
        public MainController (Main_Frame _view) {
            this.fa = new FacadeModel();
            this._view = _view;
            addListener();
            init();
        }
    
    private void addListener() {
        this._view.setbtn_addeditCListener(new btn_addeditCListener());
        this._view.setbtn_addeditEListener(new btn_addeditEListener());
        this._view.setbtn_addeditTListener(new btn_addeditTListener());
        this._view.setbtn_addeditPListener(new btn_addeditPListener());
        

        this._view.setbtn_refreshListener(new btn_refreshListener());
        this._view.setbtn_maxListener(new btn_maxListener());
        this._view.setbtn_setFullListener(new btn_setFullListener());
        this._view.setbtn_setOpenListener(new btn_setOpenListener());
        this._view.setbtn_setProcessListener(new btn_setProcessListener());
        this._view.setbtn_setClosedListener(new btn_setClosedListener());
        
        this._view.setedt_filterfullticketListener(new edt_filterfullticketListener() {});
        this._view.setedt_filtertickethisListener(new edt_filtertickethisListener());
        this._view.setedt_filterproductListener(new edt_filterproductListener());
        this._view.setedt_filteremployeeListener(new edt_filteremployeeListener());
        this._view.setedt_filtercustomerListener(new edt_filtercustomerListener());
        
        this._view.setintf_fullticketListener(new intf_fullticketListener());
        this._view.setintf_historyListener(new intf_historyListener());
        
        this._view.settable_fullticketListener(new table_fullticketListener());
        this._view.settable_customerListener(new table_customerListener());
        this._view.settable_employeeListener(new table_employeeListener());
        this._view.settable_productListener(new table_productListener());
        
        this._view.settable_fullticketValueListener(new table_fullticketValueListener());
        this._view.settable_historyValueListener(new table_fullticketValueListener());
        this._view.settable_productValueListener(new table_fullticketValueListener());
        
        this._view.setmenu_customerListener(new menu_customerListener());
        this._view.setmenu_employeeListener(new menu_employeeListener());
        this._view.setmenu_productListener(new menu_productListener());
        this._view.setmenu_ticketListener(new menu_ticketListener());
        this._view.setmenu_quitListener(new menu_quitListener());
    }
    
    private void init () {
            _view.btn_refresh.setEnabled(false);
            new Timer().schedule(new btn_activate(), Refreshbtn_Timer);
            _view.table_fullticket.setModel(fa.getFullticketTable());
            _view.table_customer.setModel(fa.getCustomerTable());
            _view.table_employee.setModel(fa.getEmployeeTable());
            _view.table_product.setModel(fa.getProductTable());
            _view.table_history.setModel(fa.getHistoryTable());
            new refreshTable("Customer", "Employee", "Fullticket", "History", "Product", _view).start();
    }
    
     /*************************************
      * 
      *     ButtonListener
      * 
      **************************************/
    
    
        
     /*************************************
      * Thread - create new Customer,Employee,Product  
      * or Ticket Controller   
      **************************************/
    class btn_addeditCListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {  
           new Thread (new CEController(null,"Customer")).start();
        }
    }
    
     class btn_addeditEListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {  
           new Thread (new CEController(null,"Employee")).start();
        }
    }
     
     
      class btn_addeditTListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent e) {  
              new Thread (new TController(null,_view)).start();
          }
      }
        
      class btn_addeditPListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent e) {  
              new Thread (new PController(null)).start();
          }
      }
      

      class btn_refreshListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) { 
            new refreshTable("Customer", "Employee", "Fullticket", "History", "Product", _view).start();
            //Set CellRenderer for icons in JTable
            _view.table_fullticket.getColumnModel().getColumn(2).setCellRenderer(new ImageRenderer());
            //Deactivate refresh button for 2 sec to prevent DB connection overflow
            _view.btn_refresh.setEnabled(false);
            new Timer().schedule(new btn_activate(), Refreshbtn_Timer);
        }
      }
      
      class btn_maxListener implements ActionListener{
      @Override
          public void actionPerformed(ActionEvent e) {  
                _view.intf_history.setVisible(true);
                _view.intf_fullticket.setVisible(true);
                _view.btn_max.setEnabled(false);
          }
      }
      
       /*************************************
      * Fullticket Control Buttons
      **************************************/
      
      class btn_setFullListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent e) {  
            new refreshTable("", "", "Fullticket", "", "", null).start();
          }
      }
                 
      class btn_setOpenListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent e) {  
            fa.getFullticketTable().setStatus("Open");
            fa.FullticketTableRun();
          }
      }
      
      class btn_setProcessListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent e) {  
            fa.getFullticketTable().setStatus("In process");  
            fa.FullticketTableRun();
          }
      }
      
      class btn_setClosedListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent e) {  
            fa.getFullticketTable().setStatus("Closed");  
            fa.FullticketTableRun();
          }
      }
      
      
      
       /*************************************
      *  Textfield Keylistener
      *  immediatly search table if user 
      *  pressed a key
      **************************************/
      
       class edt_filterfullticketListener implements KeyListener{
         @Override
         public void keyReleased(KeyEvent e) {
             TableRowSorter<TableModel> sorter =
             new TableRowSorter<TableModel>(fa.getFullticketTable());
             _view.table_fullticket.setRowSorter(sorter);
             filter(_view.edt_filterfullticket.getText(),sorter);
         }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }
      }
      
       class edt_filtertickethisListener implements KeyListener{
         @Override
         public void keyReleased(KeyEvent e) {
             TableRowSorter<TableModel> sorter =
             new TableRowSorter<TableModel>(fa.getHistoryTable());
             _view.table_history.setRowSorter(sorter);
             filter(_view.edt_filtertickethis.getText(),sorter);
         }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }
      }
      
       class edt_filterproductListener implements KeyListener{
         @Override
         public void keyReleased(KeyEvent e) {
             TableRowSorter<TableModel> sorter =
             new TableRowSorter<TableModel>(fa.getProductTable());
             _view.table_product.setRowSorter(sorter);
             filter(_view.edt_filterproduct.getText(),sorter);
         }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }
      }
      
       class edt_filteremployeeListener implements KeyListener{
         @Override
         public void keyReleased(KeyEvent e) {
             TableRowSorter<TableModel> sorter =
             new TableRowSorter<TableModel>(fa.getEmployeeTable());
             _view.table_employee.setRowSorter(sorter);
             filter(_view.edt_filteremployee.getText(),sorter);
         }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }
      }
      
       class edt_filtercustomerListener implements KeyListener{
         @Override
         public void keyReleased(KeyEvent e) {
             TableRowSorter<TableModel> sorter =
             new TableRowSorter<TableModel>(fa.getCustomerTable());
             _view.table_customer.setRowSorter(sorter);
             filter(_view.edt_filtercustomer.getText(),sorter);
         }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }
      }
    
        
       
       
     /*************************************
      * InternalFrameListener
      * same function as a simply border with iconifiable
      * looks better
      **************************************/
      
       class intf_fullticketListener implements InternalFrameListener{
        @Override
        public void internalFrameClosing (InternalFrameEvent e) {  
            _view.intf_fullticket.setVisible(false);
            _view.btn_max.setEnabled(true);
        }

        @Override
        public void internalFrameOpened(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameClosed(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameIconified(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameDeiconified(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameActivated(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameDeactivated(InternalFrameEvent e) {
        }
      }
      
        
       class intf_historyListener implements InternalFrameListener{
      @Override
        public void internalFrameClosing (InternalFrameEvent e) {  
            _view.intf_history.setVisible(false);
            _view.btn_max.setEnabled(true);
        }

        @Override
        public void internalFrameOpened(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameClosed(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameIconified(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameDeiconified(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameActivated(InternalFrameEvent e) {
        }

        @Override
        public void internalFrameDeactivated(InternalFrameEvent e) {
        }
      }
            
            
       
       
      /*************************************
      * 
      *     JTable Mouselistener
      * 
      **************************************/    
       
      /*************************************
      * Get the selected row and init JPopUp 
      * If doubleclicked ticket,customer.. edit
      * frame will open and automatically fill out with data
      **************************************/    
       
       class table_fullticketListener implements MouseListener{
        @Override
        public void mouseReleased(MouseEvent evt) {
        if (evt.getClickCount()==2) {
            tableDoubleClick("Fullticket");
        }
            if (evt.getButton() == MouseEvent.BUTTON3) {
                if (_view.table_fullticket.isRowSelected(_view.table_fullticket.getSelectedRow())) {
                    Integer integer = (Integer)_view.table_fullticket.getValueAt(
                    _view.table_fullticket.getSelectedRow(), 0);
                    // get the row index that contains that coordinate
                    int rowNumber = _view.table_fullticket.rowAtPoint(evt.getPoint());
                    // set the selected interval of rows. Using the "rowNumber"
                    // variable for the beginning and end. Selects only that one row.
                    _view.table_fullticket.getSelectionModel().setSelectionInterval(rowNumber, rowNumber);
                    showPopup(evt, integer, "Fullticket");
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
      }
            
       class table_customerListener implements MouseListener{
        @Override
        public void mouseReleased(MouseEvent evt) {
         if (evt.getClickCount()==2) {
             tableDoubleClick("Customer");
         }
            if (_view.table_customer.isRowSelected(_view.table_customer.getSelectedRow())) {
                if (evt.getButton() == MouseEvent.BUTTON3) {
                    Integer integer = (Integer)_view.table_customer.getValueAt(
                    _view.table_customer.getSelectedRow(), 0);
                    // get the coordinates of the mouse click
                    // get the row index that contains that coordinate
                    int rowNumber = _view.table_customer.rowAtPoint(evt.getPoint());
                    // set the selected interval of rows. Using the "rowNumber"
                    // variable for the beginning and end. Selects only that one row.
                    _view.table_customer.getSelectionModel().setSelectionInterval(rowNumber, rowNumber);
                    showPopup(evt, integer, "Customer");
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
      }
      
       
       class table_employeeListener implements MouseListener{
        @Override
        public void mouseReleased(MouseEvent evt) {
         if (evt.getClickCount()==2) {
                tableDoubleClick("Employee");
         }
            if (_view.table_employee.isRowSelected(_view.table_employee.getSelectedRow())) {
                if (evt.getButton() == MouseEvent.BUTTON3) {
                    Integer integer = (Integer)_view.table_employee.getValueAt(
                    _view.table_employee.getSelectedRow(), 0);
                    // get the coordinates of the mouse click
                    // get the row index that contains that coordinate
                    int rowNumber = _view.table_employee.rowAtPoint(evt.getPoint());
                    // set the selected interval of rows. Using the "rowNumber"
                    // variable for the beginning and end. Selects only that one row.
                    _view.table_employee.getSelectionModel().setSelectionInterval( rowNumber, rowNumber );
                    showPopup(evt, integer, "Employee");
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
      }
      
       
       class table_productListener implements MouseListener{
        @Override
        public void mouseReleased(MouseEvent evt) {
         if (evt.getClickCount()==2) {
                tableDoubleClick("Product");
         }
            if (_view.table_product.isRowSelected(_view.table_product.getSelectedRow())) {
                if (evt.getButton() == MouseEvent.BUTTON3) {
                    Integer integer = (Integer)_view.table_product.getValueAt(
                    _view.table_product.getSelectedRow(), 0);
                    // get the coordinates of the mouse click
                    // get the row index that contains that coordinate
                    int rowNumber = _view.table_product.rowAtPoint(evt.getPoint());
                    // set the selected interval of rows. Using the "rowNumber"
                    // variable for the beginning and end. Selects only that one row.
                    _view.table_product.getSelectionModel().setSelectionInterval( rowNumber, rowNumber );
                    showPopup(evt, integer, "Product");
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
      }
      
       
       
       /*************************************
      * 
      *     JTable ListeSelectionListener
      * 
      **************************************/
       
      /*************************************
      * Handler for list selection changes
      * Get selected row and display the data 
      * in Editor pane - set verticalscrollbar to the top
      **************************************/
       
    class table_fullticketValueListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent event) {
 	// activate only on deselecting and see if this is a valid table selection
        if (!event.getValueIsAdjusting()) {
            //fullticket table
	   if( event.getSource() == _view.table_fullticket.getSelectionModel() && event.getFirstIndex() >= 0) {
		// Determine the selected item    
		Integer integer = (Integer)_view.table_fullticket.getValueAt(_view.table_fullticket.getSelectedRow(), 0);
                _view.txp_fullticket.setText(fa.Htmlfullticket(integer).toString());
                 //set vertical scrollbar to top 
                 javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() { 
                        _view.scrollpane_full.getVerticalScrollBar().setValue(0);
                    }
                  });
                }
           
         //history table
         if( event.getSource() == _view.table_history.getSelectionModel() && event.getFirstIndex() >= 0) {
		Integer integer = (Integer)_view.table_history.getValueAt(_view.table_history.getSelectedRow(), 0);
                String changed = (String)_view.table_history.getValueAt(_view.table_history.getSelectedRow(), 1);
                String name = (String)_view.table_history.getValueAt(_view.table_history.getSelectedRow(), 2);
                _view.txp_history.setText(fa.Htmlhistory(integer,changed,name).toString());
                 javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() { 
                        _view.scrollpane_his.getVerticalScrollBar().setValue(0);
                    }
                 });
            }
         //product table
          if( event.getSource() == _view.table_product.getSelectionModel() && event.getFirstIndex() >= 0) {
		Integer integer = (Integer)_view.table_product.getValueAt(_view.table_product.getSelectedRow(), 0);
                _view.txp_product.setText(fa.Htmlproduct(integer).toString());
                 javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() { 
                        _view.scrollpane_his.getVerticalScrollBar().setValue(0);
                    }
                 });
            }
        }
        }
    }  
       
       
      /*************************************
      * 
      *     Menu Item Listener
      * 
      **************************************/
       
      class menu_customerListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent e) {  
            new Thread (new CEController(null,"Customer")).start();
          }
      }
      
      class menu_employeeListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent e) {  
            new Thread (new CEController(null,"Employee")).start();
          }
      }
            
      class menu_productListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent e) {  
            new Thread (new PController(null)).start();
          }
      }
      
      class menu_ticketListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent e) {  
            new Thread (new TController(null,_view)).start();
          }
      }
      
      class menu_quitListener implements ActionListener{
          @Override
          public void actionPerformed(ActionEvent e) {  
            System.exit(0);
          }
      }
    
    
    
    
      /*************************************
      * Doubleclick function 
      * Thread - create new Controller
      * and fill frame with data
      **************************************/
      public void tableDoubleClick (String select) {
            if ("Customer".equals(select)) {
                Integer integer = (Integer)_view.table_customer.getValueAt(
                _view.table_customer.getSelectedRow(), 0);
                new Thread (new CEController(integer,"Customer")).start();
            } else if ("Employee".equals(select)) {
                Integer integer = (Integer)_view.table_employee.getValueAt(
                _view.table_employee.getSelectedRow(), 0);
                new Thread (new CEController(integer,"Employee")).start();
            } else if ("Fullticket".equals(select)) {
                Integer integer = (Integer)_view.table_fullticket.getValueAt(
                _view.table_fullticket.getSelectedRow(), 0);
                new Thread (new TController(integer, _view)).start();
            } else if ("Product".equals(select)) {
                Integer integer = (Integer)_view.table_product.getValueAt(
                _view.table_product.getSelectedRow(), 0);
                new Thread (new PController(integer)).start();
            }
    }
      
       /*************************************
      * JPopUpMenu for JTable
      **************************************/
     private void showPopup(MouseEvent e, final Integer select, final String man) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem update = new JMenuItem("Update");
        JMenuItem delete = new JMenuItem("Delete");
        //Set deletefunction for employee, customer or product
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if ("Employee".equals(man)) {
                   fa.deleteEmployee(select);
                   fa.EmployeeTableRun();
                } else if ("Customer".equals(man)) {
                   fa.deleteCustomer(select);
                   fa.CustomerTableRun();
                } else if ("Product".equals(man)) {
                   fa.deleteProduct(select);
                   fa.ProductTableRun();
                }
            }
	});
        //Set updatefunction
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                tableDoubleClick(man);
            }
         });
         //Add item to Menu - Fullticket doesn't have delete function
         popupMenu.add(update);
            if (!"Fullticket".equals(man)) {
                popupMenu.add(delete);
            }
            //JPopup trigger
            if (e.isPopupTrigger()) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
    }    
      
     /********************************
     *  Textfilter function for jtable
     ************************************/
    public void filter (String text,TableRowSorter sorter) {
           if (text.length() == 0) {
                 sorter.setRowFilter(null);
           } else {
                try {
                    sorter.setRowFilter(
                    RowFilter.regexFilter(text));
                } catch (PatternSyntaxException e) {
                    Error_Frame.Error(e.getPattern());
                }
           }
    }
    
    /*******************************
     *  Timer - activate refresh button
     *  after n-secs
     *************************************/
    class btn_activate extends TimerTask {
        @Override
        public void run(){
            _view.btn_refresh.setEnabled(true);
        }
    }
    
    
}
