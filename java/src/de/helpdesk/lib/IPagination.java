package de.helpdesk.lib;

import java.sql.SQLException;

public interface IPagination {
	/**
	 * Set the number of rows to display. Calling this function is optional, defaulted to 1.
	 * @param per_page Rows per page (Default: 1).
	 */
	public void setPerPage(int per_page);
	
	/**
	 * Set the query to manipulate. Calculates the rows and pages.
	 * @param query SQL query to paginate.
	 * @throws SQLException In case of invalid sql query.
	 */
	public void setQuery(String query) throws SQLException;
	
	/**
	 * Reloads the number of rows for a query. Used when there were INSERT/DELETES after calling setQuery().
	 * @throws SQLException In case of invalid sql query.
	 */
	public void reload() throws SQLException;
	
	/**
	 * Prepares the query in such a way pagination is possible. Page is 1 for the first, 2 for the second, and so on.
	 * Invalid page numbers are corrected automatically.
	 * @param page The page to display.
	 * @return Manipulated SQL query with offset applied.
	 */
	public String paginate(int page);
	
	/**
	 * Number of pages to display.
	 * @return Number of pages.
	 */
	public int getPages();
	
	/**
	 * Number of the current page.
	 * @return Number of current page.
	 */
	public int getCurrPage();
	
	/**
	 * Returns the next page, in case there is no next page it returns the last page.
	 * @return Next page.
	 */
	public int getNextPage();
	
	/**
	 * Returns the previous page, in case there is no previos page it returns 0.
	 * @return Previous page.
	 */
	public int getPrevPage();
	
	/**
	 * Returns if there are more pages. Will return false if current page = last page.
	 * @return true if there is a next page.
	 */
	public boolean hasNextPage();
	
	/**
	 * Returns if there is a previous page. Returns false if current page = 0.
	 * @return true if there is a previous page.
	 */
	public boolean hasPrevPage();
}
