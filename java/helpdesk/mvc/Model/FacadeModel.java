
package Helpdesk.java.helpdesk.mvc.Model;

import Helpdesk.java.helpdesk.mvc.View.Main_Frame;
import java.util.ArrayList;

public class FacadeModel {
    private StatusModel statusmodel;
    private CategoryModel categorymodel;
    
    /**************************
    * Facade TableModel
    ***************************/
    public  CustomerTable getCustomerTable () {
        return CustomerTable.getInstance();
    }
    public  EmployeeTable getEmployeeTable () {
        return EmployeeTable.getInstance();
    }
    public  ProductTable getProductTable () {
        return ProductTable.getInstance();
    }
    public  TicketTable getTicketTable () {
        return TicketTable.getInstance();
    }
    public  HistoryTable getHistoryTable () {
        return HistoryTable.getInstance();
    }
    public  FullticketTable getFullticketTable () {
        return FullticketTable.getInstance();
    }
    
    /**************************
    * Facade Model Category
    ***************************/
    public CategoryModel getCategoryModel () {
        return this.categorymodel;
    }
    
    public  CategoryModel newCategory () {
        CategoryModel ctm = new CategoryModel();
        this.categorymodel = ctm;
        return ctm;
    }
    
    public  ArrayList<Category> showAllCategory() {
        return Category.showAll();
    }
    
    
    /**************************
    * Facade Model Comp
    ***************************/
    
    public  Integer getEmployeeID (String txt) {
        return Comp.getEID(txt);
    }
    
    public  String getEmployeeUsername (Integer _int) {
        return Comp.getEUsername(_int);
    }
    
    public  ArrayList<String> searchTicketProduct(Integer ID) {
        return Comp.searchTicketProduct(ID);
    }
    
    /**************************
    * Facade Model Counter
    ***************************/
    
    public  Object[] getCount() {
        return Counter.getCount();
    }
    
    public Counter CounterRun (Main_Frame main) {
        return new Counter(main);
    }
    
    /**************************
    * Facade Model Customer
    ***************************/
    
    public  void CustomerTableRun () {
        CustomerTable.getInstance().run();
    }
    
    public  ArrayList<Customer> showAllCustomer() {
        return Customer.showAll();
    }
    public void newCustomer(Integer CID,String firstname, String lastname, String username,String pw ,
                            String tel, String email) {
        
        Customer customer = new Customer (CID, firstname,  lastname,  username, pw , tel,  email);
        customer.newCustomer();
    }
    public void updateCustomer(Integer CID,String firstname, String lastname, String username,
                              String pw ,String tel, String email,boolean equal) {
        
        Customer customer = new Customer (CID, firstname,  lastname,  username, pw , tel,  email);
        customer.updateCustomer(equal);
    }
    public  void deleteCustomer(Integer ID) {
        Customer.deleteCustomer(ID);
    }
    public  String[] searchCustomer(Integer ID) {
        return Customer.searchCustomer(ID);
    }
    
    /**************************
    * Facade Model Employee
    ***************************/
    
    public  void EmployeeTableRun () {
        EmployeeTable.getInstance().run();
    }
    
    public  ArrayList<Employee> showAllEmployee() {
        return Employee.showAll();
    }
    
    public void newEmployee(Integer EID, String firstname, String lastname, String username, 
                            Integer trb, String pw) {
        
        Employee employee = new Employee (EID, firstname, lastname, username, trb, pw);
        employee.newEmployee();
    }
    
    public void updateEmployee(Integer EID, String firstname, String lastname, String username, 
                               Integer trb, String pw,boolean equal) {
        
        Employee employee = new Employee (EID, firstname, lastname, username, trb, pw);
        employee.updateEmployee(equal);
    }
    
    public  void deleteEmployee(Integer ID) {
        Employee.deleteEmployee(ID);
    }
    
    public  String[] searchEmployee(Integer ID) {
        return Employee.searchEmployee(ID);
    }
    
    /**************************
    * Facade Model Fullticket
    ***************************/
    public  void FullticketTableRun () {
        FullticketTable.getInstance().run();
    }
    
    public  ArrayList<FullTicket> showAllFullticket(String status) {
        return FullTicket.showAll(status);
    }
    
    public  String[] searchFullTicket(Integer ID) {
        return FullTicket.searchFullTicket(ID);
    }
    
    /**************************
    * Facade Model History
    ***************************/
    
    public  ArrayList<History> showHistory() {
        return History.showHistory();
    }
    
    public  String[] searchHistory(Integer ID,String changed, String name) {
        return History.searchHistory(ID, changed, name);
    }
    
    /**************************
    * Facade Model Html
    ***************************/
    
    public  StringBuilder Htmlfullticket (Integer integer) {
        return HtmlModel.Htmlfullticket(integer);
    }
    
    public  StringBuilder Htmlhistory (Integer ID, String changed, String name) {
        return HtmlModel.Htmlhistory(ID, changed, name);
    }
    
    public  StringBuilder Htmlproduct (Integer ID) {
        return HtmlModel.Htmlproduct(ID);
    }
    
    public  String StringtoDate(String timestamp){ 
        return HtmlModel.StringtoDate(timestamp);
    }
    
    /**************************
    * Facade Model Product
    ***************************/
    
    public  void ProductTableRun () {
        ProductTable.getInstance().run();
    }
    
    public  ArrayList<Product> showAllProduct() { 
        return Product.showAll();
    }
    
    public void newProduct(Integer ID, String p_name, String p_descrription) { 
        Product product = new Product (ID, p_name, p_descrription);
        product.newProduct();
    }
    
    public void updateProduct(Integer ID, String p_name, String p_descrription) {
        Product product = new Product (ID, p_name, p_descrription);
        product.updateProduct();
    }
    
    public  void deleteProduct(Integer ID) {
        Product.deleteProduct(ID);
    }
    
    public  String[] searchProduct(Integer ID) {
        return Product.searchProduct(ID);
    }
    
    public  ArrayList<String> showProductName() {
        return Product.showName();
    }
    
    /**************************
    * Facade Model ProductInvolved
    ***************************/
    
    public void newInvProduct(Integer TID, Object[] PID){
        ProductInv inv = new ProductInv (TID, PID);
        inv.newInvProduct();
    }
    
    public  void deleteInvProduct(Integer TID) {
        ProductInv.deleteInvProduct(TID);
    }
    
    /**************************
    * Facade Model Status
    ***************************/
    
    public StatusModel getStatusModel () {
        return this.statusmodel;
    }
    
    public StatusModel newStatus () {
        this.statusmodel = new StatusModel();
        return this.statusmodel;
    }
    
    public  ArrayList<Status> showAllStatus() {
        return Status.showAll();
    }
    
    /**************************
    * Facade Model Ticket
    ***************************/
    
    public  ArrayList<Ticket> showAllTicket() {
        return Ticket.showAll();
    }
    
    public Integer newTicket(Integer TID, Integer customer_CID, Integer employee_EID, 
                             Integer CategoryID, Integer StatusID, String Topic, 
                             String Problem,String Note, String Solution, String created_on, 
                             String last_update) {
        
        Ticket ticket = new Ticket (TID, customer_CID, employee_EID, CategoryID, StatusID, Topic, 
                             Problem,Note, Solution, created_on, last_update);
        
        return ticket.newTicket();
    }
    
    public void updateTicket(Integer TID, Integer customer_CID, Integer employee_EID, 
                             Integer CategoryID, Integer StatusID, String Topic, 
                             String Problem,String Note, String Solution, String created_on, 
                             String last_update) {
        
        Ticket ticket = new Ticket (TID, customer_CID, employee_EID, CategoryID, StatusID, Topic, 
                             Problem,Note, Solution, created_on, last_update);
        
        ticket.updateTicket();
    }
    
    public  String[] searchTicket(Integer ID) {
        return Ticket.searchTicket(ID);
    }
    
    public  ArrayList Ticket_ComboBox(Integer ID) {
        return Ticket.Ticket_ComboBox(ID);
    }
    
    
}
