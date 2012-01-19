package Helpdesk.java.helpdesk.mvc.View;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.lib.ImageRenderer;
import java.awt.Frame;
import java.awt.MenuContainer;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.Serializable;

import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionListener;
import Helpdesk.java.helpdesk.lib.refreshTable;
import Helpdesk.java.helpdesk.mvc.Model.Counter;
import Helpdesk.java.helpdesk.mvc.Model.CustomerTable;
import Helpdesk.java.helpdesk.mvc.Model.EmployeeTable;
import Helpdesk.java.helpdesk.mvc.Model.FullticketTable;
import Helpdesk.java.helpdesk.mvc.Model.HistoryTable;
import Helpdesk.java.helpdesk.mvc.Model.ProductTable;


public class Main_Frame extends javax.swing.JFrame implements ImageObserver, MenuContainer, Serializable {
    private CustomerTable c_model;
    private EmployeeTable e_model;
    private FullticketTable f_model;
    private HistoryTable h_model;
    private ProductTable p_model;
    
     /**************************
     *  
     *  set look and feel - init GUI and components
     *  
     ***************************/
    // <editor-fold desc="Constructor">
    /** Creates new form helpdesk_main */
    public Main_Frame(CustomerTable c_model,EmployeeTable e_model,
                                FullticketTable f_model, HistoryTable h_model,
                                ProductTable p_model) {
       this.c_model = c_model;
       this.e_model = e_model;
       this.f_model = f_model;
       this.h_model = h_model;
       this.p_model = p_model;
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initComponents();
    }
    //</editor-fold>
    
    
    // <editor-fold desc="Initialize">
    public void showView(){ 
        if (Toolkit.getDefaultToolkit().isFrameStateSupported(Frame.MAXIMIZED_BOTH)) {
                this.setExtendedState(Frame.MAXIMIZED_BOTH);
            }
        this.setVisible(true);
        init();
    }    
     //</editor-fold>
    
    
     /**************************
     *  init JTable and set models
     ***************************/
    // <editor-fold desc="init JTable and set models">
    private void init () {
        getStatusCount ();
        table_fullticket.setModel(f_model);
        table_customer.setModel(c_model);
        table_employee.setModel(e_model);
        table_product.setModel(p_model);
        table_history.setModel(h_model);
        refreshTable A1 = new refreshTable(c_model, e_model, f_model, h_model, p_model);
        A1.start();
        table_fullticket.getColumnModel().getColumn(2).setCellRenderer(new ImageRenderer());
        initTable();
    }
    
    public void initTable () {
        int[] fArray = {45,120,80,45,150,150,45,150,150,150,200,150,150,150,150,150,150};
        for (int i=0; i<= table_fullticket.getColumnCount()-1; i++) {
            table_fullticket.getColumnModel().getColumn(i).setPreferredWidth(fArray[i]);
        }
        
        int [] cArray = {45,150,150,150,220,150,270};
        for (int i=0; i<= table_customer.getColumnCount()-1; i++) {
            table_customer.getColumnModel().getColumn(i).setPreferredWidth(cArray[i]);
        }
        
        int[] eArray = {45,150,150,150,130,250};
        for (int i=0; i<= table_employee.getColumnCount()-1; i++) {
            table_employee.getColumnModel().getColumn(i).setPreferredWidth(eArray[i]);
        }
        
        int[] pArray = {45, 200, 800};
        for (int i=0; i<= table_product.getColumnCount()-1; i++) {
            table_product.getColumnModel().getColumn(i).setPreferredWidth(pArray[i]);
        }
        
        int[] hArray = {70,150,150,400};
        for (int i=0; i<= table_history.getColumnCount()-1; i++) {
            table_history.getColumnModel().getColumn(i).setPreferredWidth(hArray[i]);
        }
    }
    //</editor-fold>
    
    
      /*****************************************************************
     *  Count ticket status like open,in process and closed in fulltickettable
     *  and set counts in fullticket control buttons
     ********************************************************************/
    // <editor-fold desc="Status count">
    public void getStatusCount () {
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
        btn_setopen.setText("Open [" + openCount+"]");
        btn_setprocess.setText("In Process [" + processCount +"]");
        btn_setclosed.setText("Closed [" + closedCount +"]");
    }
    //</editor-fold>
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_addeditC = new javax.swing.JButton();
        btn_addeditE = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        btn_addeditT = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        Pane_Overview = new javax.swing.JTabbedPane();
        pane_fullticket = new javax.swing.JPanel();
        scrollpane_fullticket = new javax.swing.JScrollPane();
        table_fullticket = new javax.swing.JTable();
        intf_fullticket = new javax.swing.JInternalFrame();
        scrollpane_full = new javax.swing.JScrollPane();
        txp_fullticket = new javax.swing.JEditorPane();
        edt_filterfullticket = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        pane_customer = new javax.swing.JPanel();
        scrollpane_customer = new javax.swing.JScrollPane();
        table_customer = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        edt_filtercustomer = new javax.swing.JTextField();
        pane_employee = new javax.swing.JPanel();
        scrollpane_employee = new javax.swing.JScrollPane();
        table_employee = new javax.swing.JTable();
        edt_filteremployee = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        pane_product = new javax.swing.JPanel();
        scrollpane_product = new javax.swing.JScrollPane();
        table_product = new javax.swing.JTable();
        edt_filterproduct = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        pane_history = new javax.swing.JPanel();
        scrollpane_history = new javax.swing.JScrollPane();
        table_history = new javax.swing.JTable();
        edt_filtertickethis = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        intf_history = new javax.swing.JInternalFrame();
        scrollpane_his = new javax.swing.JScrollPane();
        txp_history = new javax.swing.JEditorPane();
        jSeparator1 = new javax.swing.JSeparator();
        btn_max = new javax.swing.JButton();
        btn_addeditP = new javax.swing.JButton();
        Logo = new javax.swing.JLabel();
        intf_fcontrol = new javax.swing.JInternalFrame();
        btn_setfull = new javax.swing.JButton();
        btn_setopen = new javax.swing.JButton();
        btn_setprocess = new javax.swing.JButton();
        btn_setclosed = new javax.swing.JButton();
        intf_mycontrol = new javax.swing.JInternalFrame();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Helpdesk");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.white);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setName("frame_main"); // NOI18N

        btn_addeditC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_addeditC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mvc/View/pics/customer_button.png"))); // NOI18N
        btn_addeditC.setText("Customer");
        btn_addeditC.setToolTipText("Add or Edit Customer");
        btn_addeditC.setFocusable(false);
        btn_addeditC.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_addeditC.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn_addeditC.setMaximumSize(new java.awt.Dimension(61, 59));
        btn_addeditC.setMinimumSize(new java.awt.Dimension(61, 59));
        btn_addeditC.setPreferredSize(new java.awt.Dimension(61, 59));
        btn_addeditC.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_addeditC.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btn_addeditE.setFont(new java.awt.Font("Tahoma", 0, 12));
        btn_addeditE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mvc/View/pics/employee_button.png"))); // NOI18N
        btn_addeditE.setText("Employee");
        btn_addeditE.setToolTipText("Add or Edit Employee");
        btn_addeditE.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_addeditE.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn_addeditE.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_addeditE.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btn_refresh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mvc/View/pics/svn-update.png"))); // NOI18N
        btn_refresh.setText("Refresh");
        btn_refresh.setToolTipText("Refresh all Datatables");
        btn_refresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_refresh.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn_refresh.setMaximumSize(new java.awt.Dimension(99, 57));
        btn_refresh.setMinimumSize(new java.awt.Dimension(99, 57));
        btn_refresh.setPreferredSize(new java.awt.Dimension(99, 57));
        btn_refresh.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_refresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btn_addeditT.setFont(new java.awt.Font("Tahoma", 0, 12));
        btn_addeditT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mvc/View/pics/ticket_button.png"))); // NOI18N
        btn_addeditT.setText("Ticket");
        btn_addeditT.setToolTipText("Add or Edit Ticket");
        btn_addeditT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_addeditT.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn_addeditT.setMaximumSize(new java.awt.Dimension(99, 57));
        btn_addeditT.setMinimumSize(new java.awt.Dimension(99, 57));
        btn_addeditT.setPreferredSize(new java.awt.Dimension(99, 57));
        btn_addeditT.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_addeditT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        table_fullticket.setAutoCreateRowSorter(true);
        table_fullticket.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table_fullticket.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollpane_fullticket.setViewportView(table_fullticket);

        intf_fullticket.setClosable(true);
        intf_fullticket.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        intf_fullticket.setTitle("Fullticket");
        intf_fullticket.setToolTipText("");
        intf_fullticket.setMinimumSize(new java.awt.Dimension(566, 469));
        intf_fullticket.setPreferredSize(new java.awt.Dimension(566, 469));
        intf_fullticket.setVisible(true);

        txp_fullticket.setBackground(new java.awt.Color(204, 204, 204));
        txp_fullticket.setContentType("text/html");
        txp_fullticket.setEditable(false);
        txp_fullticket.setText("");
        txp_fullticket.setMinimumSize(new java.awt.Dimension(200, 600));
        txp_fullticket.setName(""); // NOI18N
        txp_fullticket.setPreferredSize(new java.awt.Dimension(300, 1000));
        scrollpane_full.setViewportView(txp_fullticket);

        javax.swing.GroupLayout intf_fullticketLayout = new javax.swing.GroupLayout(intf_fullticket.getContentPane());
        intf_fullticket.getContentPane().setLayout(intf_fullticketLayout);
        intf_fullticketLayout.setHorizontalGroup(
            intf_fullticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane_full, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
        intf_fullticketLayout.setVerticalGroup(
            intf_fullticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane_full, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );

        jLabel1.setText("Filter");

        javax.swing.GroupLayout pane_fullticketLayout = new javax.swing.GroupLayout(pane_fullticket);
        pane_fullticket.setLayout(pane_fullticketLayout);
        pane_fullticketLayout.setHorizontalGroup(
            pane_fullticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_fullticketLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_fullticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pane_fullticketLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edt_filterfullticket, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE))
                    .addComponent(scrollpane_fullticket, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(intf_fullticket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pane_fullticketLayout.setVerticalGroup(
            pane_fullticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_fullticketLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_fullticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(intf_fullticket, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addGroup(pane_fullticketLayout.createSequentialGroup()
                        .addGroup(pane_fullticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edt_filterfullticket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollpane_fullticket, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE))))
        );

        try {
            intf_fullticket.setIcon(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        Pane_Overview.addTab("FullTickets", pane_fullticket);

        table_customer.setAutoCreateRowSorter(true);
        table_customer.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table_customer.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollpane_customer.setViewportView(table_customer);

        jLabel3.setText("Filter");

        javax.swing.GroupLayout pane_customerLayout = new javax.swing.GroupLayout(pane_customer);
        pane_customer.setLayout(pane_customerLayout);
        pane_customerLayout.setHorizontalGroup(
            pane_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pane_customerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollpane_customer, javax.swing.GroupLayout.DEFAULT_SIZE, 1298, Short.MAX_VALUE)
                    .addGroup(pane_customerLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edt_filtercustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 1270, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pane_customerLayout.setVerticalGroup(
            pane_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_customerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(edt_filtercustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollpane_customer, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE))
        );

        Pane_Overview.addTab("Customer", pane_customer);

        table_employee.setAutoCreateRowSorter(true);
        table_employee.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table_employee.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollpane_employee.setViewportView(table_employee);

        jLabel4.setText("Filter");

        javax.swing.GroupLayout pane_employeeLayout = new javax.swing.GroupLayout(pane_employee);
        pane_employee.setLayout(pane_employeeLayout);
        pane_employeeLayout.setHorizontalGroup(
            pane_employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_employeeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollpane_employee, javax.swing.GroupLayout.DEFAULT_SIZE, 1298, Short.MAX_VALUE)
                    .addGroup(pane_employeeLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edt_filteremployee, javax.swing.GroupLayout.DEFAULT_SIZE, 1270, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pane_employeeLayout.setVerticalGroup(
            pane_employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_employeeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(edt_filteremployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollpane_employee, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE))
        );

        Pane_Overview.addTab("Employee", pane_employee);

        table_product.setAutoCreateRowSorter(true);
        table_product.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table_product.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollpane_product.setViewportView(table_product);

        jLabel5.setText("Filter");

        javax.swing.GroupLayout pane_productLayout = new javax.swing.GroupLayout(pane_product);
        pane_product.setLayout(pane_productLayout);
        pane_productLayout.setHorizontalGroup(
            pane_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_productLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollpane_product, javax.swing.GroupLayout.DEFAULT_SIZE, 1298, Short.MAX_VALUE)
                    .addGroup(pane_productLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edt_filterproduct, javax.swing.GroupLayout.DEFAULT_SIZE, 1270, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pane_productLayout.setVerticalGroup(
            pane_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_productLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(edt_filterproduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollpane_product, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE))
        );

        Pane_Overview.addTab("Product", pane_product);

        table_history.setAutoCreateRowSorter(true);
        table_history.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table_history.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollpane_history.setViewportView(table_history);

        jLabel6.setText("Filter");

        intf_history.setClosable(true);
        intf_history.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        intf_history.setTitle("History");
        intf_history.setToolTipText("");
        intf_history.setMinimumSize(new java.awt.Dimension(566, 469));
        intf_history.setPreferredSize(new java.awt.Dimension(566, 469));
        intf_history.setVisible(true);

        txp_history.setBackground(new java.awt.Color(204, 204, 204));
        txp_history.setContentType("text/html");
        txp_history.setEditable(false);
        txp_history.setText("");
        txp_history.setMinimumSize(new java.awt.Dimension(200, 600));
        txp_history.setName(""); // NOI18N
        txp_history.setPreferredSize(new java.awt.Dimension(300, 1000));
        scrollpane_his.setViewportView(txp_history);

        javax.swing.GroupLayout intf_historyLayout = new javax.swing.GroupLayout(intf_history.getContentPane());
        intf_history.getContentPane().setLayout(intf_historyLayout);
        intf_historyLayout.setHorizontalGroup(
            intf_historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane_his, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
        intf_historyLayout.setVerticalGroup(
            intf_historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane_his, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pane_historyLayout = new javax.swing.GroupLayout(pane_history);
        pane_history.setLayout(pane_historyLayout);
        pane_historyLayout.setHorizontalGroup(
            pane_historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_historyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pane_historyLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edt_filtertickethis, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE))
                    .addComponent(scrollpane_history, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(intf_history, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pane_historyLayout.setVerticalGroup(
            pane_historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pane_historyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pane_historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pane_historyLayout.createSequentialGroup()
                        .addComponent(intf_history, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pane_historyLayout.createSequentialGroup()
                        .addGroup(pane_historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(edt_filtertickethis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollpane_history, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE))))
        );

        try {
            intf_history.setIcon(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        Pane_Overview.addTab("Ticket History", pane_history);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pane_Overview, javax.swing.GroupLayout.DEFAULT_SIZE, 1323, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pane_Overview, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Overview", jPanel2);

        btn_max.setFont(new java.awt.Font("Tahoma", 0, 12));
        btn_max.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mvc/View/pics/window_fullscreen.png"))); // NOI18N
        btn_max.setText("Pane max");
        btn_max.setEnabled(false);
        btn_max.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_max.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn_max.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btn_addeditP.setFont(new java.awt.Font("Tahoma", 0, 12));
        btn_addeditP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mvc/View/pics/HD.png"))); // NOI18N
        btn_addeditP.setText("Product");
        btn_addeditP.setToolTipText("Add or Edit Ticket");
        btn_addeditP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_addeditP.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn_addeditP.setMaximumSize(new java.awt.Dimension(99, 57));
        btn_addeditP.setMinimumSize(new java.awt.Dimension(99, 57));
        btn_addeditP.setPreferredSize(new java.awt.Dimension(99, 57));
        btn_addeditP.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_addeditP.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        Logo.setFont(new java.awt.Font("Monotype Corsiva", 1, 36));
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mvc/View/pics/helpd_logo.png"))); // NOI18N
        Logo.setText("<HTML><BODY>Hard & <BR>Software</BODY></HTML>");
        Logo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Logo.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        intf_fcontrol.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        intf_fcontrol.setTitle("Fullticket Control");
        intf_fcontrol.setToolTipText("");
        intf_fcontrol.setMaximumSize(new java.awt.Dimension(300, 33));
        intf_fcontrol.setVisible(true);

        btn_setfull.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mvc/View/pics/all.png"))); // NOI18N
        btn_setfull.setText("View all");
        btn_setfull.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_setfull.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_setfull.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btn_setopen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mvc/View/pics/open.png"))); // NOI18N
        btn_setopen.setText("");
        btn_setopen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_setopen.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btn_setopen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btn_setprocess.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mvc/View/pics/process.png"))); // NOI18N
        btn_setprocess.setText("");
        btn_setprocess.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_setprocess.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_setprocess.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btn_setclosed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mvc/View/pics/closed.png"))); // NOI18N
        btn_setclosed.setText("");
        btn_setclosed.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_setclosed.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_setclosed.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout intf_fcontrolLayout = new javax.swing.GroupLayout(intf_fcontrol.getContentPane());
        intf_fcontrol.getContentPane().setLayout(intf_fcontrolLayout);
        intf_fcontrolLayout.setHorizontalGroup(
            intf_fcontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, intf_fcontrolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(intf_fcontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_setopen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                    .addComponent(btn_setfull, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                    .addComponent(btn_setprocess, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_setclosed, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        intf_fcontrolLayout.setVerticalGroup(
            intf_fcontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(intf_fcontrolLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_setfull, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_setopen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_setprocess)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_setclosed)
                .addContainerGap(142, Short.MAX_VALUE))
        );

        intf_mycontrol.setTitle("My Tickets");
        intf_mycontrol.setFocusable(false);
        intf_mycontrol.setMaximumSize(new java.awt.Dimension(2147483647, 33));
        try {
            intf_mycontrol.setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        intf_mycontrol.setVisible(true);

        javax.swing.GroupLayout intf_mycontrolLayout = new javax.swing.GroupLayout(intf_mycontrol.getContentPane());
        intf_mycontrol.getContentPane().setLayout(intf_mycontrolLayout);
        intf_mycontrolLayout.setHorizontalGroup(
            intf_mycontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 119, Short.MAX_VALUE)
        );
        intf_mycontrolLayout.setVerticalGroup(
            intf_mycontrolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(intf_mycontrol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(intf_fcontrol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1328, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btn_addeditC, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_addeditE, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_addeditP, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_addeditT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_max, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 614, Short.MAX_VALUE)
                        .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_addeditC, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_addeditE, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_addeditP, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_addeditT, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_max, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(intf_fcontrol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(intf_mycontrol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE))
                .addContainerGap())
        );

        try {
            intf_fcontrol.setIcon(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            intf_mycontrol.setIcon(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        pack();
    }// </editor-fold>//GEN-END:initComponents
 
    
    /**************************
     *  ActionEvents
     *  Buttons, InternalFrame
     *  Textfield, JTable
     ***************************/
    // <editor-fold defaultstate="collapsed" desc="ActionEvents">     
    
    
     /**************
     *  Buttons
     ***************/
    
    public void setbtn_addeditCListener(ActionListener l){
        this.btn_addeditC.addActionListener(l);
    }
    
    public void setbtn_addeditEListener(ActionListener l){
        this.btn_addeditE.addActionListener(l);
    }
    
    public void setbtn_addeditTListener(ActionListener l){
        this.btn_addeditT.addActionListener(l);
    }
    
    public void setbtn_addeditPListener(ActionListener l){
        this.btn_addeditP.addActionListener(l);
    }
    
    public void setbtn_refreshListener(ActionListener l){
        this.btn_refresh.addActionListener(l);
    } 
    
    public void setbtn_maxListener(ActionListener l){
        this.btn_max.addActionListener(l);
    } 
    
    public void setbtn_setFullListener(ActionListener l){
        this.btn_setfull.addActionListener(l);
    } 
    
    public void setbtn_setOpenListener(ActionListener l){
        this.btn_setopen.addActionListener(l);
    } 
    
    public void setbtn_setProcessListener(ActionListener l){
        this.btn_setprocess.addActionListener(l);
    } 
    
    public void setbtn_setClosedListener(ActionListener l){
        this.btn_setclosed.addActionListener(l);
    } 

    /*****************
     *  Textfilter
     *****************/
    
    public void setedt_filterfullticketListener(KeyListener l){
        this.edt_filterfullticket.addKeyListener(l);
    }
    
   public void setedt_filtertickethisListener(KeyListener l){
        this.edt_filtertickethis.addKeyListener(l);
    }
        
    public void setedt_filterproductListener(KeyListener l){
        this.edt_filterproduct.addKeyListener(l);
    }
    
    public void setedt_filteremployeeListener(KeyListener l){
        this.edt_filteremployee.addKeyListener(l);
    }
    
    public void setedt_filtercustomerListener(KeyListener l){
        this.edt_filtercustomer.addKeyListener(l);
    }
    
    
     /*****************
     *  Internal Frame
     *****************/
    public void setintf_fullticketListener(InternalFrameListener l){
        this.intf_fullticket.addInternalFrameListener(l);
    }
    
    public void setintf_historyListener(InternalFrameListener l){
        this.intf_history.addInternalFrameListener(l);
    }
    
    
    /*************************
     *  JTable click function
     ************************/
    
    public void settable_fullticketListener(MouseListener l){
        this.table_fullticket.addMouseListener(l);
    }
    
    public void settable_customerListener(MouseListener l){
        this.table_customer.addMouseListener(l);
    }
        
    public void settable_employeeListener(MouseListener l){
        this.table_employee.addMouseListener(l);
    }
    
    public void settable_productListener(MouseListener l){
        this.table_product.addMouseListener(l);
    }
    
    
    /******************************
     *  JTable Select List function
     ******************************/
    public void settable_fullticketValueListener(ListSelectionListener l){
        this.table_fullticket.getSelectionModel().addListSelectionListener(l);
    }
    
    public void settable_historyValueListener(ListSelectionListener l){
        this.table_history.getSelectionModel().addListSelectionListener(l);
    }
    //</editor-fold>

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logo;
    private javax.swing.JTabbedPane Pane_Overview;
    public javax.swing.JButton btn_addeditC;
    public javax.swing.JButton btn_addeditE;
    public javax.swing.JButton btn_addeditP;
    private javax.swing.JButton btn_addeditT;
    public javax.swing.JButton btn_max;
    public javax.swing.JButton btn_refresh;
    public javax.swing.JButton btn_setclosed;
    public javax.swing.JButton btn_setfull;
    public javax.swing.JButton btn_setopen;
    public javax.swing.JButton btn_setprocess;
    public javax.swing.JTextField edt_filtercustomer;
    public javax.swing.JTextField edt_filteremployee;
    public javax.swing.JTextField edt_filterfullticket;
    public javax.swing.JTextField edt_filterproduct;
    public javax.swing.JTextField edt_filtertickethis;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JInternalFrame intf_fcontrol;
    public javax.swing.JInternalFrame intf_fullticket;
    public javax.swing.JInternalFrame intf_history;
    private javax.swing.JInternalFrame intf_mycontrol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel pane_customer;
    private javax.swing.JPanel pane_employee;
    private javax.swing.JPanel pane_fullticket;
    private javax.swing.JPanel pane_history;
    private javax.swing.JPanel pane_product;
    private javax.swing.JScrollPane scrollpane_customer;
    private javax.swing.JScrollPane scrollpane_employee;
    public javax.swing.JScrollPane scrollpane_full;
    private javax.swing.JScrollPane scrollpane_fullticket;
    public javax.swing.JScrollPane scrollpane_his;
    private javax.swing.JScrollPane scrollpane_history;
    private javax.swing.JScrollPane scrollpane_product;
    public javax.swing.JTable table_customer;
    public javax.swing.JTable table_employee;
    public javax.swing.JTable table_fullticket;
    public javax.swing.JTable table_history;
    public javax.swing.JTable table_product;
    public javax.swing.JEditorPane txp_fullticket;
    public javax.swing.JEditorPane txp_history;
    // End of variables declaration//GEN-END:variables
}
