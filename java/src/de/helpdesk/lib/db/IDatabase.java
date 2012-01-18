package de.helpdesk.lib.db;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface IDatabase {
	public void connect(String host, String db_name, String user, String pw) throws SQLException;

	public void prepare(String query) throws SQLException;
	public void bind_param(int index, String value) throws SQLException;

	// used for SELECT
	public ResultSet executeQuery() throws SQLException;
	public ResultSet executeQuery(String query) throws SQLException;
	
	// used for INSERT,UPDATE and DELETE
	public int executeUpdate() throws SQLException;
	public int executeUpdate(String query) throws SQLException;
	
	// frees up resources bound by the statement
	public void free_result() throws SQLException;
	
	// returns the rows from the last (current) query
	public int num_rows() throws SQLException;
	
	// rows affected by the last query
	public int affected_rows() throws SQLException;
	
	// auto generated key from the last query (auto_increment)
	public int insert_id() throws SQLException;
	
	// closes the connection to the database
	public void close() throws SQLException;
}
