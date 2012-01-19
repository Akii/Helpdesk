/*
 * Class Database
 * 
 * Singleton design pattern.
 * Abstract factory design pattern.
 */
package Helpdesk.java.helpdesk.lib.db;
/******************
 * Imports
 ******************/
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class Database implements IDatabase {
	protected static Database db = null;
	
	// prepared statement data
	// current statement
	protected String last_prepare="";
	// hashmap with index, value
	protected Map<Integer, String> values = new HashMap<Integer,String>();

	/**
	 * Returns an existing database object.
	 * @return A database object of the previously initialized database.
	 * @throws Exception In case no database has previously been initialized.
	 */
	public static Database getInstance() throws Exception {
		if(Database.db == null)
			throw new Exception("No database initialized.");
		else
			return Database.db;
	}
	
	/**
	 * Establish connection to the database and load the appropriate driver.
	 * @param host Host name or IP address and port 127.0.0.1:3306.
	 * @param db_name Name of the database to connect to.
	 * @param user Database user name.
	 * @param pw Password for the db user.
	 */
    @Override
	public abstract void connect(String host, String db_name, String user, String pw) throws SQLException;
	
	/**
	 * Creates a PreparedStatement object for sending parameterized SQL statements to the database.
	 * @param query SQL query to prepare
	 */
    @Override
	public abstract void prepare(String query) throws SQLException;
	
	/**
	 * Fills placeholders in a prepared statement.
	 * @param index Identifier for the value. First placeholder is 1, second 2, and so on.
	 * @param value The value to fill the placeholder with. All values are sent to the database as VARCHAR.
	 */
    @Override
	public abstract void bind_param(int index, String value) throws SQLException;
	
	/**
	 * Executes a SELECT prepared statement.
	 * @return <code>ResultSet</code> with the results from the query.
	 */
    @Override
	public abstract ResultSet executeQuery() throws SQLException;
	
	/**
	 * Executes the given SELECT query directly without filling placeholders or preparing a statement.
	 * @param query The query to execute.
	 * @return <code>ResultSet</code> with the results from the query.
	 */
    @Override
	public abstract ResultSet executeQuery(String query) throws SQLException;
	
	/**
	 * Executes a INSERT, UPDATE or DELETE prepared statement.
	 * @return Number of rows affected by the query.
	 */
    @Override
	public abstract int executeUpdate() throws SQLException;
	
	/**
	 * Executes the given INSERT, UPDATE or DELETE query directly without filling placeholders or preparing a statement.
	 * @param query The query to execute.
	 * @return Number of rows affected by the query.
	 */
    @Override
	public abstract int executeUpdate(String query) throws SQLException;
	
	/**
	 * Frees up resources bound by the last query.
	 */
    @Override
	public abstract void free_result() throws SQLException;
	
	/**
	 * Gives the number of rows from the current <code>ResultSet</code>
	 * @return Number of rows.
	 */
    @Override
	public abstract int num_rows() throws SQLException;
	
	/**
	 * Gives the number of affected rows from the last data manipulating query.
	 * Returns -1 in case of a non data manipulating query.
	 * @return Rows affected.
	 */
    @Override
	public abstract int affected_rows() throws SQLException;
	
	/**
	 * Returns the auto generated keys from the last INSERT query.
	 * @return Insert ID
	 */
    @Override
	public abstract int insert_id() throws SQLException;
	
	/**
	 * Manipulates a query to limit its output of rows.
	 * @param query The query to manipulate.
	 * @param from Row to start with.
	 * @param to Last row to display.
	 * @return Manipulated query with offset applied.
	 */
	public abstract String add_offset(String query, int from, int to);
	
	/**
	 * Closes the connection of the database.
	 */
    @Override
	public abstract void close() throws SQLException;
}
