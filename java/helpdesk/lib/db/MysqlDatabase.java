// singleton class
package Helpdesk.java.helpdesk.lib.db;

import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;

public class MysqlDatabase extends Database {
	// db connection handle
	private Connection con;
	private PreparedStatement stmt;
	private ResultSet res;
        private String host,db_name,user,pw;

    @Override
        public void first (String host, String db_name, String user, String pw) {
            try {
                this.host = host;
                this.db_name = db_name;
                this.user = user;
                this.pw = pw;
                connect();
            } catch (SQLException ex) {
                Error_Frame.Error(ex.toString());
            }
        }
	
	public static Database getInstance() throws Exception {
		// this enables us to call Database.getInstance() at any
		// given point in the runtime after initializing the db
		// once, for example in the Main method (Singleton Pattern)
		if(Database.db == null) {
			Database.db = new MysqlDatabase();
		}
		return Database.db;
	}
	
	public MysqlDatabase() throws Exception {
        // load the driver for mysql
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch(Exception e) {
            // no need to distinct exception type
            // if that doesn't work the driver cannot be found
            // and there is no point in going any further
            String msg = String.format( "The MySQL driver could not be loaded.\n" +
            "Details: %s", e.getMessage());
            throw new Exception(msg);
            }
        }
	
	// TODO: timeout handling for invalid hosts (maybe on GUI level)
    @Override
	public void connect() throws SQLException {
		String url = String.format("jdbc:mysql://%s/%s", this.host, this.db_name);
                	// Timeout of a failed connection is around 30 secs.
                        // Connection timeout should not be more than 10 secs.
                        // Trying to do with setLoginTimeout, but it doesnt seems to work.
                        DriverManager.setLoginTimeout(5);
                        this.con = DriverManager.getConnection(url, this.user, this.pw);
	}
	
    @Override
	public void prepare(String query) throws SQLException {
		checkCon();
		
		// check if we need to prepare again and reset data from old statements
		if(this.last_prepare.equals(query)) return;
		this.values.clear();
		
		this.stmt = this.con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		this.last_prepare = query;
	}
	
    @Override
	public void bind_param(int index, String value) throws SQLException {
		checkStmt();
		
		//Placeholder
		this.values.put(index, value);
		this.stmt.setString(index, this.values.get(index));
	}
	
    @Override
	public ResultSet executeQuery() throws SQLException {
		checkStmt();
		
		return this.res = this.stmt.executeQuery();
	}
	
    @Override
	public ResultSet executeQuery(String query) throws SQLException {
		checkCon();
		
		Statement temp_stmt = this.con.createStatement();
		return this.res = temp_stmt.executeQuery(query);
	}
	
    @Override
	public int executeUpdate() throws SQLException {
		checkStmt();
		
		return this.stmt.executeUpdate();
	}
	
    @Override
	public int executeUpdate(String query) throws SQLException {
		checkCon();
		
		Statement temp_stmt = this.con.createStatement();
		return temp_stmt.executeUpdate(query);
	}
    
    @Override
	public void addBatch() throws SQLException {
		checkStmt();
		
                this.stmt.addBatch();
	}
        
    @Override
        public int[] executeBatch() throws SQLException {
		checkStmt();
                
                return this.stmt.executeBatch();
	}
	
    @Override
	public void free_result() throws SQLException {
		// no need to close a stmt which doesn't exist
		if(this.stmt != null && !this.stmt.isClosed()) {
			this.stmt.close();
			this.stmt = null;
			this.last_prepare = "";
		}
		if(this.res != null && !this.res.isClosed()) {
			this.res.close();
			this.res = null;
		}
	}
	
    @Override
	public int num_rows() throws SQLException {
		if(this.res != null && !this.res.isClosed()) {
			res.last();
			int num_rows = res.getRow();
			res.beforeFirst();
			return num_rows;
		} else {
			return 0;
		}
	}
	
    @Override
	public int affected_rows() throws SQLException {
		checkStmt();
		
		return this.stmt.getUpdateCount();
	}
	
    @Override
	public int insert_id() throws SQLException {
		checkStmt();
		
		ResultSet st = this.stmt.getGeneratedKeys();
		st.next();
		
		return st.getInt(1);
	}
	
    @Override
	public String add_offset(String query, int from, int to) {
		// mysql uses the LIMIT syntax
		return String.format("%s LIMIT %d,%d", query, from, (to-from));
	}
	
    @Override
	public void close() throws SQLException {
		// close any result or statement and reset them
		free_result();
		
		// close the connection and reset it
		if(this.con != null && !this.con.isClosed()) {
			this.con.close();
		}
		this.con = null;		
	}
	
	private void checkCon() throws SQLException {
		if(this.con == null || this.con.isClosed())
			throw new SQLException("Connection not ready.");
	}
	
	private void checkStmt() throws SQLException {
		if(this.stmt == null || this.stmt.isClosed())
			throw new SQLException("Statement not ready.");
	}
}
